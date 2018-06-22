package com.conf.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conf.template.common.ErrorCode;
import com.conf.template.common.ErrorUtil;
import com.conf.template.common.ToolsUtil;
import com.conf.template.db.mapper.ConfNodeInfoMapper;
import com.conf.template.db.mapper.ConfNodeTemplateMapper;
import com.conf.template.db.mapper.ConfProductNodeMapper;
import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.model.ConfNodeInfo;
import com.conf.template.db.model.ConfNodeTemplate;
import com.conf.template.db.model.ConfProductNode;
import com.conf.template.db.model.ConfRuleInfo;

@Service
public class NodeService {
	
	@Autowired
	ConfNodeInfoMapper confNodeInfoMapper;
	
	@Autowired
	ConfRuleInfoMapper confRuleInfoMapper;
	
	@Autowired
	ConfNodeTemplateMapper confNodeTemplateMapper;
	
	@Autowired
	ConfProductNodeMapper confProductNodeMapper;

	@Transactional
	public Map<String, ? extends Object> createNode(Map<String, ? extends Object> data) {
		// 参数拼装
		ConfNodeInfo confNodeInfo = new ConfNodeInfo();
		confNodeInfo.setNodeName((String) data.get("nodeName"));
		confNodeInfo.setNodeType((String) data.get("nodeType"));
		confNodeInfo.setOrg((String) data.get("org"));
		confNodeInfo.setRemark((String) data.get("remark "));
		confNodeInfo.setTeller((String) data.get("teller"));
		confNodeInfo.setVersion("");
		int result = confNodeInfoMapper.insertSelective(confNodeInfo);
		if (result != 1) {
			return ErrorUtil.errorResp(ErrorCode.code_0002);
		}
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("nodeId", confNodeInfo.getNodeId());
		return ErrorUtil.successResp(body);
	}

	public Map<String, ? extends Object> queryNodeList(Map<String, ? extends Object> data) {

		String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
		String nodeType = ToolsUtil.obj2Str(data.get("nodeType"));
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);

		int startNum = (pageNum - 1) * pageSize;
		int endNum = pageNum * pageSize;
		List<ConfNodeInfo> list = confNodeInfoMapper.queryNodeList(nodeName, nodeType, startNum, endNum);
		int totalNum = confNodeInfoMapper.queryCount(nodeName, nodeType);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("totalNum", totalNum);
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}

	@Transactional
	public Map<String, ? extends Object> deleteNode(Map<String, ? extends Object> data) {

		if (!ToolsUtil.obj2Str(data.get("nodeId")).contains(",")) {
			confNodeInfoMapper.deleteByPrimaryKey(Integer.parseInt(ToolsUtil.obj2Str(data.get("nodeId"))));
		} else {
			String[] str = ToolsUtil.obj2Str(data.get("nodeId")).split(",");
			for (int i = 0; i < str.length; i++) {
				confNodeInfoMapper.deleteByPrimaryKey(Integer.parseInt(str[i]));
			}
		}
		Map<String, Object> body = new HashMap<String, Object>();
		return ErrorUtil.successResp(body);
	}

	public Map<String, ? extends Object> queryRuleByNode(Map<String, ? extends Object> data) {
		int nodeId = Integer.parseInt(ToolsUtil.obj2Str(data.get("nodeId")));
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
		int startNum = (pageNum - 1) * pageSize;
		int endNum = pageNum * pageSize;
		List<ConfRuleInfo> list = confRuleInfoMapper.selectRecordListByPage(nodeId, startNum, endNum);
		int totalNum = confRuleInfoMapper.queryCountByNodeId(nodeId);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("totalNum", totalNum);
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}

	public Map<String, ? extends Object> queryRuleList(Map<String, ? extends Object> data) {
		String ruleName = ToolsUtil.obj2Str(data.get("ruleName"));
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
		int startNum = (pageNum - 1) * pageSize;
		int endNum = pageNum * pageSize;
		int totalNum = confRuleInfoMapper.queryCountByName(ruleName);
		List<ConfRuleInfo> list = confRuleInfoMapper.queryRuleListByName(ruleName, startNum, endNum);
		// Map<String,Object> map = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("totalNum", totalNum);
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> addRuleByNode(Map<String, ? extends Object> data) {

		List<Map<String, Object>> ruleList = (List<Map<String, Object>>) data.get("ruleList");
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		ConfNodeTemplate record = null;
		for (int i = 0; i < ruleList.size(); i++) {
			record = new ConfNodeTemplate();
			record.setNodeId(Integer.parseInt(nodeId));
			record.setOrg("");
			record.setTeller("");
			record.setUid(ToolsUtil.obj2Int(ruleList.get(i).get("uid"), null));
			confNodeTemplateMapper.insertSelective(record);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> deleteRuleByNode(Map<String, ? extends Object> data) {

		List<Map<String, Object>> ruleList = (List<Map<String, Object>>) data.get("ruleList");
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
		for (Map<String, Object> map : ruleList) {
			confNodeTemplateMapper.deleteByIdAndUid(Integer.valueOf(nodeId), ToolsUtil.obj2Int(map.get("uid"), null));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> queryNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
		int startNum = (pageNum - 1) * pageSize;
		int endNum = pageNum * pageSize;
		List<ConfNodeInfo> list = confNodeInfoMapper.queryNodeByProduct(productId, startNum, endNum);
		int totalNum = confNodeInfoMapper.queryNodeCountByProduct(productId);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("totalNum", totalNum);
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}	
	
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> addNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> nodeList = (List<Map<String, Object>>) data.get("nodeList");
		//通过nodeId查询Conf_Node_Template表里对应的uid
		ConfProductNode record = null;
		for (int i = 0; i < nodeList.size(); i++) {
			int nodeId = ToolsUtil.obj2Int(nodeList.get(i).get("nodeId"), null);
			List<ConfNodeTemplate> templateList = 
					confNodeTemplateMapper.confNodeTemplateList(nodeId);
			for (int j = 0; j < templateList.size(); j++) {
				record =new ConfProductNode();
				record.setNodeId(nodeId);
				record.setOrg(0);
				record.setProductId(ToolsUtil.obj2Int(productId, null));
				record.setTeller(0);
				record.setUid(templateList.get(j).getUid());
				confProductNodeMapper.insertSelective(record);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> deleteNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> nodeList = (List<Map<String, Object>>) data.get("nodeList");
		for (int i = 0; i < nodeList.size(); i++) {
			int nodeId = ToolsUtil.obj2Int(nodeList.get(i).get("nodeId"), null);
			confProductNodeMapper.deleteByProductAndNodeId(ToolsUtil.obj2Int(productId, null), nodeId);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}
}
