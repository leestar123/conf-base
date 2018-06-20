package com.conf.template.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conf.template.common.ToolsUtil;

/**
 * 配置节点控制器
 * 
 * @author li_mingxing
 *
 */
@RestController("node")
public class ConfNodeController {

	@RequestMapping(value="createNode", method=RequestMethod.POST)
	public Map<String, ? extends Object> createNode(Map<String, ? extends Object> data) {
		String nodeType = ToolsUtil.obj2Str(data.get("nodeType"));
		if (StringUtils.isBlank(nodeType)) {
			
		}
		return null;
	}
}
