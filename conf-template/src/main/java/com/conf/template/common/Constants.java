package com.conf.template.common;

public class Constants {
	
	public static String SCAN_PACKAGE_NAME = "com.conf.template.rule";
	
	public final static String SYSTEM_ERROR_CODE = "9999";
	
	public final static long SCANNING_TIME_INTERVAL = 20L;
	
	/**
	 * 状态生效状态 0- 生效 1-失效
	 */
	public final static String EFFECT_STATUS_VALID = "0";
	
	/**
	 * 状态生效状态 0- 生效 1-失效
	 */
	public final static String EFFECT_STATUS_INVALID = "1";
	
	/**
	 * 最大序列号
	 */
	public final static Integer MAX_SEQUENCE = 9999;
	
	/**
	 * RULE基础地址
	 */
    public final static String RULE_URL_BASE = "http://" + ToolsUtil.localIP() + ":8081/urule/";
    
    /**
     * 规则类型  0-动作
     */
    public final static String RULE_TYPE_ACTION = "0";
}
