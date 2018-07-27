package com.conf.common.process;

import java.util.Map;

/**
 * HTTP协议请求前后处理
 * 
 * @author li_mingxing
 *
 */
public interface HttpAopProcess
{
    public void beforeProcess(Map<String, ? extends Object> data);
    
    public void afterPorcess(Map<String, ? extends Object> data);
}
