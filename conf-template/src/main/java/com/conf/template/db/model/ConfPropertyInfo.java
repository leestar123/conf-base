package com.conf.template.db.model;

/**
 * 属性信息
 * 
 * @author li_mingxing
 *
 */
public class ConfPropertyInfo {

	/**
	 * 唯一ID
	 */
	private String uid;
	
	/**
	 * 字段英文名
	 */
	private String name;
	
	/**
	 * 字段中文名称
	 */
	private String nickName;
	
	/**
	 * 属性类型 文本类型、数值类型、日期类型、枚举、浮动
	 */
	private String type;
	
	/**
	 * 属性默认值
	 */
	private String defaultValue;
	
	/**
	 * 匹配正则
	 */
	private String regular;
	
	/**
	 * 数据字典
	 */
	private String dicit;
}
