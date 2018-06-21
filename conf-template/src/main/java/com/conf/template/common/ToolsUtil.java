package com.conf.template.common;

import java.text.NumberFormat;

public class ToolsUtil {

	public final static int sequence =0;
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
		return Integer.valueOf(result);
	}
}
