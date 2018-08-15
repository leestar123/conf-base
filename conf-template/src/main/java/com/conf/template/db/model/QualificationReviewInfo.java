package com.conf.template.db.model;

import java.math.BigDecimal;
import java.util.Date;

public class QualificationReviewInfo {
    private Integer qualificationReviewId;

    private String tranSeqNo;

    private String processId;

    private String custNo;

    private String custName;

    private String custType;

    private String cardNo;

    private String cardType;

    private String telephoneNo;

    private String isCreditAuthorized;

    private String stageType;

    private String creditProduct;

    private BigDecimal creditQuota;

    private String creditTerm;

    private BigDecimal guaranteeQuota;

    private String guaranteeType;

    private String sourceSystem;

    private String tellerNo;

    private String tellerOrg;

    private String qualificationReviewResult;

    private String qualificationReviewFailCode;

    private String qualificationReviewReason;

    private String creidtValue;

    private String creidtLevel;

    private String passFlag;

    private String passRate;

    private String overPercent;

    private String adviceQuota;

    private String sysRate;

    private String loanQuota;

    private String loanRate;

    private String lossLevel;

    private String investType;

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

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType == null ? null : custType.trim();
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

    public String getAdviceQuota() {
        return adviceQuota;
    }

    public void setAdviceQuota(String adviceQuota) {
        this.adviceQuota = adviceQuota == null ? null : adviceQuota.trim();
    }

    public String getSysRate() {
        return sysRate;
    }

    public void setSysRate(String sysRate) {
        this.sysRate = sysRate == null ? null : sysRate.trim();
    }

    public String getLoanQuota() {
        return loanQuota;
    }

    public void setLoanQuota(String loanQuota) {
        this.loanQuota = loanQuota == null ? null : loanQuota.trim();
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate == null ? null : loanRate.trim();
    }

    public String getLossLevel() {
        return lossLevel;
    }

    public void setLossLevel(String lossLevel) {
        this.lossLevel = lossLevel == null ? null : lossLevel.trim();
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType == null ? null : investType.trim();
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType == null ? null : reportType.trim();
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
}