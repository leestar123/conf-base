package com.conf.template.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.model.ConfRuleInfo;

/**
 * 规则组件服务
 * 
 * @author li_mingxing
 *
 */
@Component
public class RuleService {
	@Autowired
	ConfRuleInfoMapper confRuleInfoMapper;
	/**
	 * 执行规则组件
	 * 
	 * @param productId
	 * @param nodeName
	 * @return
	 */
	public boolean doRule(int productId, int nodeId) {
		//TODO: 根据节点名称查找对应的规则，并进行执行
		List<ConfRuleInfo> selectRecordList = confRuleInfoMapper.selectRecordList(productId, nodeId);
		//判断是否有记录，如果没有则返回false
		if(selectRecordList.size() == 0)
		{
			return false;
		}

		Class<?> cls = null;
		Map map = null ;
		for(int i=0;i<selectRecordList.size();i++)
		{
			try {
				cls = Class.forName(selectRecordList.get(i).getClazz());
				//初始化一个实例  
				Object obj = cls.newInstance();
				//方法名和对应的参数类型  
			    Method method;
			    method = cls.getMethod(selectRecordList.get(i).getMethod(),java.util.Map.class); 
			    //调用得到的上边的方法method
			    method.invoke(obj,map);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				return false;
			}catch (InstantiationException e) {
				// TODO Auto-generated catch block
				return false;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				return false;
			}catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				return false;
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				return false;
			}catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				return false;
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		return true;
	}
}
