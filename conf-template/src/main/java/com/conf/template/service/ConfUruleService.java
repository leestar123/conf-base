package com.conf.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.conf.application.FileThreadPoolExecutor;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.console.repository.model.FileType;
import com.bstek.urule.console.repository.model.ResourcePackage;
import com.bstek.urule.model.GeneralEntity;
import com.conf.client.RuleInvokerService;
import com.conf.common.ConfContext;
import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.process.InvokerAopProcess;
import com.conf.template.db.mapper.ConfFlowInfoMapper;
import com.conf.template.db.mapper.ConfInvokInfoMapper;
import com.conf.template.db.mapper.ConfNodeInfoMapper;
import com.conf.template.db.mapper.ConfProductStepMapper;
import com.conf.template.db.mapper.ConfStepInfoMapper;
import com.conf.template.db.mapper.QualificationReviewInfoMapper;
import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfInvokInfo;
import com.conf.template.db.model.ConfNodeInfo;
import com.conf.template.db.model.ConfProductStep;
import com.conf.template.db.model.ConfStepInfo;
import com.conf.template.db.model.QualificationReviewInfo;

/**
 * 调用urule相关服务
 * @author zhang_pengfei
 *
 */

@Service
public class ConfUruleService
{
    private final static Logger logger = LoggerFactory.getLogger(ConfUruleService.class);
    
    private static Map<String, String> clazzMap = new HashMap<>();
    
    @Autowired
    private ConfProductStepMapper confProductStepMapper;
    
    @Autowired
    private ConfStepInfoMapper confStepInfoMapper;
    
    @Autowired
    private InvokerAopProcess invokerAopProcess;
    
    @Autowired
    private ConfNodeInfoMapper confNodeInfoMapper;
    
    @Autowired
    private ConfFlowInfoMapper confFlowInfoMapper;
    
    @Autowired
    private ConfInvokInfoMapper confInvokInfoMapper;
    
    @Autowired
    private RuleInvokerService invokerService;
    
    @Autowired
    private FileThreadPoolExecutor excutor;
    
