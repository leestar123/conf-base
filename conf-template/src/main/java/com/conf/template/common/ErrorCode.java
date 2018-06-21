package com.conf.template.common;

import java.text.MessageFormat;

public enum ErrorCode {

	code_9999("9999", "系统执行异常"),
	code_0000("0000","excute success") ,
	code_0001("0001", "缺少必输字段{0}"),
	code_0002("0002", "数据库操作异常");
	private	String code;
    private String msg;  
    // 构造方法  
    private ErrorCode(String code, String msg) {  
    	this.code = code;
    	this.msg = msg;
    }
    
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg(Object... params) {
		if (params.length > 0) {
			msg = MessageFormat.format(msg, params);  
		}
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
