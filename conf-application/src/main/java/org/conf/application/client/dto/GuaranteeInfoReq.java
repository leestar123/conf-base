package org.conf.application.client.dto;

/**
 * 查询担保人、共同借款人请求
 * 
 * @author li_mingxing
 *
 */
public class GuaranteeInfoReq {

	//授信申请编号
	private String applyNo;
	
	//查询类型
	private String questionType;

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

}
