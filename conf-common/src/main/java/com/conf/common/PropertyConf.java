package com.conf.common;

/**
 * 配置类
 * 
 * @author  sunliang
 * @version  [版本号, 2018年7月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PropertyConf
{
    private String scanPackageName;

    public String getScanPackageName()
    {
        return scanPackageName == null ? Constants.SCAN_PACKAGE_NAME : scanPackageName;
    }

    public void setScanPackageName(String scanPackageName)
    {
        this.scanPackageName = scanPackageName;
    }

}
