package org.conf.application.client.dto;

/**
 * 调查报告
 * @author li_mingxing
 *
 */
public class Strategy {
	
	private String investType;
	
	private String reportType;
	
	//流失等级
	private String lossLevel;
	
	//建议利率
	private String sugRate;

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getLossLevel() {
		return lossLevel;
	}

	public void setLossLevel(String lossLevel) {
		this.lossLevel = lossLevel;
	}

	public String getSugRate() {
		return sugRate;
	}

	public void setSugRate(String sugRate) {
		this.sugRate = sugRate;
	}
}
