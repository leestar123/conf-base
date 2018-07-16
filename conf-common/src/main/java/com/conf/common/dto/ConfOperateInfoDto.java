package com.conf.common.dto;

import java.util.Date;
import java.util.List;

public class ConfOperateInfoDto {
    private Integer id;

    private String serialNo;
    
    private Integer operateType;

    private Integer operateModule;

    private List<ModuleInfo> module;

    private String request;

    private Integer success;

    private String teller;

    private String org;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Integer getOperateModule() {
        return operateModule;
    }

    public void setOperateModule(Integer operateModule) {
        this.operateModule = operateModule;
    }

    public List<ModuleInfo> getModule()
    {
        return module;
    }

    public void setModule(List<ModuleInfo> module)
    {
        this.module = module;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request == null ? null : request.trim();
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getTeller() {
        return teller;
    }

    public void setTeller(String teller) {
        this.teller = teller == null ? null : teller.trim();
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org == null ? null : org.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSerialNo()
    {
        return serialNo;
    }

    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }
}