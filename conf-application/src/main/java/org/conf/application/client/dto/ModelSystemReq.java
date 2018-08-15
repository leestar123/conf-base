package org.conf.application.client.dto;

/**
 * 模型系统请求对象
 * 
 * @author li_mingxing
 *
 */
public class ModelSystemReq {

	//客户号
	private String custNo;
	
	//报告编号
	private String reportId;

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
}
