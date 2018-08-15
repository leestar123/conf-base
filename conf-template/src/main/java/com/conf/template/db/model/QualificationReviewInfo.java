package com.conf.template.db.model;

import java.math.BigDecimal;
import java.util.Date;

public class QualificationReviewInfo {
	
	//自增主键
    private Integer qualificationReviewId;

    //业务流水
    private String tranSeqNo;

    //流程编号
    private String processId;

    //客户号
    private String custNo;

    //客户名称
    private String custName;

    //客户类型 01-借款人  02-共同借款人 03-担保人
    private String custType;

    //证件号
    private String cardNo;
    
    //证件类型
    private String cardType;

    //手机号
    private String telephoneNo;

    //是否征信授权
    private String isCreditAuthorized;

    //阶段类型
    private String stageType;

    //授信产品
    private String creditProduct;

    //授信额度
    private BigDecimal creditQuota;

    //授信期限
    private String creditTerm;

    //担保额度
    private BigDecimal guaranteeQuota;

    //担保方式
    private String guaranteeType;

    //来源系统
    private String sourceSystem;

    //经办人
    private String tellerNo;

    //经办机构
    private String tellerOrg;

    //资质审查结果
    private String qualificationReviewResult;

    //资质审查失败节点
    private String qualificationReviewFailCode;

    //资质审查失败原因
    private String qualificationReviewReason;

    //信用评分
    private String creidtValue;

    //信用评级
    private String creidtLevel;

    //评分评级通过标识
    private String passFlag;

    //建议通过率
    private String passRate;

    //可能逾期率
    private String overPercent;
    
    //建议额度
    private String sysAdvice;
    
    //建议利率
    private String sysRate;
    
    //贷款额度
    private String loanAdvice;
    
    //贷款利率
    private String loanRate;
    
    //流失等级
    private String lossLevel;
    
    //调查方式
    private String investType;
    
    //报表编制
    private String reportType;

    private Date lastUpdatedStamp;

    private Date lastUpdatedTxStamp;

    private Date createdStamp;

    private Date createdTxStamp;

    public Integer getQualificationReviewId() {
        return qualificationReviewId;
    }

    public void setQualificationReviewId(Integer qualificationReviewId) {
        this.qualificationReviewId = qualificationReviewId;
    }

    public String getTranSeqNo() {
        return tranSeqNo;
    }

    public void setTranSeqNo(String tranSeqNo) {
        this.tranSeqNo = tranSeqNo == null ? null : tranSeqNo.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo == null ? null : custNo.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo == null ? null : telephoneNo.trim();
    }

    public String getIsCreditAuthorized() {
        return isCreditAuthorized;
    }

    public void setIsCreditAuthorized(String isCreditAuthorized) {
        this.isCreditAuthorized = isCreditAuthorized == null ? null : isCreditAuthorized.trim();
    }

    public String getStageType() {
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType == null ? null : stageType.trim();
    }

    public String getCreditProduct() {
        return creditProduct;
    }

    public void setCreditProduct(String creditProduct) {
        this.creditProduct = creditProduct == null ? null : creditProduct.trim();
    }

    public BigDecimal getCreditQuota() {
        return creditQuota;
    }

    public void setCreditQuota(BigDecimal creditQuota) {
        this.creditQuota = creditQuota;
    }

    public String getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(String creditTerm) {
        this.creditTerm = creditTerm == null ? null : creditTerm.trim();
    }

    public BigDecimal getGuaranteeQuota() {
        return guaranteeQuota;
    }

    public void setGuaranteeQuota(BigDecimal guaranteeQuota) {
        this.guaranteeQuota = guaranteeQuota;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType == null ? null : guaranteeType.trim();
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem == null ? null : sourceSystem.trim();
    }

    public String getTellerNo() {
        return tellerNo;
    }

    public void setTellerNo(String tellerNo) {
        this.tellerNo = tellerNo == null ? null : tellerNo.trim();
    }

    public String getTellerOrg() {
        return tellerOrg;
    }

    public void setTellerOrg(String tellerOrg) {
        this.tellerOrg = tellerOrg == null ? null : tellerOrg.trim();
    }

    public String getQualificationReviewResult() {
        return qualificationReviewResult;
    }

    public void setQualificationReviewResult(String qualificationReviewResult) {
        this.qualificationReviewResult = qualificationReviewResult == null ? null : qualificationReviewResult.trim();
    }

    public String getQualificationReviewFailCode() {
        return qualificationReviewFailCode;
    }

    public void setQualificationReviewFailCode(String qualificationReviewFailCode) {
        this.qualificationReviewFailCode = qualificationReviewFailCode == null ? null : qualificationReviewFailCode.trim();
    }

    public String getQualificationReviewReason() {
        return qualificationReviewReason;
    }

    public void setQualificationReviewReason(String qualificationReviewReason) {
        this.qualificationReviewReason = qualificationReviewReason == null ? null : qualificationReviewReason.trim();
    }

    public String getCreidtValue() {
        return creidtValue;
    }

    public void setCreidtValue(String creidtValue) {
        this.creidtValue = creidtValue == null ? null : creidtValue.trim();
    }

    public String getCreidtLevel() {
        return creidtLevel;
    }

    public void setCreidtLevel(String creidtLevel) {
        this.creidtLevel = creidtLevel == null ? null : creidtLevel.trim();
    }

    public String getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(String passFlag) {
        this.passFlag = passFlag == null ? null : passFlag.trim();
    }

    public String getPassRate() {
        return passRate;
    }

    public void setPassRate(String passRate) {
        this.passRate = passRate == null ? null : passRate.trim();
    }

    public String getOverPercent() {
        return overPercent;
    }

    public void setOverPercent(String overPercent) {
        this.overPercent = overPercent == null ? null : overPercent.trim();
    }

    public Date getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(Date lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public Date getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(Date lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public Date getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Date createdStamp) {
        this.createdStamp = createdStamp;
    }

    public Date getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(Date createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getSysAdvice() {
		return sysAdvice;
	}

	public void setSysAdvice(String sysAdvice) {
		this.sysAdvice = sysAdvice;
	}

	public String getSysRate() {
		return sysRate;
	}

	public void setSysRate(String sysRate) {
		this.sysRate = sysRate;
	}

	public String getLoanAdvice() {
		return loanAdvice;
	}

	public void setLoanAdvice(String loanAdvice) {
		this.loanAdvice = loanAdvice;
	}

	public String getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}

	public String getLossLevel() {
		return lossLevel;
	}

	public void setLossLevel(String lossLevel) {
		this.lossLevel = lossLevel;
	}

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
}