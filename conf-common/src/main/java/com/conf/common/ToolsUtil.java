package com.conf.common;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ToolsUtil {

	private static SequenceGen gen = new SequenceGen(2L);
	
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
        try
        {
            return WebToolUtils.getLocalIP();
        }
        catch (Exception e)
        {
            return "127.0.0.1";
        }
    }
    
    /**
     * 设置内存分页数据
     * @param begin
     * @param end
     * @param list
     * @param totleNum 
     * @return 
     */
    public static List<Map<String, Object>> getListPageData(Integer pageNum, Integer pageSize, List<Map<String, Object>> list){
    	List<Map<String, Object>> pageList = null;
    	if (pageNum != null &&  pageSize != null && list != null){
    		Integer total = list.size();
    		if (pageNum * pageSize < total) {
    			pageList = list.subList((pageNum - 1) * pageSize, pageNum * pageSize);
    		} else {
    			pageList = list.subList((pageNum - 1) * pageSize, total);
    		}
    	}
    	return pageList == null ? new ArrayList<>() : pageList;
    }
    
    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 
     * 
     * @param version1
     * @param version2
     * @return
     * @throws Exception
     */
	public static int compareVersion(String version1, String version2) throws Exception {

		if (version1 == null || version2 == null) {
			throw new Exception("compareVersion error:illegal params.");
		}
		String[] versionArray1 = version1.split("\\.");// 注意此处为正则匹配，不能用"."；
		for (int i = 0; i < versionArray1.length; i++) { // 如果位数只有一位则自动补零（防止出现一个是04，一个是5 直接以长度比较）
			if (versionArray1[i].length() == 1) {
				versionArray1[i] = "0" + versionArray1[i];
			}
		}
		String[] versionArray2 = version2.split("\\.");
		for (int i = 0; i < versionArray2.length; i++) {// 如果位数只有一位则自动补零
			if (versionArray2[i].length() == 1) {
				versionArray2[i] = "0" + versionArray2[i];
			}
		}
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);// 取最小长度值
		int diff = 0;
		while (idx < minLength && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0// 先比较长度
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {// 再比较字符
			++idx;
		}
		// 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}
}
