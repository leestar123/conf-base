package com.conf.common.process;

import java.util.Map;

/**
 * 服务调用前后处理
 * 
 * @author li_mingxing
 *
 */
public interface InvokerAopProcess {
	
    public void beforeProcess(Map<String, ? extends Object> data);
    
    public void afterPorcess(Map<String, ? extends Object> data);
}
