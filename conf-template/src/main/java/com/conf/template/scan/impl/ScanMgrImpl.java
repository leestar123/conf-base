package com.conf.template.scan.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jboss.jandex.Main;

import com.conf.common.PropertyConf;
import com.conf.template.scan.AnnotationParsing;
import com.conf.template.scan.ScanMgr;

/**
 * 启动扫描
 * 
 * @author li_mingxing
 *
 */
public class ScanMgrImpl implements ScanMgr{

	private AnnotationParsing annotationParsing;
	
	private PropertyConf property;
	
	public void setAnnotationParsing(AnnotationParsing annotationParsing)
    {
        this.annotationParsing = annotationParsing;
    }

    public void setProperty(PropertyConf property)
    {
        this.property = property;
    }


    List<String> classPaths = new ArrayList<String>();
	
	public void firstScan() throws Exception {
		//包名
		String []basePacks = property.getScanPackageName().split(",");
        //先把包名转换为路径,首先得到项目的classpath
        String classpath = Main.class.getResource("/").toString().substring(6);  
        for (String basePack : basePacks)
        {
            //然后把我们的包名basPach转换为路径名
            String basePackCopy = basePack.replace(".", File.separator);
            //然后把classpath和basePack合并
            String searchPath = classpath + basePackCopy;
            doPath(new File(searchPath));
            //这个时候我们已经得到了指定包下所有的类的绝对路径了。我们现在利用这些绝对路径和java的反射机制得到他们的类对象
            for (String s : classPaths) {
                //把 D:\work\code\20170401\search-class\target\classes\com\baibin\search\a\A.class 这样的绝对路径转换为全类名com.baibin.search.a.A
                s = s.replace(classpath.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".").replace(".class","");
                Class<?> cls = Class.forName(s.substring(s.indexOf(basePack)));
                annotationParsing.insertAnnotationInfo(cls);
            }
        }
	}

	@Override
	public void reloadableScan() throws Exception {
		
	}
	
	/**
     * 该方法会得到所有的类，将类的绝对路径写入到classPaths中
     * @param file
     */
    private void doPath(File file) {
        if (file.isDirectory()) {//文件夹
            //文件夹我们就递归
            File[] files = file.listFiles();
            for (File f1 : files) {
                doPath(f1);
            }
        } else {//标准文件
            //标准文件我们就判断是否是class文件
            if (file.getName().endsWith(".class")) {
                //如果是class文件我们就放入我们的集合中。
                classPaths.add(file.getPath());
            }
        }
    }
}
