package com.conf.template.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
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
	public Map<String, ? extends Object> doRule(int productId, int nodeId) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, ? extends Object>> list = new ArrayList<>();
		result.put("result", list);
		
		//根据节点名称查找对应的规则，并进行执行
		List<ConfRuleInfo> selectRecordList = confRuleInfoMapper.selectRecordList(productId, nodeId, null, Constants.EFFECT_STATUS_VALID);
		//判断是否有记录，如果没有则返回false
		if(selectRecordList.size() == 0)
		{
			return ErrorUtil.errorResp(ErrorCode.code_0003, nodeId);
		}

		Class<?> cls = null;
		//获取上下文
		Map<String, ? extends Object> map = (Map<String, ? extends Object>) ToolsUtil.threadLocalGet();
		for(ConfRuleInfo ruleInfo :selectRecordList)
		{
			try {
				cls = Class.forName(ruleInfo.getClazz());
				//初始化一个实例  
				Object obj = cls.newInstance();
				//方法名和对应的参数类型  
			    Method method;
			    method = cls.getMethod(ruleInfo.getMethod(),java.util.Map.class); 
			    //调用得到的上边的方法method
			    //TODO: 考虑调用方法入参和上下文参数传递
			    Object success = method.invoke(obj,map);
			    Map<String, Object> excute = new HashMap<>();
			    if (Boolean.class.isInstance(success)) {
			    	excute.put("nodeId", ruleInfo.getRuleName());
			    	excute.put("success", (Boolean)success);
			    	list.add(excute);
			    	if (!(Boolean)success) {
			    		return ErrorUtil.errorResp(ErrorCode.code_0004, ruleInfo.getRuleName());
			    	}
			    }
			} catch (Exception e) {
				return ErrorUtil.errorResp(ErrorCode.code_9999);
			}
		}
		return ErrorUtil.successResp(result);
	}
}
