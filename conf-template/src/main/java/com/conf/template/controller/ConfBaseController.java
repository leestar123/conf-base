package com.conf.template.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conf.client.CommController;
import com.conf.client.RuleInvokerService;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.annotation.ApiException;
import com.conf.common.dto.BuildXMlDto;
import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfNodeInfo;
import com.conf.template.db.model.ConfProductStep;
import com.conf.template.service.ConfBaseService;
import com.conf.template.service.ConfBeanService;
import com.conf.template.service.ConfFlowService;
import com.conf.template.service.ConfStepService;
import com.conf.template.service.NodeService;

@Component
public class ConfBaseController implements CommController
{
    private final static Logger logger = LoggerFactory.getLogger(ConfBaseController.class);
    
    @Autowired
    private ConfBaseService confBaseService;
    
    @Autowired
    private ConfBeanService confBeanService;
    
    @Autowired
    private NodeService nodeService;
    
    @Autowired
    private RuleInvokerService invokerService;
    
    @Autowired
    private ConfFlowService confFlowService;
    
    @Autowired
    private ConfStepService confStepService;
	
	public void setConfBaseService(ConfBaseService confBaseService) {
		this.confBaseService = confBaseService;
	}
	
	public void setConfBeanService(ConfBeanService confBeanService) {
		this.confBeanService = confBeanService;
	}
	
	public void setConfFlowService(ConfFlowService confFlowService) {
		this.confFlowService = confFlowService;
	}

	public void setConfStepService(ConfStepService confStepService) {
		this.confStepService = confStepService;
	}

	/**
	 * 阶段创建
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
    public Map<String, ? extends Object> createStep(Map<String, ? extends Object> data)
    {
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		String stepName = ToolsUtil.obj2Str(data.get("stepName"));
		String remark = ToolsUtil.obj2Str(data.get("remark"));
		String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        if (StringUtils.isBlank(nodeId))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
        }
        if (StringUtils.isBlank(stepName))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "stepName");
        }
        if (StringUtils.isBlank(remark))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "remark");
        }
        if (StringUtils.isBlank(teller))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "teller");
        }
        if (StringUtils.isBlank(org))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "org");
        }
        
        return confBaseService.createConfStepInfo(data);
    }
	
	/**
	 * 阶段查询
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryStep(Map<String, ? extends Object> data) {
				
		return confBaseService.queryStep(data);
	}
	
	/**
	 * 产品或业务类型绑定阶段流程
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
    @ApiException
    public Map<String, ? extends Object> addFlowByProduct(Map<String, ? extends Object> data)
    {
		String productName = ToolsUtil.obj2Str(data.get("productName"));
		Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
		String businessType = ToolsUtil.obj2Str(data.get("businessType"));
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        if (StringUtils.isBlank(productName))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "productName");
        }
        if (productId == null && StringUtils.isBlank(businessType))
        {
            if (StringUtils.isBlank(businessType)) {
                return ErrorUtil.errorResp(ErrorCode.code_0001, "businessType");
            } 
            else {
                return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
            }
        }
        if (StringUtils.isBlank(teller))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "teller");
        }
        if (StringUtils.isBlank(org))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "org");
        }
        
        Map<String, ? extends Object> result = confBaseService.addFlowByProduct(data);
        
        if (ErrorUtil.isSuccess(result)) {
            try
            {
                Map<String, Object> body = (Map<String, Object>)result.get("body");
                List<BuildXMlDto> bind = new ArrayList<>();
                JSONArray filter = JSONObject.parseArray(JSONObject.toJSONString(body.get("filter")));
                body.remove("filter");
                for (int i = 0; i < filter.size(); i++)
                {
                    Map map = JSONObject.parseObject(JSONObject.toJSONString(filter.get(i)), Map.class);
                    List<JSONObject> addList =
                        JSONObject.parseArray(JSONObject.toJSONString(map.get("bind")), JSONObject.class);
                    buildKnowledge(addList, bind, true, productId, businessType);
                }
                Map<String, Object> invoker = new HashMap<>();
                invoker.put("bind", bind);
                nodeService.publishKnowledge(invoker);
            } catch (Exception e) {
                
            }
        }
        return result;
    }
	
	/**
	 * 构建发布知识包内容
	 * 
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @param list
	 * @param result
	 * @param add
	 * @param productId
	 * @param businessType
	 * @see [类、类#方法、类#成员]
	 */
    private void buildKnowledge(List<JSONObject> list, List<BuildXMlDto> result, boolean add, Integer productId, String businessType)
    {
        for (JSONObject json : list)
        {
            try
            {
            	BuildXMlDto dto = new BuildXMlDto();
                Integer flowId = json.getInteger("flowId");
                ConfFlowInfo flowInfo = confBaseService.queryFlowById(flowId);
                if (flowInfo == null)
                    continue;
                ConfNodeInfo nodeInfo = nodeService.queryNodeByStepId(flowInfo.getStepId());
                if (nodeInfo == null)
                    continue;
				if (!invokerService.existCheck(flowInfo.getFlowPath())) {
					logger.error("File [" + flowInfo.getFlowPath() + "] not exists, skip!");
					continue;
				}
                Document doc = invokerService.getFileSource(flowInfo.getFlowPath());
                String processId = doc.getRootElement().attributeValue("id");
                dto.setProductId(productId + "");
                dto.setFlowId(processId);
                dto.setNodeName(nodeInfo.getNodeName());
                dto.setPath(flowInfo.getFlowPath());
                result.add(dto);
                if (add) {
                    ConfProductStep product = confBaseService.queryProductStep(flowId, flowInfo.getStepId(), productId, businessType);
                    if (product != null)
                    	dto.setProductId(product.getId() + "");
                }
            }
            catch (Exception e)
            {
                logger.error("build knowledge error!", e);
                continue;
            }
        }
    }
	/**
	 * 查询阶段列表
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryStepList(Map<String, ? extends Object> data) {
				
		return confBaseService.queryStepList(data);
	}
	
    @Override
    public String url()
    {
        return "/conf";
    }
	
	/**
	 * 节点查配置查询
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryNodeConfList(Map<String, ? extends Object> data) {
				
		return confBaseService.queryNodeConfList(data);
	}
	
	/**
	 * 创建流程
	 * 
	 * @param data
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    @ApiException
    public Map<String, ? extends Object> createFlow(Map<String, ? extends Object> data)
    {
        return confBaseService.createFlow(data);
    }
	
    /**
     * 流程查询
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    @ApiException
    public Map<String, ? extends Object> queryFlowList(Map<String, ? extends Object> data)
    {
        return confBaseService.queryFlowList(data);
    }
    
    /**
     * 分页流程查询
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    @ApiException
    public Map<String, ? extends Object> queryFlowListByPage(Map<String, ? extends Object> data)
    {
        return confBaseService.queryFlowListByPage(data);
    }
    
    /**
     * 流程明细查询
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    @ApiException
    public Map<String, ? extends Object> queryFlowDetail(Map<String, ? extends Object> data)
    {
        return confBaseService.queryFlowDetail(data);
    }
    
    /**
	 * 阶段流程列表查询
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryStepFlow(Map<String, ? extends Object> data) {
				
		return confBaseService.queryStepFlow(data);
	}
	
	 /**
	 * 操作日志表查询
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryOperateLog(Map<String, ? extends Object> data) {
				
		return confBaseService.queryOperateLog(data);
	}
	
	 /**
	 * 查看调用日志
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryInvokLog(Map<String, ? extends Object> data) {
				
		return confBaseService.queryInvokLog(data);
	}
	
    /**
     * 调用知识包
     */
    @ApiException
    public Map<String, ? extends Object> excuteKnowledge(Map<String, ? extends Object> data) {
        String flowId = ToolsUtil.obj2Str(data.get("productId"));
        String businessType = ToolsUtil.obj2Str(data.get("businessType"));
        if (StringUtils.isBlank(flowId) && StringUtils.isBlank(businessType))
        {
            if (StringUtils.isBlank(businessType)) {
                return ErrorUtil.errorResp(ErrorCode.code_0001, "businessType");
            } 
            else {
                return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
            }
        }
        return confBaseService.excuteKnowledge(data);
    }
    
