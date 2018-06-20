package com.conf.template.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.template.common.ErrorCode;
import com.conf.template.common.ErrorUtil;
import com.conf.template.db.mapper.ConfNodeInfoMapper;
import com.conf.template.db.model.ConfNodeInfo;

@Service
public class NodeService {
	@Autowired
	ConfNodeInfoMapper confNodeInfoMapper ;
	public Map<String, ? extends Object> createNode(Map<String, ? extends Object> data) {
		//参数拼装
		ConfNodeInfo confNodeInfo= new ConfNodeInfo();
		confNodeInfo.setNodeName((String)data.get("nodeName"));
		confNodeInfo.setNodeType((String)data.get("nodeType"));
		confNodeInfo.setOrg((String)data.get("org"));
		confNodeInfo.setRemark((String)data.get("remark "));
		confNodeInfo.setTeller((String)data.get("teller"));
		confNodeInfo.setVersion("");
		//nodeid生成
		SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");//设置日期格式
		int nodeId = Integer.valueOf(df.format(new Date()));// new Date()为获取当前系统时间，也可使用当前时间戳
		confNodeInfo.setNodeId(nodeId);
		int result = confNodeInfoMapper.insertSelective(confNodeInfo);
		if(result !=1)
		{
			return ErrorUtil.errorResp(ErrorCode.code_0002);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("nodeId", nodeId);
		map.put("body", body);
		return ErrorUtil.successResp(map);
	}
	
	public Map<String, ? extends Object> queryNodeList(Map<String, ? extends Object> data) {
		return null;
	}
}
