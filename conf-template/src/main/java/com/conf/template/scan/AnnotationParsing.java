package com.conf.template.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conf.common.Constants;
import com.conf.common.annotation.Rule;
import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.model.ConfRuleInfo;
import com.conf.template.service.ConfBeanService;

@Component
public class AnnotationParsing {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private ConfRuleInfoMapper confRuleInfoMapper;
	
	/**
	 * 扫描class文件方法入库
	 * @param clazz
	 */
	public void insertAnnotationInfo(Class<?> clazz)
	{
		logger.info("The insertAnnotationInfo method start " );
		ConfRuleInfo record = null;
		ConfRuleInfo confRuleInfo = null;
		String className = clazz.getName();
		String packageName  = clazz.getPackage().getName();
		className = className.substring(className.lastIndexOf(".") + 1, className.length());
		Rule rule = clazz.getAnnotation(Rule.class);
		record = new ConfRuleInfo();
		record.setPackageName(packageName);
        record.setClazz(className);
        //record.setMethod(m);
        record.setRuleName(rule.name());
        record.setRuleType(Constants.RULE_TYPE_ACTION);
        record.setRemark(rule.remark());
        record.setVersion(rule.version());
        record.setParam(ConfBeanService.buildParamString(clazz));
        //查询表里是否存在该方法(精确查询)
        confRuleInfo = confRuleInfoMapper.selectByName(rule.name());
        if(confRuleInfo == null){
            //入库操作
            confRuleInfoMapper.insertSelective(record);
            logger.info("The rule["+rule.name()+"] is added into record successly！");
        }
	}
}
