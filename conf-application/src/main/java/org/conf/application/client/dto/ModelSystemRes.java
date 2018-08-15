package org.conf.application.client.dto;

import java.util.List;

import org.conf.application.client.dto.LossWarningRes.Strategy;

/**
 * 模型系统返回对象
 * 
 * @author li_mingxing
 *
 */
public class ModelSystemRes {

	//报告编号
	private String reportId;
	
	private List<Strategy> strategyList;
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public List<Strategy> getStrategyList() {
		return strategyList;
	}

	public void setStrategyList(List<Strategy> strategyList) {
		this.strategyList = strategyList;
	}
}
