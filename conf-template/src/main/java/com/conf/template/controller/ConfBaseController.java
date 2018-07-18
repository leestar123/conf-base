package com.conf.template.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conf.client.CommController;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.annotation.ApiException;
import com.conf.template.service.ConfBaseService;

@Component
public class ConfBaseController implements CommController
{
	@Autowired
	private ConfBaseService confBaseService;
	
	public void setConfBaseService(ConfBaseService confBaseService) {
		this.confBaseService = confBaseService;
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
	@ApiException
    public Map<String, ? extends Object> addFlowByProduct(Map<String, ? extends Object> data)
    {
		String productName = ToolsUtil.obj2Str(data.get("productName"));
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		String businessType = ToolsUtil.obj2Str(data.get("businessType"));
		String stepId = ToolsUtil.obj2Str(data.get("stepId"));
        String flowId = ToolsUtil.obj2Str(data.get("flowId"));
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        if (StringUtils.isBlank(productName))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "productName");
        }
        if (StringUtils.isBlank(productId))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
        }
        if (StringUtils.isBlank(businessType))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "businessType");
        }
        if (StringUtils.isBlank(stepId))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "stepId");
        }
        if (StringUtils.isBlank(flowId))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "flowId");
        }
        if (StringUtils.isBlank(teller))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "teller");
        }
        if (StringUtils.isBlank(org))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "org");
        }
        
        return confBaseService.addFlowByProduct(data);
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
}
