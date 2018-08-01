package com.conf.common;

import java.util.Map;

import com.bstek.urule.runtime.rete.Context;
import com.conf.common.dto.ConfOperateInfoDto;

/**
 * 系统上下文
 * 
 * @author li_mingxing
 *
 */
public class ConfContext {
	
	static ThreadLocal<Map<String,? extends Object>> threadLocal =new ThreadLocal<Map<String, ? extends Object>>();  
	
	static ThreadLocal<ConfOperateInfoDto> operateLocal =new ThreadLocal<>(); 
	
	static ThreadLocal<String> invokerLocal =new ThreadLocal<>(); 
	
	static Context context;
	
	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		ConfContext.context = context;
	}

	/**
	 * 为每个线程存储上下文作为共享变量
	 * @param map
	 * @return
	 */
	public static ThreadLocal<Map<String, ? extends Object>> threadLocalSet(Map<String, ? extends Object> map)
	{
		threadLocal.set(map);
		return threadLocal;
	}
	
	/**
	 * 获取每个线程存储上下文
	 * @return
	 */
	public static Map<String, ? extends Object> threadLocalGet()
	{
		Map<String, ? extends Object> maps = threadLocal.get();
		return maps;
	}
	
	/**
	 * 
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ConfOperateInfoDto operateLocalGet()
    {
        if (operateLocal.get() == null) 
        {
            operateLocal.set(new ConfOperateInfoDto());
        } 
        return operateLocal.get();
    }

	/**
	 * 
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @param operateLocal
	 * @see [类、类#方法、类#成员]
	 */
    public static void operateLocalSet(ConfOperateInfoDto value)
    {
        operateLocal.set(value);
    }
    
    /**
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String invokerLocalGet()
    {
        return invokerLocal.get();
    }

    /**
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * @param operateLocal
     * @see [类、类#方法、类#成员]
     */
    public static void invokerLocalSet(String value)
    {
        invokerLocal.set(value);
    }
    
}
