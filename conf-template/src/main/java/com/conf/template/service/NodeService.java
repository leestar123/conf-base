package com.conf.template.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.template.common.ErrorCode;
import com.conf.template.common.ErrorUtil;
import com.conf.template.common.ToolsUtil;
import com.conf.template.db.mapper.ConfNodeInfoMapper;
import com.conf.template.db.mapper.ConfNodeTemplateMapper;
import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.model.ConfNodeInfo;
import com.conf.template.db.model.ConfNodeTemplate;
import com.conf.template.db.model.ConfRuleInfo;

@Service
public class NodeService {
	@Autowired
	ConfNodeInfoMapper confNodeInfoMapper ;
	@Autowired
	ConfRuleInfoMapper confRuleInfoMapper;
	@Autowired
	ConfNodeTemplateMapper confNodeTemplateMapper;
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
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("nodeId", nodeId);
		return ErrorUtil.successResp(body);
	}
	
	public Map<String, ? extends Object> queryNodeList(Map<String, ? extends Object> data) {
		
		String nodeName = (String) data.get("nodeName");
		String nodeType = (String) data.get("nodeType");
		int  startNum = ((int) data.get("pageSize")-1)*(int) data.get("pageNum")+1;
		int  endNum = (int) data.get("pageSize")*(int) data.get("pageNum");
		List<ConfNodeInfo> list= confNodeInfoMapper.queryNodeList(nodeName, nodeType, startNum, endNum);
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("totalNum", list.size());
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}
	
	public Map<String, ? extends Object> deleteNode(Map<String, ? extends Object> data) {
		int result = confNodeInfoMapper.deleteByPrimaryKey(Integer.valueOf((String) data.get("nodeId")));
		if(result !=1)
		{
			return ErrorUtil.errorResp(ErrorCode.code_0002);
		}
		Map<String,Object> body = new HashMap<String, Object>();
		return ErrorUtil.successResp(body);
	}
	
	public Map<String, ? extends Object> queryRuleByNode(Map<String, ? extends Object> data) {
		int nodeId = (int) data.get("nodeId");
		int  startNum = ((int) data.get("pageSize")-1)*(int) data.get("pageNum")+1;
		int  endNum = (int) data.get("pageSize")*(int) data.get("pageNum");
		List<ConfRuleInfo> list= confRuleInfoMapper.selectRecordListByPage(nodeId, startNum, endNum);
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("totalNum", list.size());
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}
	
	public Map<String, ? extends Object> queryRuleList(Map<String, ? extends Object> data) {
		
		String ruleName = (String) data.get("ruleName");
		int  startNum = ((int) data.get("pageSize")-1)*(int) data.get("pageNum")+1;
		int  endNum = (int) data.get("pageSize")*(int) data.get("pageNum");
		List<ConfRuleInfo> list= confRuleInfoMapper.queryRuleListByName(ruleName, startNum, endNum);
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("totalNum", list.size());
		body.put("list", list);
		return ErrorUtil.successResp(map);
	}
	
	public Map<String, ? extends Object> addRuleByNode(Map<String, ? extends Object> data) {
		
		@SuppressWarnings("unchecked")
		List<String> ruleList = (List<String>) data.get("ruleList");
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		ConfNodeTemplate record = null;
		for(int i=0;i<ruleList.size();i++)
		{
			record = new ConfNodeTemplate();
			int id = ToolsUtil.autoNumber();
			record.setId(id);
			record.setNodeId(Integer.valueOf(nodeId));
			record.setOrg("");
			record.setTeller("");
			record.setUid(Integer.valueOf(ruleList.get(i)));
			confNodeTemplateMapper.insertSelective(record);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}
	
	public Map<String, ? extends Object> deleteRuleByNode(Map<String, ? extends Object> data) {
		
		@SuppressWarnings("unchecked")
		List<String> ruleList = (List<String>) data.get("ruleList");
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		for(int i=0;i<ruleList.size();i++)
		{
			confNodeTemplateMapper.deleteByIdAndUid(Integer.valueOf(nodeId),Integer.valueOf(ruleList.get(i)));
		}
		Map<String,Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}
	
}
