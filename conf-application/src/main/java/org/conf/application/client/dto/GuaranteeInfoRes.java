package org.conf.application.client.dto;

/**
 * 查询担保人
 * 
 * @author li_mingxing
 *
 */
public class GuaranteeInfoRes {
	
	//担保人客户号
	private String guaranteeCustNo;
	
	//担保人名称
	private String guaranteeCustName;

	public String getGuaranteeCustNo() {
		return guaranteeCustNo;
	}

	public void setGuaranteeCustNo(String guaranteeCustNo) {
		this.guaranteeCustNo = guaranteeCustNo;
	}

	public String getGuaranteeCustName() {
		return guaranteeCustName;
	}

	public void setGuaranteeCustName(String guaranteeCustName) {
		this.guaranteeCustName = guaranteeCustName;
	}
}
