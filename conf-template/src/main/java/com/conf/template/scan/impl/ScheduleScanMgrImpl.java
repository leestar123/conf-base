package com.conf.template.scan.impl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jboss.jandex.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.conf.template.scan.AnnotationParsing;
import com.conf.template.scan.ScanMgr;
import com.conf.template.scheduler.ScheduleTask;

/**
 * 定时扫描
 * 
 * @author li_mingxing
 *
 */
@Configuration
@Component
@EnableScheduling
public class ScheduleScanMgrImpl extends ScheduleTask implements ScanMgr{
	
	@Autowired
	private  AnnotationParsing annotationParsing;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	Set<String> classPaths = new HashSet<String>();
	Set<String> scanClassPaths = new HashSet<String>();
	
	@Override
	public void firstScan() throws Exception {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void reloadableScan() throws Exception {
		logger.debug("start reloadScan...");
		List<String> size = new ArrayList<String>();
		// 先把包名转换为路径,首先得到项目的classpath
		String classpath = Main.class.getResource("/").toString().substring(6);
		
		doPath(new File(classpath));
		for (String s : classPaths) {

			File file = new File(s);
			@SuppressWarnings("resource")
			JarFile jarFile = new JarFile(s);
			URL url = file.toURI().toURL();
			ClassLoader loader = new URLClassLoader(new URL[] { url });
			Enumeration<?> files = jarFile.entries();
			while (files.hasMoreElements()) {
				process(files.nextElement(), size, loader);
				scanClassPaths.add(s);
				classPaths.remove(s);
			}
		}
	}
	
	@Override
	public void doTask() {
		try {
			reloadableScan();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isMaster() {
		return true;
	}
	
	public  void process(Object fileList, List<String> jarList, ClassLoader loader) {
		// 为指定的 JAR 文件条目名称创建新的 JarEntry。
		JarEntry entry = (JarEntry) fileList;

		// 返回条目名称。
		String name = entry.getName();

		// 格式化条目名称
		formatName(jarList, name, loader);
	}

	/**
	 * 格式化jar清单路径（条目名称）
	 * 
	 * @param size
	 * @param name
	 */
	private  void formatName(List<String> size, String name, ClassLoader loader) {
		if (!name.endsWith(".MF") && name.endsWith(".class")) {
			String fileName = name.replaceAll("/", ".");
			int n = 6;
			String ultimaName = fileName.substring(0, name.length() - n);
			try {
				// 加载指定类，注意一定要带上类的包名
				Class<?> cls = loader.loadClass(ultimaName);
				logger.debug("This Class is " + ultimaName);
				// 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，
				// 或是否是其超类或超接口。如果是则返回 true；否则返回 false。如果该 Class 表示一个基本类型，
				// 且指定的 Class 参数正是该 Class 对象，则该方法返回 true；否则返回 false。
				// if(lifeCycle.isAssignableFrom(cls)){
				// size.add(ultimaName);
				// }
				// 执行规则入库操作
				annotationParsing.insertAnnotationInfo(cls);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
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
        } else if(file.getName().endsWith(".jar")) {//标准文件
                //如果是class文件我们就放入我们的集合中。
            if (!scanClassPaths.contains(file.getPath())) {
                classPaths.add(file.getPath());
            }
        }
    }
}
