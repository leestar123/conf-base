package com.conf.common.dto;

public class ModuleInfo
{
    private Integer moduleId;

    private String moduleName;
    
    private Integer operateType;

    private Integer operateModule;
    
    public Integer getModuleId()
    {
        return moduleId;
    }

    public void setModuleId(Integer moduleId)
    {
        this.moduleId = moduleId;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
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
    
    
}