    /**
     * 刷新知识包
     */
    @ApiException
    public Map<String, ? extends Object> refreshKnowledgeCacheByStepAndFlow(Map<String, ? extends Object> data) {
    	
    	return confBaseService.refreshKnowledgeCacheByStepAndFlow(data);
    }
    
    /**
     * 根据规则编号查询动作Bean参数
     */
    @ApiException
    public Map<String, ? extends Object> queryParamByUid(Map<String, ? extends Object> data) {
    	
    	return confBeanService.queryParamByUid(data);
    }
    
    /**
     * 配置ActionBean属性
     */
    @ApiException
    public Map<String, ? extends Object> confBeanField(Map<String, ? extends Object> data) {
    	
    	return confBeanService.confBeanField(data);
    }
    
    /**
     * 更新流程
     * 
     */
    @ApiException
    public Map<String, ? extends Object> updateFlow(Map<String, ? extends Object> data)
    {
        return confFlowService.updateByPrimaryKey(data);
    }
    
    /**
     * 删除流程
     * 
     */
    @ApiException
    public Map<String, ? extends Object> deleteFlow(Map<String, ? extends Object> data)
    {
        return confFlowService.deleteFlow(data);
    }
    
    /**
     * 更新阶段
     * 
     */
    @ApiException
    public Map<String, ? extends Object> updateStep(Map<String, ? extends Object> data)
    {
        return confStepService.updateByPrimaryKeySelective(data);
    }
    
    /**
     * 删除阶段
     * 
     */
    @ApiException
    public Map<String, ? extends Object> deleteStep(Map<String, ? extends Object> data)
    {
        return confStepService.deleteStep(data);
    }
    
    
}
