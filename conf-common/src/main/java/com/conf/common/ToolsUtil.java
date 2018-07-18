package com.conf.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Properties;

import com.conf.common.dto.ConfOperateInfoDto;

public class ToolsUtil {

	private static SequenceGen gen = new SequenceGen(2L);
	
	static ThreadLocal<Map<String,? extends Object>> threadLocal =new ThreadLocal<Map<String, ? extends Object>>();  
	
	static ThreadLocal<ConfOperateInfoDto> operateLocal =new ThreadLocal<>(); 
	
	static ThreadLocal<String> invokerLocal =new ThreadLocal<>(); 
	
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
    

    /**
	 * 组装路径
	 * 
	 * @param name
	 * @return
	 */
	public static String combPath(String... name) {
		if (name == null || name.length == 0)
			return "";
		String path = "";
		for (String str : name) {
			path += "/" + str ;
		}
		return path;
	}
	
	/**
	 * 规则类型转换
	 * @param type
	 * @return
	 */
	public static String parse(String type) {
		if (type.equals("rs.xml")) {
			//决策集
			return "1";
		} else if (type.equals("dt.xml")) {
			//决策表
			return "2";
		} else if (type.equals("dtree.xml")) {
			//决策树
			return "3";
		} else if (type.equals("sc")) {
			//评分表
			return "4";
		} else {
			throw new RuntimeException("Unknow type:" + type);
		}
	}
	
	   /**
     * 规则类型转换
     * @param type
     * @return
     */
    public static String unParse(String type) {
        switch(type) {
            case "1"://决策集
                return "rs.xml";
            case "2": //决策表
                return "dt.xml";
            case "3"://决策树
                return "dtree.xml";
            case "4"://评分表
                return "sc";
            default:
                throw new RuntimeException("Unknow type:" + type);
        }
    }
	
	/**
	 * 添加URL
	 * 
	 * @param data
	 * @param url
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public static void addUrl(Map<String, ? extends Object> data, String url) {
        if (ErrorUtil.isSuccess(data)) {
            Map<String, Object> body = (Map<String, Object>)data.get("body");
            body.put("url", url);
        }
	}
	
	/**
	 * 
	 * 获取本机IP地址
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    public static String localIP()
    {
        InetAddress address;
        try
        {
            address = InetAddress.getLocalHost();
            return address.getHostAddress();
        }
        catch (UnknownHostException e)
        {
            return "127.0.0.1";
        }
    }
}
