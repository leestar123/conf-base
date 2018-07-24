package com.conf.common;

import java.text.MessageFormat;

public enum ErrorCode {

	code_9999("9999", "系统执行异常"),
	code_0000("0000","excute success") ,
	code_0001("0001", "缺少必输字段{0}"),
	code_0002("0002", "数据库操作异常"),
	code_0003("0003", "组件{0}未绑定任何规则，请先绑定规则！"),
	code_0004("0004", "规则{0}执行失败！"),
	code_0005("0005", "文件{0}已存在！"),
	code_0006("0006", "组件{0}已存在！"),
    code_0007("0007", "阶段编号{0}不存在！"),
    code_0008("0008", "节点编号{0}不存在！"),
    code_0009("0009", "流程编号{0}不存在！"),
    code_0010("0010", "阶段{0}未绑定任何节点！"),
    code_0011("0011", "流程{0}绑定关系不存在！");
	
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
	    String temp = "";
		if (params.length > 0) {
		    temp = MessageFormat.format(msg, params);  
		}
		return temp;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
