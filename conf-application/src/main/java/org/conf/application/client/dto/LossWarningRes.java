package org.conf.application.client.dto;

import java.util.List;

/**
 * 流失预警返回对象
 * 
 * @author li_mingxing
 *
 */
public class LossWarningRes {

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
