package com.conf.template.scan;

/**
 * 组件扫描
 * 
 * @author Administrator
 *
 */
public interface ScanMgr {

    /**
     * 首次载入扫描
     * @throws Exception
     */
    void firstScan() throws Exception;

    /**
     * 重新载入扫描
     *
     * @throws Exception
     */
    void reloadableScan() throws Exception;
}
