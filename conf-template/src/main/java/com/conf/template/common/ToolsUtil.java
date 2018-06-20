package com.conf.template.common;

public class ToolsUtil {

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
}
