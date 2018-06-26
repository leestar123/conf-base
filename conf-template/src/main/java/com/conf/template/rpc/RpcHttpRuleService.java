package com.conf.template.rpc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conf.template.common.ErrorCode;
import com.conf.template.common.ErrorUtil;
import com.conf.template.common.ToolsUtil;
import com.conf.template.common.annotation.ApiException;
import com.conf.template.service.RuleService;

@RestController
@RequestMapping("service")
public class RpcHttpRuleService {

	@Autowired
	private RuleService ruleService;
	
	@ApiException
	@RequestMapping(value="doRule", method=RequestMethod.POST)
	public Map<String, ? extends Object> doRule(@RequestBody Map<String, ? extends Object> data) {
		Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
		if (productId == null) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "productId");
		}
		Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null);
		if (nodeId == null) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, "nodeId");
		}
		ToolsUtil.threadLocalSet(data);
		ruleService.doRule(productId, nodeId);
		return null;
	}
}
