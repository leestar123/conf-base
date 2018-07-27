package com.conf.template.scan.impl;

import java.util.List;

import com.conf.common.AnnoManageUtil;
import com.conf.common.PropertyConf;
import com.conf.common.annotation.Rule;
import com.conf.template.scan.AnnotationParsing;
import com.conf.template.scan.ScanMgr;
import com.conf.template.service.ConfBeanService;

/**
 * 启动扫描
 * 
 * @author li_mingxing
 *
 */
public class ScanMgrImpl implements ScanMgr{

	private AnnotationParsing annotationParsing;
	
	private ConfBeanService confBeanService;
	
	private PropertyConf property;
	
	public void setAnnotationParsing(AnnotationParsing annotationParsing)
    {
        this.annotationParsing = annotationParsing;
    }

    public void setProperty(PropertyConf property)
    {
        this.property = property;
    }
    
	public void setConfBeanService(ConfBeanService confBeanService) {
		this.confBeanService = confBeanService;
	}

	public void firstScan() throws Exception {
		//包名
		String []basePacks = property.getScanPackageName().split(",");
        for (String basePack : basePacks)
        {
            List<Class<?>> list = AnnoManageUtil.getPackageAnnotaion(basePack, Rule.class);
            for (Class<?> cls : list) {
                annotationParsing.insertAnnotationInfo(cls);
            }
        }
        
        // 初始化ActionBean的属性值
        confBeanService.initActionBeanField();
	}

	@Override
	public void reloadableScan() throws Exception {
		
	}
}
