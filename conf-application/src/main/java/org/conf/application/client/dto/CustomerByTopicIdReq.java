package org.conf.application.client.dto;

/**
 * 批量获取客户名单
 * 
 * @author li_mingxing
 *
 */
public class CustomerByTopicIdReq {

	private String marketingCampaigntId;
	
	private Integer pageNum;
	
	private Integer countNum;

	public String getMarketingCampaigntId() {
		return marketingCampaigntId;
	}

	public void setMarketingCampaigntId(String marketingCampaigntId) {
		this.marketingCampaigntId = marketingCampaigntId;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}
}
