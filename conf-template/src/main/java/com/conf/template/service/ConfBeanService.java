package com.conf.template.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.annotation.ActionParam;
import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.model.ConfRuleInfo;

/**
 * ActionBean的配置相关服务类
 * @author zhang_pengfei
 *
 */
@Component
public class ConfBeanService {
	
	private final static Logger logger = LoggerFactory.getLogger(ConfBeanService.class);
	
	@Autowired
	ConfRuleInfoMapper confRuleInfoMapper;

	/**
	 * 配置ActionBean属性
	 * @param data
	 * @return
	 */
	@Transactional
    public Map<String, ? extends Object> confBeanField(Map<String, ? extends Object> data) {
		Map<String, Object> body = new HashMap<>();
		Integer uid = ToolsUtil.obj2Int(data.get("uid"), null);
		if (uid == null || uid == 0) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, uid);
		}
		
		// 1.解析param参数json数组
		String param = ToolsUtil.obj2Str(data.get("param"));
		JSONArray jsonArray = JSONArray.parseArray(param);
		
		// 2.根据uid查询规则信息clazz
		ConfRuleInfo rule = confRuleInfoMapper.selectByPrimaryKey(uid);
		try {
			// 3.反射的方式动态注入clazz的属性值
			Map<String, ? extends Object> returnData = setFieldsValue(rule, jsonArray);
		
			// 4.更新uid对应的param(格式：[{"name":"xxx", "value":"xxx", "desc":"xxx"},{"name":"xxx", "value":"xxx", "desc":"xxx"}])
			Object obj = null;
			if ((obj = returnData.get("success")) != null && obj instanceof Boolean) {
				if ((boolean) obj) {
					ConfRuleInfo record = new ConfRuleInfo();
					record.setUid(uid);
					record.setParam(param);
					confRuleInfoMapper.updateByPrimaryKeySelective(record);
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("conf bean field failly!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
		return ErrorUtil.successResp(body);
	}
	
	/**
	 * 初始化ActionBean的静态属性值
	 */
	public void initActionBeanField() {
		List<ConfRuleInfo> beanList = confRuleInfoMapper.selectParamList();
		beanList.stream().forEach(bean -> {
			// 组装jsonArray参数
			JSONArray jsonArray = JSONArray.parseArray(bean.getParam());
			try {
				// 设置属性值
				setFieldsValue(bean, jsonArray);
			} catch (Exception e) {
				logger.error("conf bean field failly!", e);
		        return;
			}
		});
	}

	/**
	 * 给静态变量动态赋值
	 * @param rule 
	 * @param field
	 * @param jsonArray
	 * @return 
	 */
	private Map<String, ? extends Object> setFieldsValue(ConfRuleInfo rule, JSONArray jsonArray) throws ClassNotFoundException{
		String packageName = rule.getPackageName();
		String clazzName = rule.getClazz();
		Field[] fields = null;
		try
		{
			fields = Class.forName(packageName + "." + clazzName).getDeclaredFields();
		}catch (ClassNotFoundException e) {
			logger.error(packageName + "." + clazzName + "找不到对应的类！");
			//TODO: 实例化失败后如何处理
		}
		if (fields == null) {
            return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
		for (Field field : fields) {
			if(Modifier.isStatic(field.getModifiers()) && jsonArray != null) {
				// 给静态变量动态赋值
				jsonArray.stream().forEach(obj -> {
					@SuppressWarnings("unchecked")
					Map<String, Object> map =  (Map<String, Object>)obj;
					try {
						if (field.getName().equals(map.get("name"))) {
							if (field.getType() == int.class)
				            {
								field.setInt(null, Integer.parseInt(map.get("value").toString()));
				            }
				            else if (field.getType() == float.class)
				            {
				                field.setFloat(null, Float.parseFloat(map.get("value").toString()));
				            }
				            else if (field.getType() == double.class)
				            {
				                field.setDouble(null,Double.parseDouble(map.get("value").toString()));
				            }
				            else if (field.getType() == boolean.class)
				            {
				                field.setBoolean(null, Boolean.parseBoolean(map.get("value").toString()));
				            }
				            else if (field.getType() == String.class) {
				            	field.set(null, map.get("value").toString());
				            }
				            else
				            {
				                Object val= field.getType().newInstance();
				                field.set(null,val);
				            }
						}
					} catch (Exception e) {
						logger.error("conf bean field failly!", e);
						return;
					}
				});
			}
		}
		Map<String, ? extends Object> body = new HashMap<>();
		return ErrorUtil.successResp(body);
	}
	
	/**
	 * 构建参数字符串
	 * @param clazz
	 * @return
	 */
	public static String buildParamString (Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		JSONArray jsonArray = new JSONArray();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				if(Modifier.isStatic(field.getModifiers())) {
					ActionParam actionParam = field.getDeclaredAnnotation(ActionParam.class);
					Map<String, Object> paramMap = new HashMap<>();
					paramMap.put("name", field.getName());
					paramMap.put("value", actionParam.defaultValue());
					paramMap.put("desc", actionParam.desc());
					paramMap.put("length", actionParam.length());
					jsonArray.add(paramMap);
				}
			}
		}
		return jsonArray.toJSONString();
	}
	
	/**
	 * 根据规则编号查询动作Bean参数
	 * @param data
	 * @return
	 */
	public Map<String, ? extends Object> queryParamByUid(Map<String, ? extends Object> data) {
		Map<String, Object> body = new HashMap<>();
		Integer uid = ToolsUtil.obj2Int(data.get("uid"), null);
		if (uid == null || uid == 0) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, uid);
		}
		ConfRuleInfo rule = confRuleInfoMapper.selectByPrimaryKey(uid);
		body.put("param", JSONObject.parseArray(rule.getParam()));
		return ErrorUtil.successResp(body);
	}
	
}