    @Autowired
    private QualificationReviewInfoMapper qualificationReviewInfoMapper;
    
    
    /**
     * 批量调用知识包
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map<String, ? extends Object> excuteKnowledgeForList(Map<String, ? extends Object> data)
    {
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
    	Map<String, ? extends Object> body = excuteKnowledgeForEach(userInfoArray, queryMap);
		return ErrorUtil.successResp(body);
    }
    
    /**
     * 循环调用urule知识包
     * @param userInfoArray
     * @param queryMap
     * @return
     */
    private Map<String, ? extends Object> excuteKnowledgeForEach(List<Map<String, Object>> userInfoArray, Map<String, Object> queryMap) {
    	Map<String, Object>  body = new HashMap<>();
    	List<Map<String, ? extends Object>> resultList = new ArrayList<>();
    	String identityIsTrue = ToolsUtil.obj2Str(queryMap.get("identityIsTrue"));
    	Long time = System.currentTimeMillis();
    	body.put("dateTime", time);
    	body.put("result", Constants.QUALITY_RESULT_SUCCESS);
    	
    	List<Map<String, Object>> objList = new ArrayList<>();
    	for (Map<String, Object> userInfo : userInfoArray) {
          	Map<String, Object> objMap = new HashMap<>();
          	String custType = ToolsUtil.obj2Str(userInfo.get("custType"));
          	objMap.put("key", Constants.QUALITY_INFO_STR);
          	objMap.put("IDENTITY_IS_TRUE", identityIsTrue); // 是否资质审查 0-需要
          	objMap.put("CLIENT_NAME", userInfo.get("custName")); // 客户名称
          	objMap.put("BANK_NO", userInfo.get("acountNo")); // 银行卡号
          	objMap.put("CLIENT_NO", userInfo.get("custNo")); // 客户号
          	objMap.put("GLOBAL_TYPE", userInfo.get("globalType")); // 证件类型
          	objMap.put("GLOBAL_ID", userInfo.get("globalId")); // 证件号码
          	objMap.put("PHONE_NO", userInfo.get("telphoneNo")); // 手机号
          	objMap.put("CLIENT_TYPE_CODE", custType); // 客户类型 1-借款人 2-共同借款人 3-担保人
          	objMap.put("IF_RATEING", false); // 是否评分评级
          	String stageType = ToolsUtil.obj2Str(queryMap.get("stageType"));
          	String businessType = ToolsUtil.obj2Str(queryMap.get("businessType"));
          	String stage = "";
          	if (Constants.STAGE_TYPE_02.equals(stageType)) {
          		if (Constants.BUSINESS_TYEP_CREDIT.equals(businessType)) {
          			stage = Constants.SCORE_LEVEL_CREDIT_PRE;
          		} else {
          			stage = Constants.SCORE_LEVEL_COMMON_PRE;
          		}
          	} else if (Constants.STAGE_TYPE_03.equals(stageType)) {
          		if (Constants.BUSINESS_TYEP_CREDIT.equals(businessType)) {
          			stage = Constants.SCORE_LEVEL_CREDIT_TRIAL;
          		} else if (Constants.BUSINESS_TYEP_LOAN.equals(businessType)) {
          			stage = Constants.SCORE_LEVEL_LOAN_TRIAL;
          		} else {
          			stage = Constants.SCORE_LEVEL_COMMON_PRE;
          		}
          	} else {}
          	objList.add(objMap);
          	queryMap.put("objList", objList);
          	
      		objMap.put("STAGE", stage);
          	Map<String, ? extends Object> resultMap = excuteKnowledge(ToolsUtil.obj2Str(userInfo.get("custNo")), custType, queryMap);
          	if (ErrorUtil.isSuccess(resultMap))
          	{
          		if (Constants.CUST_TYPE_LOAN.equals(custType))
          		{
          			body.put("lastScore", ErrorUtil.getBody(resultMap).get("lastScore"));//系统评分
          			body.put("sysAdvice", ErrorUtil.getBody(resultMap).get("sysAdvice"));//系统建议额度
          			body.put("riskCode", ErrorUtil.getBody(resultMap).get("riskCode"));//风险评级
          			body.put("investType", ErrorUtil.getBody(resultMap).get("investType"));//调查方式
          			body.put("reportType", ErrorUtil.getBody(resultMap).get("reportType"));//报表编制
          			body.put("lossLevel", ErrorUtil.getBody(resultMap).get("lossLevel"));//流失等级
          			body.put("loanAdvice", ErrorUtil.getBody(resultMap).get("loanAdvice"));//贷款额度
          			body.put("loanRate", ErrorUtil.getBody(resultMap).get("loanRate"));//贷款利率
          		}
          		String reviewResult = ToolsUtil.obj2Str(resultMap.get("qualificationReviewResult"));
          		if (Constants.QUALITY_RESULT_FAIL.equals(reviewResult)) 
          		{
          			body.put("result", Constants.QUALITY_RESULT_FAIL);
          		}
          		ErrorUtil.getBody(resultMap).putAll(userInfo);
          		resultList.add(ErrorUtil.getBody(resultMap));
           	}
    	}
    	body.put("evalArray", resultList);
		return body;
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
    	if (confProductStep != null) {
    		queryMap.put("flowId", confProductStep.getFlowId());
    	}
    	queryMap.put("teller", data.get("teller")); // 交易柜员
    	queryMap.put("org", data.get("org")); // 交易机构
    	queryMap.put("stageType", data.get("stageType"));
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
    		stepName = "准入筛选阶段";
    	} else if (Constants.STAGE_TYPE_02.equals(stageType)) {
    		stepName = "预筛选阶段";
    	} else if (Constants.STAGE_TYPE_03.equals(stageType)) {
    		stepName = "信审评分阶段";
    	}
		return stepName;
	}
    
    public Map<String, ? extends Object> excuteKnowledge(Map<String, ? extends Object> data)
    {
    	return excuteKnowledge(null, null, data);
    }
    
    /**
     * 调用知识包
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, ? extends Object> excuteKnowledge(String custNo, String custType, Map<String, ? extends Object> data)
    {
        logger.info("Begin to excute knowledge service!");
        
        Map<String, Object> body = new HashMap<>();
        Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
        String businessType = ToolsUtil.obj2Str(data.get("businessType"));
        Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null);
        Integer flowId = ToolsUtil.obj2Int(data.get("flowId"), null);
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        Integer doTest = ToolsUtil.obj2Int(data.get("doTest"), 0);
        if ( doTest == 1) {// 仿真测试数据不入库
        	//invokerAopProcess = new TestQualificationInvokerAopProcess();
        }
        ConfProductStep product = confProductStepMapper.queryIdByCondition(stepId, flowId, productId, businessType);
        if (product == null)
            return ErrorUtil.errorResp(ErrorCode.code_0011, flowId);
        
        ConfFlowInfo flowInfo = confFlowInfoMapper.selectByPrimaryKey(flowId);
        if (flowInfo == null) 
        {
            return ErrorUtil.errorResp(ErrorCode.code_0009, flowId);
        }
        ConfNodeInfo nodeInfo = confNodeInfoMapper.queryNodeByStep(flowInfo.getStepId());
        if (nodeInfo == null)
        {
            return ErrorUtil.errorResp(ErrorCode.code_0010, flowInfo.getStepId());
        }
        
        ConfInvokInfo invokInfo = new ConfInvokInfo();
        invokInfo.setRequest(JSONObject.toJSONString(data));
        invokInfo.setService(flowInfo.getFlowName());
        invokInfo.setSuccess(Constants.EXCUTE_STATUS_FAIL);
        invokInfo.setTeller(teller);
        invokInfo.setOrg(org);
        
        try
        {
        	invokerAopProcess.beforeProcess(data);
            List<GeneralEntity> entityList = new ArrayList<>();
            List<Map<String, Object>> objList = new ArrayList<>();
            Object obj = data.get("objList");
            if (obj != null && List.class.isInstance(obj))
            {
                //此处必须为实体对象
                objList = (List<Map<String, Object>>)obj;
                for (Map<String, Object> map : objList)
                {
                    String key = ToolsUtil.obj2Str(map.get("key"));
                    map.remove("key");
                    if (StringUtils.isBlank(clazzMap.get(key))) {
                        buildKnowledgeObject("/" + nodeInfo.getNodeName(), key);
                    }
                    String clazz = clazzMap.get(key);
                    GeneralEntity entity = new GeneralEntity(clazz);
                    for (String set : map.keySet())
                    {
                        entity.put(set, map.get(set));
                    }
                    entityList.add(entity);
                }
            }
            logger.info("Excute knowledge service actually, file is [" + flowInfo.getFlowPath() + "]!");
            Document doc = invokerService.getFileSource(flowInfo.getFlowPath());
            logger.debug("After parase flow document");
            String processId = doc.getRootElement().attributeValue("id");

            logger.debug("Begin to excute Konwledge");
            String files = "jcr:".concat(flowInfo.getFlowPath()).concat(",LATEST");
            Map<String, Object> params = invokerService.executeProcess(files, entityList, processId);
      		invokerAopProcess.afterPorcess(custNo, custType, data, params);
      		body.putAll(params);
            logger.info("End to excute knowledge service");
            invokInfo.setDetail(ConfContext.invokerLocalGet());
            invokInfo.setSuccess(Constants.EXCUTE_STATUS_SUCCESS);
        }
        catch (Exception e)
        {
        	invokInfo.setErrorMessage(e.getMessage());
            logger.error("Excute knowledge [" + flowInfo.getFlowPath() + "] failly!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
        }
        finally
        {
            try
            {
            	confInvokInfoMapper.insertSelective(invokInfo);
            }
            catch (Exception e)
            {
                logger.warn("调用日志表插入失败！", e);
            }
        }
        return ErrorUtil.successResp(body);
    }
    
    @SuppressWarnings("unchecked")
    private synchronized void buildKnowledgeObject (String path, String key) throws Exception {
        if (StringUtils.isNotBlank(clazzMap.get(key)))
            return;
        
        //TODO:遍历项目下的变量库
        FileType[] types = new FileType[] {FileType.VariableLibrary};
        List<String> fileList = invokerService.getDirectories(path, types, null);
        for (String file : fileList)
        {
            Document doc = invokerService.getFileSource(file);
            List<Element> elements = doc.getRootElement().elements("category");
            for (Element element : elements)
            {
                String clazz = element.attributeValue("clazz");
                String newkey = clazz.substring(clazz.lastIndexOf(".") + 1, clazz.length());
                clazzMap.put(newkey, clazz);
                
            }
        }
    }
    
    /**
     * 刷新知识包
     * @param data
     * @return
     */
    public Map<String, ? extends Object> refreshKnowledgeCacheByStepAndFlow(Map<String, ? extends Object> data)
    {
    	Map<String, Object> body = new HashMap<>();
    	Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null);
    	Integer flowId = ToolsUtil.obj2Int(data.get("flowId"), null);
    	List<ConfProductStep> productList = confProductStepMapper.queryListByStepAndFlow(stepId, flowId);
    	if (productList == null || productList.isEmpty()) {
    		return ErrorUtil.successResp(body);
    	}
    	String nodeName = confNodeInfoMapper.queryNodeNameByStepId(stepId);
    	ConfFlowInfo confFlowInfo = confFlowInfoMapper.selectByPrimaryKey(flowId);
    	String path = confFlowInfo.getFlowPath();
    	
    	// 判断是否空文件，空文件不做任何处理
    	Document doc;
		try {
			
			doc = invokerService.getFileSource(path);
			String processId = doc.getRootElement().attributeValue("id");
	    	if (processId != null) {
	    		String files = path.startsWith("/") ? "jcr:" + path : "jcr:/" + path;
		        logger.info("Begin to refresh packages, file path is [" + files + "] !");
		    	productList.stream().forEach(product -> {
					try {
						boolean addFlag = false;
						List<ResourcePackage> list = invokerService.loadProjectResourcePackages(nodeName);
						for (ResourcePackage resourcePackage : list) {
							if (resourcePackage.getId().equals(product.getProductId() + "")) {
								addFlag = true;
								break;
							}
						}
						if (!addFlag) {
							logger.info("Konwledge doesn`t exist,then publish the konwledge now!");
							invokerService.generateRLXML("/" + nodeName, product.getProductId() + "", flowId + "", path, list);
							String xml = invokerService.buildXML(list).toString();
							invokerService.saveResourcePackages(nodeName, xml);
						}
						invokerService.refreshKnowledgeCache(files, product.getProductId().toString(), nodeName);
					} catch (Exception e) {
						logger.error("Publish knowledge [" + path + "] failly!", e);
			            return;
					}
		    	});
	    	}
		} catch (Exception e1) {
			logger.error("Publish knowledge [" + path + "] failly!", e1);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
    	logger.info("End to refresh packages!");
        return ErrorUtil.successResp(body);
    } 
    
	/**
	 * 批量执行资质审查
	 * 
	 * @param data
	 * @return
	 */
	public Map<String, ? extends Object> batchExecuteQuality(Map<String, ? extends Object> data) {
		excutor.doExecutor(data);
		Map<String, Object> body = new HashMap<>();
		return ErrorUtil.successResp(body);
	}
	
	/**
	 * 查询资质审查列表
	 * 
	 * @param data
	 * @return
	 */
	public Map<String, ? extends Object> queryQualificationReviewInfoList(Map<String, ? extends Object> data) {
		String custNo = ToolsUtil.obj2Str(data.get("custNo"));
		String custName = ToolsUtil.obj2Str(data.get("custName"));
		String advocateManagePerson = ToolsUtil.obj2Str(data.get("advocateManagePerson"));
		String tellerOrg = ToolsUtil.obj2Str(data.get("tellerOrg"));
		String startDate = ToolsUtil.obj2Str(data.get("startDate"));
		String endDate = ToolsUtil.obj2Str(data.get("endDate"));
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10); // 分页大小
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);// 当前页数
		int startNum = (pageNum - 1) * pageSize;
		List<QualificationReviewInfo> list = qualificationReviewInfoMapper.queryQualificationReviewInfoList(
	    		custNo, custName, advocateManagePerson, tellerOrg, startDate, endDate, startNum, pageSize);
		int totalNum = qualificationReviewInfoMapper.queryCount(
				custNo, custName, advocateManagePerson, tellerOrg, startDate, endDate, startNum, pageSize);
		Map<String, Object> body = new HashMap<>();
		body.put("list", list);
		body.put("totalNum", totalNum);
		return ErrorUtil.successResp(body);
	}
	
	
}
