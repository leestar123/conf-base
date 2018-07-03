package com.conf.client.common;

import java.util.HashMap;
import java.util.Map;

public class ErrorUtil {

	/**
	 * 设置错误返回信息
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static Map<String, ? extends Object> errorResp(String code, String msg) {
		Map<String, Object> data = new HashMap<>();
		data.put("code", code);
		data.put("msg", msg);
		data.put("success", false);
		return data;
	}
	
	/**
     * 判断是否成功
     * 
     * @param data
     * @return
     */
    public static boolean isSuccess(Map<String, ? extends Object> data)
    {
        boolean flag = false;
        Object success = data.get("success");
        if (success != null)
            flag = (boolean)success;
        return flag;
    }
}