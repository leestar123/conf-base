package com.conf.template.common;

import java.text.NumberFormat;
import java.util.Map;
import java.util.Properties;

public class ToolsUtil {

	private static SequenceGen gen = new SequenceGen(2L);
	
	static ThreadLocal<Map<String,Object>> threadLocal =new ThreadLocal<Map<String,Object>>();  
	
	public static int sequence =0;
	/**
	 * Object对象转换为String
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2Str(Object obj) {
		if (obj == null)
			return null;
		return String.valueOf(obj);
	}
	
	public static synchronized int autoNumber()
	{
		NumberFormat format = NumberFormat.getInstance();
        format.setMinimumIntegerDigits(12);
        String result =  format.format(sequence).replace(",", "");
        sequence = Integer.valueOf(result);
		return sequence;
	}
	
	/**
	 * Object对象转换为Integer
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static Integer obj2Int(Object obj, Integer defaultValue) {
		if (obj == null)
			return defaultValue;
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 生成唯一全局流水号
	 * 
	 * @return
	 */
	public static Long nextSeq() {
		return gen.nextId();
	}
	
	public static String getSystemInfo()
	{
		Properties props=System.getProperties(); 
		String osName = props.getProperty("os.name");
		return osName;
	}
	
	/**
	 * 为每个线程存储上下文作为共享变量
	 * @param map
	 * @return
	 */
	public static ThreadLocal<Map<String,Object>> threadLocalSet(Map<String,Object> map)
	{
		threadLocal.set(map);
		return threadLocal;
	}
	
	/**
	 * 获取每个线程存储上下文
	 * @return
	 */
	public static Map<String,Object> threadLocalGet()
	{
		Map<String,Object> maps = threadLocal.get();
		return maps;
	}
}
