package com.conf.template.db.dto;

import java.util.Date;

import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfStepInfo;

public class ConfProductAndStepAndFLow
{
	private Integer productId;

    private String productName;
    
    private String teller;

    private String org;

    private Date createTime;
    
    ConfStepInfo confStepInfo;
    
    ConfFlowInfo confFlowInfo;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public ConfStepInfo getConfStepInfo() {
		return confStepInfo;
	}

	public void setConfStepInfo(ConfStepInfo confStepInfo) {
		this.confStepInfo = confStepInfo;
	}

	public ConfFlowInfo getConfFlowInfo() {
		return confFlowInfo;
	}

	public void setConfFlowInfo(ConfFlowInfo confFlowInfo) {
		this.confFlowInfo = confFlowInfo;
	}
    
    
}
