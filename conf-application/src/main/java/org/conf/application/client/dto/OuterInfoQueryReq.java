package org.conf.application.client.dto;

/**
 * 外部数据查询请求
 * 
 * @author li_mingxing
 *
 */
public class OuterInfoQueryReq {

	private String clientNo;
	
	private String globalType;
	
	private String globalId;
	
	private String url;
	
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getGlobalType() {
		return globalType;
	}
	public void setGlobalType(String globalType) {
		this.globalType = globalType;
	}
	public String getGlobalId() {
		return globalId;
	}
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
