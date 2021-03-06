package com.conf.template.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conf.template.common.Constants;
import com.conf.template.common.ErrorCode;
import com.conf.template.common.ErrorUtil;
import com.conf.template.common.ToolsUtil;
import com.conf.template.db.mapper.ConfNodeInfoMapper;
import com.conf.template.db.mapper.ConfNodeTemplateMapper;
import com.conf.template.db.mapper.ConfProductNodeMapper;
import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.model.ConfNodeInfo;
import com.conf.template.db.model.ConfNodeInfoAndProduct;
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
		confNodeInfo.setRemark((String) data.get("remark"));
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
		List<ConfNodeInfo> list = confNodeInfoMapper.queryNodeList(nodeName, nodeType, startNum, pageSize);
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
		Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null);
		Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
		int startNum = (pageNum - 1) * pageSize;
		List<ConfRuleInfo> list = null;
		if (productId == null) {
			list = confRuleInfoMapper.selectRecordListByPage(nodeId, startNum, pageSize);
		} else {
			list = confRuleInfoMapper.selectEffectRecordListByPage(productId, nodeId, startNum, pageSize);
		}
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
		int totalNum = confRuleInfoMapper.queryCountByName(ruleName);
		List<ConfRuleInfo> list = confRuleInfoMapper.queryRuleListByName(ruleName, startNum, pageSize);
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
	public Map<String, ? extends Object> queryNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
		int startNum = (pageNum - 1) * pageSize;
		List<ConfNodeInfo> list = confNodeInfoMapper.queryNodeByProduct(ToolsUtil.obj2Int(productId, null), startNum, pageSize);
		int totalNum = confNodeInfoMapper.queryNodeCountByProduct(ToolsUtil.obj2Int(productId, null));
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("totalNum", totalNum);
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}	
	
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> addNodeByProduct(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId"));
		String teller = ToolsUtil.obj2Str(data.get("teller"));
		String org = ToolsUtil.obj2Str(data.get("org"));
		List<Map<String, Object>> nodeList = (List<Map<String, Object>>) data.get("nodeList");
		//通过nodeId查询Conf_Node_Template表里对应的uid
		ConfProductNode record = null;
		for (int i = 0; i < nodeList.size(); i++) {
			int nodeId = ToolsUtil.obj2Int(nodeList.get(i).get("nodeId"), null);
			List<ConfNodeTemplate> templateList = 
					confNodeTemplateMapper.confNodeTemplateList(nodeId);
			if (templateList == null || templateList.size() == 0) {
				return ErrorUtil.errorResp(ErrorCode.code_0003, nodeId);
			}
			for (int j = 0; j < templateList.size(); j++) {
				record =new ConfProductNode();
				record.setNodeId(nodeId);
				record.setProductId(ToolsUtil.obj2Int(productId, null));
				record.setUid(templateList.get(j).getUid());
				record.setOrg(org);
				record.setEffect(Constants.EFFECT_STATUS_VALID);
				record.setTeller(teller);
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
		List<Map<String, Object>> nodeList = (List<Map<String, Object>>) data.get("nodeList");
		for (int i = 0; i < nodeList.size(); i++) {
			int nodeId = ToolsUtil.obj2Int(nodeList.get(i).get("nodeId"), null);
			confProductNodeMapper.deleteByProductAndNodeId(ToolsUtil.obj2Int(productId, null), nodeId);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}
	
	@Transactional
	public Map<String, ? extends Object> batchQueryNodeByProduct(Map<String, ? extends Object> data) {
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
		int startNum = (pageNum - 1) * pageSize;
		//先查询产品ID
		List<ConfNodeInfoAndProduct> productList= confProductNodeMapper.batchQueryNodeByProduct(startNum, pageSize);
		Map<String, Object> body = new HashMap<String, Object>();		
		int totalNum = confProductNodeMapper.queryProductIdCount(startNum, pageSize);
		body.put("totalNum", totalNum);
		List<Map<String,Object>> array = new ArrayList<>();		
		for (ConfNodeInfoAndProduct product : productList) {
			Map<String,Object> map = new HashMap<>();
			map.put("productId", product.getProductId());
			if(null !=product.getNodeList())
			{
				map.put("list", product.getNodeList());
			}			
			array.add(map);
		}
		body.put("array", array);
		return ErrorUtil.successResp(body);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> modifyNodeByProduct(Map<String, ? extends Object> data) {
		Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
		Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null);
		String teller = ToolsUtil.obj2Str(data.get("teller"));
		String org = ToolsUtil.obj2Str(data.get("org"));
		List<Map<String, Object>> ruleList = (List<Map<String, Object>>) data.get("ruleList");
		//对传过来的sequence进行判断
		List<String> seqList = new ArrayList<>();
		Map<String, Object> ruleMap = new HashMap<>();
		for(int i=0;i<ruleList.size();i++)
		{
			String sequence = ToolsUtil.obj2Str(ruleList.get(i).get("sequence"));
			seqList.add(sequence);
			ruleMap.put(sequence, ruleList.get(i));
		}
		//按照sequence大小进行修改
		Arrays.sort( seqList.toArray());
		List<Integer> uidList = new ArrayList<>();
		for(String i: seqList){
			Map<String, Object> rule = (Map<String, Object>) ruleMap.get(i);
			Integer uid = ToolsUtil.obj2Int(rule.get("uid"),null);
			uidList.add(uid);
			String effect = ToolsUtil.obj2Str(rule.get("effect"));
			int num = confProductNodeMapper.updateEffectStatus(productId, nodeId, uid, effect, Integer.parseInt(i));			
			if (num == 0) {
				ConfProductNode productNode = new ConfProductNode();
				productNode.setNodeId(nodeId);
				productNode.setProductId(productId);
				productNode.setUid(uid);
				productNode.setEffect(effect);
				productNode.setTeller(teller);
				productNode.setOrg(org);
				productNode.setSequence(Constants.MAX_SEQUENCE);
				confProductNodeMapper.insertSelective(productNode);
			}
			//使未传输的对象失效
			confProductNodeMapper.updateInvalidStatus(productId, nodeId, uidList, Constants.EFFECT_STATUS_INVALID, Constants.MAX_SEQUENCE);
			}
		Map<String, Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}
}
