package com.conf.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.conf.application.FileThreadPoolExecutor;
import org.conf.application.client.InvokerESBServer;
import org.conf.application.client.dto.LossWarningReq;
import org.conf.application.client.dto.LossWarningRes;
import org.conf.application.client.dto.ModelSystemReq;
import org.conf.application.client.dto.ModelSystemRes;
import org.conf.application.client.dto.QuotaPriceReq;
import org.conf.application.client.dto.QuotaPriceRes;
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
import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfInvokInfo;
import com.conf.template.db.model.ConfNodeInfo;
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
    	userInfoArray.stream().forEach( userInfo -> {
     	 	List<Map<String, Object>> objList = new ArrayList<>();
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
          	objMap.put("CLIENT_TYPE_CODE", custType); // 客户类型 01-借款人 02-共同借款人 03-担保人
          	objList.add(objMap);
          	queryMap.put("objList", objList);
          	Map<String, ? extends Object> resultMap = excuteKnowledge(ToolsUtil.obj2Str(userInfo.get("custNo")), queryMap);
          	if (ErrorUtil.isSuccess(resultMap))
          	{
          		if (Constants.CUST_TYPE_LOAN.equals(custType))
          		{
          			body.put("lastScore", resultMap.get("lastScore"));//系统评分
          			body.put("sysAdvice", resultMap.get("sysAdvice"));//系统建议额度
          			body.put("riskCode", resultMap.get("riskCode"));//风险评级
          			body.put("investType", resultMap.get("investType"));//调查方式
          			body.put("reportType", resultMap.get("reportType"));//报表编制
          			body.put("lossLevel", resultMap.get("lossLevel"));//流失等级
          			body.put("loanAdvice", resultMap.get("loanAdvice"));//贷款额度
          			body.put("loanRate", resultMap.get("loanRate"));//贷款利率
          		}
          		String reviewResult = ToolsUtil.obj2Str(resultMap.get("qualificationReviewResult"));
          		if (Constants.QUALITY_RESULT_FAIL.equals(reviewResult)) 
          		{
          			body.put("result", Constants.QUALITY_RESULT_FAIL);
          		}
          		ErrorUtil.getBody(resultMap).putAll(userInfo);
          		resultList.add(ErrorUtil.getBody(resultMap));
           	}
         });
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
    	queryMap.put("flowId", confProductStep.getFlowId());
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
    	return excuteKnowledge(null, data);
    }
    
    /**
     * 调用知识包
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, ? extends Object> excuteKnowledge(String custNo, Map<String, ? extends Object> data)
    {
        logger.info("Begin to excute knowledge service!");
        
        Map<String, Object> body = new HashMap<>();
        Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
        String businessType = ToolsUtil.obj2Str(data.get("businessType"));
        Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null);
        Integer flowId = ToolsUtil.obj2Int(data.get("flowId"), null);
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
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
      		//如果是预筛选阶段，则需要特殊处理
      		if (Constants.STAGE_TYPE_02.equals(ToolsUtil.obj2Str(data.get("stageType"))))
      		{
      			ModelSystemReq modelSystem = new ModelSystemReq();
      			modelSystem.setCustNo(custNo);
      			ModelSystemRes modelSystemRes =InvokerESBServer.modelSystem(modelSystem);
      			LossWarningReq lossWarn = new LossWarningReq();
      			lossWarn.setCustNo(custNo);
      			LossWarningRes lossWarningRes = InvokerESBServer.lossWarning(lossWarn);
      			QuotaPriceReq quota = new QuotaPriceReq();
      			quota.setCLIENT_NO(custNo);
      			QuotaPriceRes quotaProces = InvokerESBServer.quotaPrice(quota);
      			buildParam(modelSystemRes, lossWarningRes, quotaProces, params);
      		}
      		body.putAll(params);
            invokerAopProcess.afterPorcess(params);
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
    
    /**
     * 根据模型系统、流失预警、定额定价对象，构建返回参数
     * 
     * @param modelSystemRes
     * @param lossWarningRes
     * @param quotaProces
     * @param params
     */
	private void buildParam(ModelSystemRes modelSystemRes, LossWarningRes lossWarningRes, QuotaPriceRes quotaProces,
			Map<String, Object> params) {
		if (modelSystemRes != null && !modelSystemRes.getStrategyList().isEmpty())
		{
			//调查方式
			params.put("investType", modelSystemRes.getStrategyList().get(0).getInvestType());
			//报表编制
			params.put("reportType", modelSystemRes.getStrategyList().get(0).getReportType());
		}
		if (lossWarningRes != null && !lossWarningRes.getStrategyList().isEmpty())
		{
			//流失等级
			params.put("lossLevel", lossWarningRes.getStrategyList().get(0).getLossLevel());
		}
		if (quotaProces != null )
		{
			//贷款额度
			params.put("loanAdvice", quotaProces.getSYS_ADVICE());
			//贷款利率
			params.put("loanRate", quotaProces.getSYS_RATE());
		}
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
}
