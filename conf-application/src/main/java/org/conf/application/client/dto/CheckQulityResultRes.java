package org.conf.application.client.dto;

import java.util.List;

public class CheckQulityResultRes {

	private Long dateTime;
	
	private String result;
	
	private List<QulityInfo> evalArray;
	
	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<QulityInfo> getEvalArray() {
		return evalArray;
	}

	public void setEvalArray(List<QulityInfo> evalArray) {
		this.evalArray = evalArray;
	}

	public class QulityInfo {
		
		private String custType ;//| 客户类型 |string | 6  |  true | |
		
		private String custNo ;//| 客户号 |string | 20  |  true | |
		
		private String custName ;//| 客户名称 |List | 150  |  true | |
		
		private String globalType ;//| 证件类型 |List | 5  |  true | |
		
		private String telphoneNo ;//| 手机号 |string | 10  |  true | |
		
		private String acountNo ;//| 银行卡号 |string | 32 |  false | |
		
		private String globalId ;//| 证件号码 |string | 32 |  true | |
		
		private String qualificationReviewResult ;//| 资质审查结果 |string | 2 |  true | |
		
		private String qualificationReviewFailCode ;//| 资质审查节点 |string | 32 |  true | |
		
		private String qualificationReviewReason ;//| 资质审查失败原因 |string | 32 |  true | |
		
		private String creidtValue ;//| 信用评分 |string | 10 |  true | |
		
		private String creidtLevel ;//| 信用评级 |string | 10 |  true | |
		
		private String overPercent ;//| 可能逾期 |string | 10 |  true | |
		
		private String passRate ;//| 建议通过率 |string | 10 |  true | ||
		
		public String getCustType() {
			return custType;
		}
		public void setCustType(String custType) {
			this.custType = custType;
		}
		public String getCustNo() {
			return custNo;
		}
		public void setCustNo(String custNo) {
			this.custNo = custNo;
		}
		public String getCustName() {
			return custName;
		}
		public void setCustName(String custName) {
			this.custName = custName;
		}
		public String getGlobalType() {
			return globalType;
		}
		public void setGlobalType(String globalType) {
			this.globalType = globalType;
		}
		public String getTelphoneNo() {
			return telphoneNo;
		}
		public void setTelphoneNo(String telphoneNo) {
			this.telphoneNo = telphoneNo;
		}
		public String getAcountNo() {
			return acountNo;
		}
		public void setAcountNo(String acountNo) {
			this.acountNo = acountNo;
		}
		public String getGlobalId() {
			return globalId;
		}
		public void setGlobalId(String globalId) {
			this.globalId = globalId;
		}
		public String getQualificationReviewResult() {
			return qualificationReviewResult;
		}
		public void setQualificationReviewResult(String qualificationReviewResult) {
			this.qualificationReviewResult = qualificationReviewResult;
		}
		public String getQualificationReviewFailCode() {
			return qualificationReviewFailCode;
		}
		public void setQualificationReviewFailCode(String qualificationReviewFailCode) {
			this.qualificationReviewFailCode = qualificationReviewFailCode;
		}
		public String getQualificationReviewReason() {
			return qualificationReviewReason;
		}
		public void setQualificationReviewReason(String qualificationReviewReason) {
			this.qualificationReviewReason = qualificationReviewReason;
		}
		public String getCreidtValue() {
			return creidtValue;
		}
		public void setCreidtValue(String creidtValue) {
			this.creidtValue = creidtValue;
		}
		public String getCreidtLevel() {
			return creidtLevel;
		}
		public void setCreidtLevel(String creidtLevel) {
			this.creidtLevel = creidtLevel;
		}
		public String getOverPercent() {
			return overPercent;
		}
		public void setOverPercent(String overPercent) {
			this.overPercent = overPercent;
		}
		public String getPassRate() {
			return passRate;
		}
		public void setPassRate(String passRate) {
			this.passRate = passRate;
		}
	}
}
