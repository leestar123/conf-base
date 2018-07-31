package com.conf.common.process;

import java.util.Map;

/**
 * 服务调用前后处理
 * 
 * @author li_mingxing
 *
 */
public interface InvokerAopProcess {
	
	/**
	 * 服务调用前处理
	 * 
	 * @param data	上送报文
	 */
    public void beforeProcess(Map<String, ? extends Object> data);
    
    /**
     * 服务调用结束后处理
     * 
     * @param data Urule返回参数
     */
    public void afterPorcess(Map<String, ? extends Object> data);
}
