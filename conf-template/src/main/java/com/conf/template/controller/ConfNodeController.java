package com.conf.template.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("node")
public class ConfNodeController {

	@Autowired
	private NodeService nodeService;
	
	/**
	 * 新增可用组件
	 * 
	 * @param data
	 * @return
	 */
	@ApiException
	@RequestMapping(value="createNode", method=RequestMethod.POST)
	public Map<String, ? extends Object> createNode(@RequestBody Map<String, ? extends Object> data) {
		String nodeType = ToolsUtil.obj2Str(data.get("nodeType"));
		String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
		if (StringUtils.isBlank(nodeType)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeType");
		}
		if (StringUtils.isBlank(nodeName)) {
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
	@RequestMapping(value="queryNodeList", method=RequestMethod.POST)
	public Map<String, ? extends Object> queryNodeList(@RequestBody Map<String, ? extends Object> data) {
				
		return nodeService.queryNodeList(data);
	}
	
	/**
	 * 删除已经定义好的组件
	 * @param data
	 * @return
	 */
	@ApiException                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
	@RequestMapping(value="deleteNode", method=RequestMethod.POST)
	public Map<String, ? extends Object> deleteNode(@RequestBody Map<String, ? extends Object> data) {
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
	@RequestMapping(value="queryRuleByNode", method=RequestMethod.POST)
	public Map<String, ? extends Object> queryRuleByNode(@RequestBody Map<String, ? extends Object> data) {
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
	@RequestMapping(value="queryRuleList", method=RequestMethod.POST)
	public Map<String, ? extends Object> queryRuleList(@RequestBody Map<String, ? extends Object> data) {		
		return nodeService.queryRuleList(data);
	}
	
	/**
	 * 查询系统中存在的规则
	 * 支持根据规则名称模糊查询
	 * @param data
	 * @return
	 */
	@ApiException
	@RequestMapping(value="addRuleByNode", method=RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> addRuleByNode(@RequestBody Map<String, ? extends Object> data) {		
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
	@RequestMapping(value="deleteRuleByNode", method=RequestMethod.POST)
	public Map<String, ? extends Object> deleteRuleByNode(@RequestBody Map<String, ? extends Object> data) {
		
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
	@RequestMapping(value="queryNodeByProduct", method=RequestMethod.POST)
	public Map<String, ? extends Object> queryNodeByProduct(@RequestBody Map<String, ? extends Object> data) {
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
	@RequestMapping(value="addNodeByProduct", method=RequestMethod.POST)
	public Map<String, ? extends Object> addNodeByProduct(@RequestBody Map<String, ? extends Object> data) {
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
		return nodeService.addNodeByProduct(data);
	}
	
	/**
	 *  从产品信息中删除已经存在的组件信息
	 * 支持多条删除
	 * @param data
	 * @return
	 */
	@ApiException
	@RequestMapping(value="deleteNodeByProduct", method=RequestMethod.POST)
	public Map<String, ? extends Object> deleteNodeByProduct(@RequestBody Map<String, ? extends Object> data) {
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
}
