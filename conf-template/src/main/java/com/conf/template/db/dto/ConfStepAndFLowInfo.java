package com.conf.template.db.dto;

import java.util.List;

import com.conf.template.db.model.ConfFlowInfo;

public class ConfStepAndFLowInfo
{
    private Integer stepId;
    
    private String stepName;
    
    List<ConfFlowInfo> flowList;

    public Integer getStepId()
    {
        return stepId;
    }

    public void setStepId(Integer stepId)
    {
        this.stepId = stepId;
    }

    public String getStepName()
    {
        return stepName;
    }

    public void setStepName(String stepName)
    {
        this.stepName = stepName;
    }

    public List<ConfFlowInfo> getFlowList()
    {
        return flowList;
    }

    public void setFlowList(List<ConfFlowInfo> flowList)
    {
        this.flowList = flowList;
    }
}
