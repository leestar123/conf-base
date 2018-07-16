package com.conf.template.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.annotation.ApiException;
import com.conf.template.service.ConfBaseService;

@Component
public class ConfBaseController
{
	@Autowired
	private ConfBaseService confBaseService;
	
	/**
	 * ½×¶Î´´½¨
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
	 * ½×¶Î²éÑ¯
	 * @param data
	 * @return
	 */
	@ApiException
	public Map<String, ? extends Object> queryStep(Map<String, ? extends Object> data) {
				
		return confBaseService.queryStep(data);
	}
}
