package com.conf.template.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.urule.console.repository.model.FileType;
import com.conf.client.CommController;
import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.annotation.ApiException;
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
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> deleteRuleByNode(Map<String, ? extends Object> data) {
		
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
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
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> addNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
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
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> deleteNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
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
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        if (StringUtils.isBlank(ruleType)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "ruleType");
        }
        if (StringUtils.isBlank(ruleName)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "ruleName");
        }
        if (StringUtils.isBlank(teller)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "teller");
        }
        if (StringUtils.isBlank(org)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "org");
        }
        
        return nodeService.createRule(data);
    }
	
    /**
     * 修改urule新增规则
     */
    @ApiException
    public Map<String, ? extends Object> modifyRule(Map<String, ? extends Object> data)
    {
        String ruleType = ToolsUtil.obj2Str(data.get("ruleType"));
        String rulePath = ToolsUtil.obj2Str(data.get("rulePath"));
        if (StringUtils.isBlank(ruleType)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "ruleType");
        }
        if (StringUtils.isBlank(rulePath)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "rulePath");
        }
        String url = Constants.RULE_URL_BASE;
        
        ruleType = ToolsUtil.unParse(ruleType);
        FileType fileType=FileType.parse(ruleType);
        if (FileType.DecisionTable == fileType) {
            url += "decisiontableeditor?file=" + rulePath;
        } else if (FileType.DecisionTree == fileType) {
            url += "decisiontreeeditor?file=" + rulePath;
        } else if (FileType.Ruleset == fileType) {
            url += "ruleseteditor?file=" + rulePath;
        } else if (FileType.Scorecard == fileType) {
            url += "scorecardeditor?file=" + rulePath;
        } else {}
        
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("url", url);
        return ErrorUtil.successResp(body);
    }
	
	/**
	 * 创建空决策流
	 */
	@ApiException
    public Map<String, ? extends Object> ruleflowDesigner(Map<String, ? extends Object> data)
    {
        String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
        String productName = ToolsUtil.obj2Str(data.get("productName"));
        String productId = ToolsUtil.obj2Str(data.get("productId"));
        String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
        if (StringUtils.isBlank(nodeName))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeName");
        }
        if (StringUtils.isBlank(productName))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "productName");
        }
        if (StringUtils.isBlank(productId))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
        }
        if (StringUtils.isBlank(nodeId))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
        }
        return nodeService.ruleflowdesigner(data);
    }

	/**
	 * 保存并发布知识吧
	 */
	@ApiException
	public Map<String, ? extends Object> publishKnowledge(Map<String, ? extends Object> data) {
        String flowId = ToolsUtil.obj2Str(data.get("flowId"));
        String path = ToolsUtil.obj2Str(data.get("flowPath"));
        //conf_product_step表中的id
        String productId = ToolsUtil.obj2Str(data.get("productId"));
        //对应工程名
        String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
        
		if (StringUtils.isBlank(nodeName)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeName");
        }
        if (StringUtils.isBlank(path)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "path");
        }
        if (StringUtils.isBlank(flowId)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "flowId");
        }
        if (StringUtils.isBlank(productId)) {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
        }
		return nodeService.publishKnowledge(data);
	}
	
	/**
	 * 查询动作规则
	 * 
	 * @param data
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@ApiException
    public Map<String, ? extends Object> queryActionRule(Map<String, ? extends Object> data)
    {
        //产品id
        String productId = ToolsUtil.obj2Str(data.get("productId"));
        //组件名称
        String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
        if (StringUtils.isBlank(productId))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
        }
        if (StringUtils.isBlank(nodeId))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
        }
        return nodeService.queryActionRule(data);
    }

	/**
	 * 刷新规则
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
    public Map<String, ? extends Object> refreshRule(Map<String, ? extends Object> data)
    {
        //规则ID
        String uid = ToolsUtil.obj2Str(data.get("uid"));
        if (StringUtils.isBlank(uid))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "uid");
        }
        return nodeService.refreshRule(data);
    }
	
	/**
	 * 删除规则
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
    public Map<String, ? extends Object> deleteRule(Map<String, ? extends Object> data)
    {
        //规则ID
        String uid = ToolsUtil.obj2Str(data.get("uid"));
        if (StringUtils.isBlank(uid))
        {
            return ErrorUtil.errorResp(ErrorCode.code_0001, "uid");
        }
        return nodeService.deleteRule(data);
    }
	
	
    @Override
    public String url()
    {
        return "/node";
    }
}
