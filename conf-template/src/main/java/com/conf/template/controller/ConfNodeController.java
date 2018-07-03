package com.conf.template.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conf.client.CommController;
import com.conf.template.common.ErrorCode;
import com.conf.template.common.ErrorUtil;
import com.conf.template.common.ToolsUtil;
import com.conf.template.common.annotation.ApiException;
import com.conf.template.service.NodeService;

/**
 * 配置节点控制器
 * 
 * @author li_mingxing
 *
 */
@Component
public class ConfNodeController implements CommController{

    @Autowired
	private NodeService nodeService;
    
    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }

    /**
	 * 新增可用组件
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
    public Map<String, ? extends Object> createNode(Map<String, ? extends Object> data)
    {
        String nodeType = ToolsUtil.obj2Str(data.get("nodeType"));
        String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
        if (StringUtils.isBlank(nodeType))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeType");
        }
        if (StringUtils.isBlank(nodeName))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeName");
        }
        
        return nodeService.createNode(data);
    }
	
	/**
	 * 查询录入的可用组件列表
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryNodeList(Map<String, ? extends Object> data) {
				
		return nodeService.queryNodeList(data);
	}
	
	/**
	 * 删除已经定义好的组件
	 * @param data
	 * @return
	 */
	@ApiException                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
	public Map<String, ? extends Object> deleteNode(Map<String, ? extends Object> data) {
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));	
		if (StringUtils.isBlank(nodeId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
		}
		return nodeService.deleteNode(data);
	}
	
	/**
	 * 单个组件查看详情，包括里面涉及已绑定规则
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryRuleByNode(Map<String, ? extends Object> data) {
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		if (StringUtils.isBlank(nodeId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
		}		
		return nodeService.queryRuleByNode(data);
	}
	
	/**
	 * 查询系统中存在的规则
	 * 支持根据规则名称模糊查询
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryRuleList(Map<String, ? extends Object> data) {		
		return nodeService.queryRuleList(data);
	}
	
	/**
	 * 查询系统中存在的规则
	 * 支持根据规则名称模糊查询
	 * @param data
	 * @return
	 */
	@ApiException
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> addRuleByNode(Map<String, ? extends Object> data) {		
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		List<Map<String, Object>> ruleList = (List<Map<String, Object>>) data.get("ruleList");
		if (StringUtils.isBlank(nodeId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
		}
		if(ruleList.size()==0)
		{
			return ErrorUtil.errorResp(ErrorCode.code_0001, "ruleList");
		}
		
		return nodeService.addRuleByNode(data);
	}
	
	/**
	 * 单个组件关联规则删除
	 * 支持多条删除
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> deleteRuleByNode(Map<String, ? extends Object> data) {
		
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		@SuppressWarnings("unchecked")
		List<String> ruleList = (List<String>) data.get("ruleList");
		if (StringUtils.isBlank(nodeId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
		}
		if(ruleList.size()==0)
		{
			return ErrorUtil.errorResp(ErrorCode.code_0001, "ruleList");
		}
		return nodeService.deleteRuleByNode(data);
	}
	
	/**
	 * 根据产品编号查询已经绑定关系的组件列表
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		if (StringUtils.isBlank(productId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
		}
		return nodeService.queryNodeByProduct(data);
	}
	
	/**
	 * 保存产品关联的组件模板信息，同时保存组件模板中的的规则
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> addNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> nodeList = (List<Map<String, Object>>) data.get("nodeList");
		if (StringUtils.isBlank(productId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
		}
		if(nodeList == null || nodeList.size()==0)
		{
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeList");
		}
		return nodeService.addNodeByProduct(data);
	}
	
	/**
	 *  从产品信息中删除已经存在的组件信息
	 * 支持多条删除
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> deleteNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> nodeList = (List<Map<String, Object>>) data.get("nodeList");
		if (StringUtils.isBlank(productId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
		}
		if(nodeList.size()==0)
		{
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeList");
		}
		return nodeService.deleteNodeByProduct(data);
	
	}
	
	/**
	 *   批量查询已经绑定关系组件的产品列表
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> batchQueryNodeByProduct(Map<String, ? extends Object> data) {
		return nodeService.batchQueryNodeByProduct(data);
	}
	
	/**
	 *   根据之前产品绑定的规则，重新选择绑定的规则
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> modifyNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> ruleList = (List<Map<String, Object>>) data.get("ruleList");
		if (StringUtils.isBlank(productId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
		}
		if (StringUtils.isBlank(nodeId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
		}
		if(ruleList.size()==0)
		{
			return ErrorUtil.errorResp(ErrorCode.code_0001, "ruleList");
		}
		return nodeService.modifyNodeByProduct(data);
	}
	
    /**
     * 调用urule新增规则
     */
	@ApiException
    public Map<String, ? extends Object> createRule(Map<String, ? extends Object> data)
    {
        String ruleType = ToolsUtil.obj2Str(data.get("ruleType"));
        String ruleName = ToolsUtil.obj2Str(data.get("ruleName"));
        String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
        if (StringUtils.isBlank(ruleType)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "ruleType");
        }
        if (StringUtils.isBlank(ruleName)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "ruleName");
        }
        if (StringUtils.isBlank(nodeId)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
        }
        if (StringUtils.isBlank(teller)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "teller");
        }
        if (StringUtils.isBlank(org)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "org");
        }
        if (StringUtils.isBlank(nodeName)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeName");
        }
        
        return nodeService.createRule(data);
    }
}
