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
	public Map<String, ? extends Object> addRuleByNode(@RequestBody Map<String, ? extends Object> data) {		
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
		return nodeService.queryRuleList(data);
	}
	
}
