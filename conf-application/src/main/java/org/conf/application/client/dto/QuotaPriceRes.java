package org.conf.application.client.dto;

/**
 * 定额定价响应对象
 * 
 * @author li_mingxing
 *
 */
public class QuotaPriceRes {

	//建议额度
	private String SYS_ADVICE;
	
	//建议利率
	private String SYS_RATE;

	public String getSYS_ADVICE() {
		return SYS_ADVICE;
	}

	public void setSYS_ADVICE(String sYS_ADVICE) {
		SYS_ADVICE = sYS_ADVICE;
	}

	public String getSYS_RATE() {
		return SYS_RATE;
	}

	public void setSYS_RATE(String sYS_RATE) {
		SYS_RATE = sYS_RATE;
	}
}
