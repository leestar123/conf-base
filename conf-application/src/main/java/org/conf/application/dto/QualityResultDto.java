package org.conf.application.dto;

import java.math.BigDecimal;
import java.util.List;

public class QualityResultDto {

	private String dealStatus;
	
	private List<businessData> updateList;
	
	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public List<businessData> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<businessData> updateList) {
		this.updateList = updateList;
	}

	class businessData {
		
		private String productId;
		
		private String productName;
		
		private float suggestInterestRate;
		
		private BigDecimal suggestAmount;
		
		private BigDecimal preCreditAmount;
		
		private float suggestPrd;

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public float getSuggestInterestRate() {
			return suggestInterestRate;
		}

		public void setSuggestInterestRate(float suggestInterestRate) {
			this.suggestInterestRate = suggestInterestRate;
		}

		public BigDecimal getSuggestAmount() {
			return suggestAmount;
		}

		public void setSuggestAmount(BigDecimal suggestAmount) {
			this.suggestAmount = suggestAmount;
		}

		public BigDecimal getPreCreditAmount() {
			return preCreditAmount;
		}

		public void setPreCreditAmount(BigDecimal preCreditAmount) {
			this.preCreditAmount = preCreditAmount;
		}

		public float getSuggestPrd() {
			return suggestPrd;
		}

		public void setSuggestPrd(float suggestPrd) {
			this.suggestPrd = suggestPrd;
		}
	}
}
