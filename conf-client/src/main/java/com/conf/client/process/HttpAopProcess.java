package com.conf.client.process;

import java.util.Map;

public interface HttpAopProcess
{
    public void beforeProcess(Map<String, ? extends Object> data);
    
    public void afterPorcess(Map<String, ? extends Object> data);
}
