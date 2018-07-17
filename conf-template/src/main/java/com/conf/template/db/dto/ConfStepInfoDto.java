package com.conf.template.db.dto;

import java.util.Date;

import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfNodeInfo;
import com.conf.template.db.model.ConfRuleInfo;

public class ConfStepInfoDto
{
	private Integer stepId;

    private Integer nodeId;

    private String stepName;

    private String remark;

    private String teller;

    private String org;

    private Date createTime;

    private Date updateTime;
    
    ConfNodeInfo confNodeInfo;
    
    ConfFlowInfo confFlowInfo;
    
    ConfRuleInfo confRuleInfo;

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTeller() {
		return teller;
	}

	public void setTeller(String teller) {
		this.teller = teller;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public ConfNodeInfo getConfNodeInfo() {
		return confNodeInfo;
	}

	public void setConfNodeInfo(ConfNodeInfo confNodeInfo) {
		this.confNodeInfo = confNodeInfo;
	}

	public ConfFlowInfo getConfFlowInfo() {
		return confFlowInfo;
	}

	public void setConfFlowInfo(ConfFlowInfo confFlowInfo) {
		this.confFlowInfo = confFlowInfo;
	}

	public ConfRuleInfo getConfRuleInfo() {
		return confRuleInfo;
	}

	public void setConfRuleInfo(ConfRuleInfo confRuleInfo) {
		this.confRuleInfo = confRuleInfo;
	}

    
}
