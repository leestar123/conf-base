package com.conf.template.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conf.template.common.ErrorCode;
import com.conf.template.common.ErrorUtil;
import com.conf.template.common.ToolsUtil;
import com.conf.template.service.NodeService;

/**
 * 配置节点控制器
 * 
 * @author li_mingxing
 *
 */
@RestController("node")
public class ConfNodeController {

	@Autowired
	private NodeService nodeService;
	
	/**
	 * 新增可用组件
	 * 
	 * @param data
	 * @return
	 */
	@RequestMapping(value="createNode", method=RequestMethod.POST)
	public Map<String, ? extends Object> createNode(Map<String, ? extends Object> data) {
		String nodeType = ToolsUtil.obj2Str(data.get("nodeType"));
		if (StringUtils.isBlank(nodeType)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001);
		}
		return nodeService.createNode(data);
	}
}
