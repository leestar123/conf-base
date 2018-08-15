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
	
	/**
	 * 关系人推荐
	 * @author li_mingxing
	 *
	 */
	public class RelatProd {
		
		private String relatCustNo;
		
		private String relatCustName;
		
		private String relatProdName;

		public String getRelatCustNo() {
			return relatCustNo;
		}

		public void setRelatCustNo(String relatCustNo) {
			this.relatCustNo = relatCustNo;
		}

		public String getRelatCustName() {
			return relatCustName;
		}

		public void setRelatCustName(String relatCustName) {
			this.relatCustName = relatCustName;
		}

		public String getRelatProdName() {
			return relatProdName;
		}

		public void setRelatProdName(String relatProdName) {
			this.relatProdName = relatProdName;
		}
	}
	
	/**
	 * 担保人列表
	 * 
	 * @author li_mingxing
	 *
	 */
	public class GuarProd {
		
		private String guarCustNo;
		
		private String guarCustName;
		
		private String guarProdName;

		public String getGuarCustNo() {
			return guarCustNo;
		}

		public void setGuarCustNo(String guarCustNo) {
			this.guarCustNo = guarCustNo;
		}

		public String getGuarCustName() {
			return guarCustName;
		}

		public void setGuarCustName(String guarCustName) {
			this.guarCustName = guarCustName;
		}

		public String getGuarProdName() {
			return guarProdName;
		}

		public void setGuarProdName(String guarProdName) {
			this.guarProdName = guarProdName;
		}
	}
}
