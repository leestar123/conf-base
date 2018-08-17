package org.conf.application.client.dto;

/**
 * 共同借款人信息
 * 
 * @author li_mingxing
 *
 */
public class CommonBorrowerInfoRes {

	//共同借款人客户号
	private String commonBorrowerCustNo;
	
	//共同借款人客户名称
	private String commonBorrowerCustName;
	
	//共同借款人身份证
	private String commonBorrowerCardNO;

	public String getCommonBorrowerCustNo() {
		return commonBorrowerCustNo;
	}

	public void setCommonBorrowerCustNo(String commonBorrowerCustNo) {
		this.commonBorrowerCustNo = commonBorrowerCustNo;
	}

	public String getCommonBorrowerCustName() {
		return commonBorrowerCustName;
	}

	public void setCommonBorrowerCustName(String commonBorrowerCustName) {
		this.commonBorrowerCustName = commonBorrowerCustName;
	}

	public String getCommonBorrowerCardNO() {
		return commonBorrowerCardNO;
	}

	public void setCommonBorrowerCardNO(String commonBorrowerCardNO) {
		this.commonBorrowerCardNO = commonBorrowerCardNO;
	}
}
