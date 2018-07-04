package com.conf.template.db.model;

import java.util.List;

public class ConfNodeInfoAndProduct {
	private Integer productId;
	
	private String productName;
	
	List<ConfNodeInfo> nodeList;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<ConfNodeInfo> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<ConfNodeInfo> nodeList) {
		this.nodeList = nodeList;
	}

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }
}
