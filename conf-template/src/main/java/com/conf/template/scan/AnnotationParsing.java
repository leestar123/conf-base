package com.conf.template.scan;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conf.template.common.annotation.Rule;
import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.model.ConfRuleInfo;

@Component
public class AnnotationParsing {
	
	@Autowired
	ConfRuleInfoMapper confRuleInfoMapper;
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	/**
	 * 扫描class文件方法入库
	 * @param clazz
	 */
	public void insertAnnotationInfo(Class<?> clazz)
	{
		logger.debug("The insertAnnotationInfo method start " );
		ConfRuleInfo record = null;
		ConfRuleInfo confRuleInfo = null;
		String className = clazz.getName();
		Method[] methods = clazz.getDeclaredMethods(); 
		for(Method method :methods){
			if(method.isAnnotationPresent(Rule.class)){
				SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");//设置日期格式
				String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
				String m = method.getName();
				Rule rule = (Rule)method.getAnnotation(Rule.class);
				record = new ConfRuleInfo();
				record.setClazz(className);
				record.setMethod(m);
				record.setRuleName(rule.name());
				record.setRemark(rule.remark());
				record.setVersion(rule.version());
				record.setUid(Integer.valueOf(date));
				//查询表里是否存在该方法(精确查询)
				confRuleInfo = confRuleInfoMapper.selectByMethod(m);
				if(confRuleInfo == null){
					//入库操作
					int result = confRuleInfoMapper.insertSelective(record);
					logger.debug("The  ruleName is " + record.getRuleName());
				}
				
			}
		}
	}
}
