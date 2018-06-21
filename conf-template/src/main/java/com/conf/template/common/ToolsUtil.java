package com.conf.template.common;

import java.text.NumberFormat;

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
}
