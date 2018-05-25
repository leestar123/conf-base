package com.conf.template.db.model;

/**
 * 节点基础信息
 * 
 * @author li_mingxing
 *
 */
public class ConfNodeInfo {

	/**
	 *  节点编号
	 */
	private String nodeId;
	
	/**
	 *  节点名称
	 */
	private String nodeName;
	
	/**
	 *  节点类型 规则节点、属性节点、配置节点
	 */
	private String nodeType;
	
	/**
	 *  继承节点 继承该节点所有的属性
	 */
	private String extendNode;
}
