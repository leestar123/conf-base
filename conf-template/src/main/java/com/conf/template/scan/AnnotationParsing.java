package com.conf.template.scan;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conf.template.common.annotation.Rule;
import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.model.ConfRuleInfo;

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
		Method[] methods = clazz.getDeclaredMethods(); 
		for(Method method :methods){
			if(method.isAnnotationPresent(Rule.class)){
				String m = method.getName();
				Rule rule = (Rule)method.getAnnotation(Rule.class);
				record = new ConfRuleInfo();
				record.setClazz(className);
				record.setMethod(m);
				record.setRuleName(rule.name());
				record.setRemark(rule.remark());
				record.setVersion(rule.version());
				//查询表里是否存在该方法(精确查询)
				confRuleInfo = confRuleInfoMapper.selectByMethod(m);
				if(confRuleInfo == null){
					//入库操作
					confRuleInfoMapper.insertSelective(record);
					logger.info("The rule["+rule.name()+"] is added into record successly！");
				}
			}
		}
	}
}
