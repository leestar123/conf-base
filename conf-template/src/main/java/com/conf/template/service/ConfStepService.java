package com.conf.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conf.client.RuleInvokerService;
import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.template.db.mapper.ConfStepInfoMapper;
import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfStepInfo;


@Service
public class ConfStepService
{
    
    
    @Autowired
    private ConfStepInfoMapper confStepInfoMapper;
    
    @Autowired
    private ConfBaseService confBaseService;
    
    @Autowired
    private RuleInvokerService invokerService;
    
    /**
	 * 更新流程
	 * 
	 * @param data
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    public Map<String, ? extends Object> updateByPrimaryKeySelective(Map<String, ? extends Object> data)
    {
    	Map<String, Object> body = new HashMap<>();
        Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null); 
        Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null); 
        String stepName = ToolsUtil.obj2Str(data.get("stepName")); 
        String remark = ToolsUtil.obj2Str(data.get("remark")); 
        String teller = ToolsUtil.obj2Str(data.get("teller")); 
        String org = ToolsUtil.obj2Str(data.get("org")); 
        Integer deleteFlag = ToolsUtil.obj2Int(data.get("deleteFlag"), null); 
        ConfStepInfo confStepInfo = new ConfStepInfo();
        confStepInfo.setStepId(stepId);
        confStepInfo.setNodeId(nodeId);
        confStepInfo.setStepName(stepName);
        confStepInfo.setRemark(remark);
        confStepInfo.setTeller(teller);
        confStepInfo.setOrg(org);
        confStepInfo.setDeleteFlag(deleteFlag);
        confStepInfoMapper.updateByPrimaryKeySelective(confStepInfo);
        body.put("stepId", stepId);
        return ErrorUtil.successResp(body);
    }

    /**
     * 删除流程
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional
	public Map<String, ? extends Object> deleteStep(Map<String, ? extends Object> data) {
		Map<String, Object> body = new HashMap<>();
		Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null); 
		String stepName = ToolsUtil.obj2Str(data.get("stepName"));
		if (stepId == null || "".equals(stepId)) {
			return ErrorUtil.errorResp(ErrorCode.code_0001, stepId);
		}
		// 判断该阶段下是否有关联流程，如果有，则不能删除
		Map<String, ? extends Object>  resultMap = confBaseService.queryFlowList(data);
		Map<String, Object> map = (Map<String, Object>) resultMap.get("body");
		List<ConfFlowInfo> flowList = (List<ConfFlowInfo>)map.get("list");
		if (flowList != null && !flowList.isEmpty()) {
			return ErrorUtil.errorResp(ErrorCode.code_0014, "请先解绑流程！");
		} else {
			// 查询节点
			String nodeName = confStepInfoMapper.queryNodeNameByStep(stepId);
			if (nodeName != null && !"".equals(nodeName)) {
				// 调用urule 删除节点
				String path = "/" + nodeName + "/" + nodeName + "-" + stepName;
				try {
					invokerService.deleteFile(path);
				} catch (Exception e) {
					return ErrorUtil.errorResp(ErrorCode.code_9999);
				}
			}
			ConfStepInfo confStepInfo = new ConfStepInfo();
	        confStepInfo.setStepId(stepId);
	        confStepInfo.setDeleteFlag(Constants.DELETE_STATUS_YES);
	        confStepInfoMapper.updateByPrimaryKeySelective(confStepInfo);
	        body.put("stepId", stepId);
	        return ErrorUtil.successResp(body);
		}
	}
}
