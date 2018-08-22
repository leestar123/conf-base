package com.conf.template.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.conf.application.FileProcess;
import org.conf.application.client.InvokerCustomerService;
import org.conf.application.client.dto.CheckQulityResultRes;
import org.conf.application.client.dto.CustomerByTopicIdReq;
import org.conf.application.client.dto.CustomerByTopicIdRes.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.conf.common.Constants;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.template.service.ConfUruleService;

public class DefaultFileProcessImpl extends FileProcess<UserInfo, CheckQulityResultRes>{

	//提交间隔
	private Integer commitInterval;
	
	@Autowired
	ConfUruleService confUruleService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CheckQulityResultRes lineProcess(Integer lineNum, UserInfo t, Map<String, ? extends Object> filekey) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		Map<String, Object> map = new HashMap<>();
		map.put("custType", Constants.CUST_TYPE_LOAN);
		map.put("custNo", t.getCustId());
		map.put("custName", t.getCustName());
		map.put("globalType", t.getIdType());
		map.put("globalId", t.getIdNo());
		map.put("telphoneNo", t.getMobilePhone());
		map.put("acountNo", t.getCardNo());
		list.add(map);
		String json = JSONObject.toJSONString(filekey);
		Map req = JSONObject.parseObject(json, Map.class);
		req.put("userInfoArray", list);
        Map<String, ? extends Object> result = confUruleService.excuteKnowledgeForList(req);
        if (ErrorUtil.isSuccess(result)) {
        	String body = JSONObject.toJSONString(ErrorUtil.getBody(result));
        	return JSONObject.parseObject(body, CheckQulityResultRes.class);
        }
		return null;
	}

	@Override
	public Integer getTotalNum(Map<String, ? extends Object> filekey) {
		String marketingCampaigntId = ToolsUtil.obj2Str(filekey.get("marketingCampaigntId"));
		return InvokerCustomerService.getCustListNum(marketingCampaigntId);
	}

	@Override
	public void combineData(List<String> returnData) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<UserInfo> readData(Integer pageNum, Integer pageSize, Map<String, ? extends Object> filekey) {
		String marketingCampaigntId = ToolsUtil.obj2Str(filekey.get("marketingCampaigntId"));
		CustomerByTopicIdReq req = new CustomerByTopicIdReq();
		req.setMarketingCampaigntId(marketingCampaigntId);
		req.setCountNum(pageSize);
		req.setPageNum(pageNum);
		try {
			return InvokerCustomerService.getCustList(req).getCustomerList();
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public String writeData(List<CheckQulityResultRes> list, Map<String, ? extends Object> filekey) {
		return null;
	}

	@Override
	public Integer getCommitInterval() {
		return commitInterval;
	}

	public void setCommitInterval(Integer commitInterval) {
		this.commitInterval = commitInterval;
	}
}
