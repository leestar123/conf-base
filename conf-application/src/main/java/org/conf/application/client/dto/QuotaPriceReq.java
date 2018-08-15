package org.conf.application.client.dto;

import java.util.List;

/**
 * 定额定价请求对象
 * 
 * @author li_mingxing
 *
 */
public class QuotaPriceReq {

	//流水号
	private String SEQ_NO;
	
	//申请金额
	private String APPLY_AMT;
	
	//担保方式
	private String GUARANTEE_MODE;
	
	//业务类型
	private String BUSS_TYPE;
	
	//行业代码
	private String INDUSTRY_CODE;
	
	//行业名称
	private String INDUSTRY_NAME;
	
	//申请期限
	private String APPLY_TERM;
	
	//申请利率
	private String APPLY_RATE;
	
	//客户号
	private String CLIENT_NO;
	
	//客户名称
	private String CLIENT_NAME;
	
	//担保信息组
	private List<GUA_INFO> GUA_INFO_ARRAY;
	
	public String getSEQ_NO() {
		return SEQ_NO;
	}



	public void setSEQ_NO(String sEQ_NO) {
		SEQ_NO = sEQ_NO;
	}



	public String getAPPLY_AMT() {
		return APPLY_AMT;
	}



	public void setAPPLY_AMT(String aPPLY_AMT) {
		APPLY_AMT = aPPLY_AMT;
	}



	public String getGUARANTEE_MODE() {
		return GUARANTEE_MODE;
	}



	public void setGUARANTEE_MODE(String gUARANTEE_MODE) {
		GUARANTEE_MODE = gUARANTEE_MODE;
	}



	public String getBUSS_TYPE() {
		return BUSS_TYPE;
	}



	public void setBUSS_TYPE(String bUSS_TYPE) {
		BUSS_TYPE = bUSS_TYPE;
	}



	public String getINDUSTRY_CODE() {
		return INDUSTRY_CODE;
	}



	public void setINDUSTRY_CODE(String iNDUSTRY_CODE) {
		INDUSTRY_CODE = iNDUSTRY_CODE;
	}



	public String getINDUSTRY_NAME() {
		return INDUSTRY_NAME;
	}



	public void setINDUSTRY_NAME(String iNDUSTRY_NAME) {
		INDUSTRY_NAME = iNDUSTRY_NAME;
	}



	public String getAPPLY_TERM() {
		return APPLY_TERM;
	}



	public void setAPPLY_TERM(String aPPLY_TERM) {
		APPLY_TERM = aPPLY_TERM;
	}



	public String getAPPLY_RATE() {
		return APPLY_RATE;
	}



	public void setAPPLY_RATE(String aPPLY_RATE) {
		APPLY_RATE = aPPLY_RATE;
	}



	public String getCLIENT_NO() {
		return CLIENT_NO;
	}



	public void setCLIENT_NO(String cLIENT_NO) {
		CLIENT_NO = cLIENT_NO;
	}



	public String getCLIENT_NAME() {
		return CLIENT_NAME;
	}



	public void setCLIENT_NAME(String cLIENT_NAME) {
		CLIENT_NAME = cLIENT_NAME;
	}



	public List<GUA_INFO> getGUA_INFO_ARRAY() {
		return GUA_INFO_ARRAY;
	}



	public void setGUA_INFO_ARRAY(List<GUA_INFO> gUA_INFO_ARRAY) {
		GUA_INFO_ARRAY = gUA_INFO_ARRAY;
	}



	public class GUA_INFO {
		
		//担保人客户号
		private String GUARANTEE_CUST_NO;

		public String getGUARANTEE_CUST_NO() {
			return GUARANTEE_CUST_NO;
		}

		public void setGUARANTEE_CUST_NO(String gUARANTEE_CUST_NO) {
			GUARANTEE_CUST_NO = gUARANTEE_CUST_NO;
		}
	}
}
