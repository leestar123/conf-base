package org.conf.application.client.dto;

import java.util.List;

/**
 * 批量名单响应
 * 
 * @author li_mingxing
 *
 */
public class CustomerByTopicIdRes {

	private Integer totalNum;
	
	private List<UserInfo> customerList;
	
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public List<UserInfo> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<UserInfo> customerList) {
		this.customerList = customerList;
	}

	public class UserInfo {
		
		private String custId;
		
		private String custName;
		
		private String idType;
		
		private String idNo;
		
		private String mobilePhone;
		
		private String cardNo;

		public String getCustId() {
			return custId;
		}

		public void setCustId(String custId) {
			this.custId = custId;
		}

		public String getCustName() {
			return custName;
		}

		public void setCustName(String custName) {
			this.custName = custName;
		}

		public String getIdType() {
			return idType;
		}

		public void setIdType(String idType) {
			this.idType = idType;
		}

		public String getIdNo() {
			return idNo;
		}

		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}

		public String getMobilePhone() {
			return mobilePhone;
		}

		public void setMobilePhone(String mobilePhone) {
			this.mobilePhone = mobilePhone;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
	}
}
