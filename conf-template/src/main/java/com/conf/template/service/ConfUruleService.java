package com.conf.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.common.Constants;
import com.conf.common.ToolsUtil;
import com.conf.template.db.mapper.ConfProductStepMapper;
import com.conf.template.db.mapper.ConfStepInfoMapper;
import com.conf.template.db.model.ConfProductStep;
import com.conf.template.db.model.ConfStepInfo;

/**
 * 调用urule相关服务
 * @author zhang_pengfei
 *
 */

@Service
public class ConfUruleService
{
    
    @Autowired
    private ConfBaseService confBaseService;
    
    @Autowired
    private ConfProductStepMapper confProductStepMapper;
    
    @Autowired
    private ConfStepInfoMapper confStepInfoMapper;
    
    /**
     * 批量调用知识包
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map<String, ? extends Object> excuteKnowledgeForList(Map<String, ? extends Object> data)
    {
    	Map<String, Object> returnMap = new HashMap<>();
    	Integer productId = ToolsUtil.obj2Int(data.get("creditProduct"), null);
        String businessType = ToolsUtil.obj2Str(data.get("businessLines"));
    	
    	// 映射阶段类型-阶段名称码值
    	String stepName = getStepNameByStageType(ToolsUtil.obj2Str(data.get("stageType")));
    	
    	// 根据 stepName 查询step_info 表 获得 stepId
    	ConfStepInfo  confStepInfo  = confStepInfoMapper.queryStepIdByStepName(stepName);
    	int stepId = confStepInfo.getStepId();
    	
    	// 根据stepId 、 businessType 、 productId 查询conf_product_step 获得flowId
    	ConfProductStep  confProductStep  = confProductStepMapper.queryFlowIdByCondition(stepId, productId, businessType);
    	
    	// 构建查询参数
    	Map<String, Object> queryMap = bulidParamMap(data, stepId, confProductStep);
    	
    	// 循环调用urule知识包
    	List<Map<String, Object>> userInfoArray = (List<Map<String, Object>>)data.get("userInfoArray"); // 用户信息组
    	List<Map<String, ? extends Object>> resultList = excuteKnowledgeForEach(userInfoArray, queryMap);
        returnMap.put("resultList", resultList);
		return returnMap;
    }
    
    /**
     * 循环调用urule知识包
     * @param userInfoArray
     * @param queryMap
     * @return
     */
    private List<Map<String, ? extends Object>> excuteKnowledgeForEach(List<Map<String, Object>> userInfoArray, Map<String, Object> queryMap) {
    	List<Map<String, ? extends Object>> resultList = new ArrayList<>();
    	String identityIsTrue = ToolsUtil.obj2Str(queryMap.get("identityIsTrue"));
    	userInfoArray.stream().forEach( userInfo -> {
     	 	List<Map<String, Object>> objList = new ArrayList<>();
          	Map<String, Object> objMap = new HashMap<>();
          	objMap.put("key", Constants.QUALITY_INFO_STR);
          	objMap.put("IDENTITY_IS_TRUE", identityIsTrue); // 是否资质审查 0-需要
          	objMap.put("CLIENT_NAME", userInfo.get("custName")); // 客户名称
          	objMap.put("BANK_NO", userInfo.get("acountNo")); // 银行卡号
          	objMap.put("CLIENT_NO", userInfo.get("custNo")); // 客户号
          	objMap.put("GLOBAL_TYPE", userInfo.get("globalType")); // 证件类型
          	objMap.put("GLOBAL_ID", userInfo.get("globalId")); // 证件号码
          	objMap.put("PHONE_NO", userInfo.get("telphoneNo")); // 手机号
          	objMap.put("CLIENT_TYPE_CODE", userInfo.get("custType")); // 客户类型 01-借款人 02-共同借款人 03-担保人
          	objList.add(objMap);
          	queryMap.put("objList", objList);
          	Map<String, ? extends Object> resultMap = confBaseService.excuteKnowledge(queryMap);
          	resultList.add(resultMap);
         });
		return resultList;
	}

	/**
     * 构建查询参数
     * @param data
     * @param stepId
     * @param confProductStep
     * @return
     */
    private Map<String, Object> bulidParamMap(Map<String, ? extends Object> data, int stepId,
			ConfProductStep confProductStep) {
    	Map<String, Object> queryMap = new HashMap<>();
    	queryMap.putAll(data);
    	queryMap.put("stepId", stepId);
    	queryMap.put("productId", data.get("creditProduct"));
    	queryMap.put("businessType", data.get("businessLines"));
    	queryMap.put("flowId", confProductStep.getFlowId());
    	queryMap.put("teller", data.get("teller")); // 交易柜员
    	queryMap.put("org", data.get("org")); // 交易机构
		return queryMap;
	}

	/**
     * 映射阶段类型码值
     * @param stageType
     * @return
     */
    private String getStepNameByStageType(String stageType) {
    	String stepName = "";
    	if (Constants.STAGE_TYPE_01.equals(stageType)) {
    		stepName = "准入筛选";
    	} else if (Constants.STAGE_TYPE_02.equals(stageType)) {
    		stepName = "预筛选";
    	} else if (Constants.STAGE_TYPE_03.equals(stageType)) {
    		stepName = "信审评分";
    	}
		return stepName;
	}
}
