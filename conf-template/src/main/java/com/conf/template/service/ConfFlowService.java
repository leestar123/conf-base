package com.conf.template.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.common.Constants;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.template.db.mapper.ConfFlowInfoMapper;
import com.conf.template.db.model.ConfFlowInfo;


@Service
public class ConfFlowService
{
    
    
    @Autowired
    private ConfFlowInfoMapper confFlowInfoMapper;
    
    
    /**
	 * 更新流程
	 * 
	 * @param data
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    public Map<String, ? extends Object> updateByPrimaryKey(Map<String, ? extends Object> data)
    {
    	Map<String, Object> body = new HashMap<>();
        Integer flowId = ToolsUtil.obj2Int(data.get("flowId"), null); 
        Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null); 
        String flowName = ToolsUtil.obj2Str(data.get("flowName")); 
        String flowPath = ToolsUtil.obj2Str(data.get("flowPath")); 
        String remark = ToolsUtil.obj2Str(data.get("remark")); 
        String teller = ToolsUtil.obj2Str(data.get("teller")); 
        String org = ToolsUtil.obj2Str(data.get("org")); 
        Integer deleteFlag = ToolsUtil.obj2Int(data.get("deleteFlag"), null); 
        ConfFlowInfo confFlowInfo = new ConfFlowInfo();
        confFlowInfo.setFlowId(flowId);
        confFlowInfo.setStepId(stepId);
        confFlowInfo.setFlowName(flowName);
        confFlowInfo.setFlowPath(flowPath);
        confFlowInfo.setRemark(remark);
        confFlowInfo.setTeller(teller);
        confFlowInfo.setOrg(org);
        confFlowInfo.setDeleteFlag(deleteFlag);
        confFlowInfoMapper.updateByPrimaryKey(confFlowInfo);
        body.put("flowId", flowId);
        return ErrorUtil.successResp(body);
    }

    /**
     * 删除流程
     * @param data
     * @return
     */
	public Map<String, ? extends Object> deleteFlow(Map<String, ? extends Object> data) {
		Map<String, Object> body = new HashMap<>();
        Integer flowId = ToolsUtil.obj2Int(data.get("flowId"), null); 
        ConfFlowInfo confFlowInfo = new ConfFlowInfo();
        confFlowInfo.setFlowId(flowId);
        confFlowInfo.setDeleteFlag(Constants.DELETE_STATUS_YES);
        confFlowInfoMapper.updateByPrimaryKey(confFlowInfo);
        body.put("flowId", flowId);
        return ErrorUtil.successResp(body);
	}
   
}
