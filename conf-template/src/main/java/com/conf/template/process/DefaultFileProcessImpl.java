package com.conf.template.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.conf.application.FileProcess;
import org.conf.application.dto.QualityDataDto;
import org.conf.application.dto.QualityResultDto;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.conf.common.HttpUtil;
import com.conf.template.service.ConfUruleService;

public class DefaultFileProcessImpl extends FileProcess<QualityDataDto, QualityResultDto>{

	//访问IP地址
	private String ip;
	
	//访问端口
	private String port;
	
	//读数据服务
	private String readService;
	
	//写数据服务
	private String writeService;
	
	//提交间隔
	private Integer commitInterval;
	
	@Autowired
	ConfUruleService confUruleService;
	
	@Override
	public QualityResultDto lineProcess(Integer lineNum, QualityDataDto t, String... filekey) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", filekey[1]);
        map.put("businessType", filekey[2]);
        map.put("stepId", filekey[3]);
        map.put("flowId", filekey[4]);
        map.put("teller", filekey[5]);
        map.put("org", filekey[6]);
        Map<String, ? extends Object> result = confUruleService.excuteKnowledge(map);
        //TODO:进行对象QualityResultDto转换
		return null;
	}

	@Override
	public Integer getTotalNum(String... filekey) {
		String url = "http://".concat(ip).concat(":").concat(port).concat(readService);
		JSONObject json = new JSONObject();
		json.put("marketingCampaigntId", filekey[0]);
		json.put("pageNum", 1);
		json.put("countNum", 5);
		try {
			String result = HttpUtil.doPost(url,json.toJSONString());
			JSONObject obj = JSONObject.parseObject(result); 
			return obj.getInteger("totalNum");
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public void combineData(List<String> returnData) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<QualityDataDto> readData(Integer startLine, Integer endLine, String... filekey) {
		String url = "http://".concat(ip).concat(":").concat(port).concat(readService);
		JSONObject json = new JSONObject();
		json.put("marketingCampaigntId", filekey[0]);
		json.put("pageNum", startLine);
		json.put("countNum", endLine);
		try {
			String result = HttpUtil.doPost(url,json.toJSONString());
			JSONObject obj = JSONObject.parseObject(result); 
			String customer = obj.getString("customerList");
			return JSONObject.parseArray(customer, QualityDataDto.class);
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public String writeData(List<QualityResultDto> list, String... filekey) {
		String url = "http://".concat(ip).concat(":").concat(port).concat(writeService);
		JSONObject json = new JSONObject();
		json.put("marketingCampaigntId", filekey[0]);
		json.put("documentDataList", list);
		try {
			HttpUtil.doPost(url,json.toJSONString());
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public Integer getCommitInterval() {
		return commitInterval;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setReadService(String readService) {
		this.readService = readService;
	}

	public void setWriteService(String writeService) {
		this.writeService = writeService;
	}

	public void setCommitInterval(Integer commitInterval) {
		this.commitInterval = commitInterval;
	}
}
