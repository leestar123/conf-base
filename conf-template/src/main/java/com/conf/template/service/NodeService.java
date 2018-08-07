package com.conf.template.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.Utils;
import com.bstek.urule.console.repository.model.FileType;
import com.bstek.urule.console.repository.model.ResourcePackage;
import com.conf.client.RuleInvokerService;
import com.conf.common.ConfContext;
import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.dto.BuildXMlDto;
import com.conf.common.dto.ConfOperateInfoDto;
import com.conf.common.dto.ModuleInfo;
import com.conf.template.db.dto.ConfNodeInfoAndProduct;
import com.conf.template.db.mapper.ConfFlowInfoMapper;
import com.conf.template.db.mapper.ConfInvokInfoMapper;
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
	
    private final static Logger logger = LoggerFactory.getLogger(NodeService.class);
	
	@Autowired
	ConfNodeInfoMapper confNodeInfoMapper;
	
	@Autowired
	ConfRuleInfoMapper confRuleInfoMapper;
	
	@Autowired
	ConfNodeTemplateMapper confNodeTemplateMapper;
	
	@Autowired
	ConfProductNodeMapper confProductNodeMapper;
	
    @Autowired
    ConfInvokInfoMapper confInvokInfoMapper;
    
    @Autowired
    ConfFlowInfoMapper confFlowInfoMapper;
    
    @Autowired
    private RuleInvokerService invokerService;
	
    public void setInvokerService(RuleInvokerService invokerService)
    {
        this.invokerService = invokerService;
    }

	@Transactional
    public Map<String, ? extends Object> createNode(Map<String, ? extends Object> data)
    {
        // 参数拼装
        ConfNodeInfo confNodeInfo = new ConfNodeInfo();
        confNodeInfo.setNodeName((String)data.get("nodeName"));
        confNodeInfo.setNodeType((String)data.get("nodeType"));
        confNodeInfo.setOrg((String)data.get("org"));
        confNodeInfo.setRemark((String)data.get("remark"));
        confNodeInfo.setTeller((String)data.get("teller"));
        confNodeInfo.setVersion("");
        logger.info("Begin to create node[" + confNodeInfo.getNodeName() + "]!");
        
        ConfOperateInfoDto local = ConfContext.operateLocalGet();
        local.setRemark("节点添加");
        ModuleInfo module = new ModuleInfo();
        module.setOperateType(Constants.OPERATE_TYPE_ADD);
        module.setOperateModule(Constants.OPERATE_MODULE_NODE);
        module.setModuleName(confNodeInfo.getNodeName());
        List<ModuleInfo> list = new ArrayList<>();
        list.add(module);
        local.setModule(list);
        logger.error("Begin to save node info, object is [" + JSONObject.toJSONString(confNodeInfo) + "]!");
        
        // 先查询，如果存在已删除状态的数据，则更新为正常状态
        ConfNodeInfo info = confNodeInfoMapper.queryInfoByNameAndType(confNodeInfo.getNodeName(), confNodeInfo.getNodeType());
        if (info != null && info.getNodeId() != null) {
        	 confNodeInfo.setNodeId(info.getNodeId());
             confNodeInfo.setDeleteFlag(0);
             confNodeInfoMapper.updateDeleteFlagByPrimaryKey(confNodeInfo);
        } else {
        	int result = confNodeInfoMapper.insertSelective(confNodeInfo);
            if (result != 1)
            {
                logger.error("Save node failly, because result doesn`t equal one");
                return ErrorUtil.errorResp(ErrorCode.code_0002);
            }
            try
            {
                logger.info("Begin to  create empty project[" + confNodeInfo.getNodeName() + "] on Urule system");
                invokerService.createProject(confNodeInfo.getNodeName());
                logger.info("End to  create empty project!");
            }
            catch (Exception e)
            {
            	logger.error(e.getMessage(), e);
            	if (e.getMessage().contains("already exist")) {
            		return ErrorUtil.errorResp(ErrorCode.code_0006, confNodeInfo.getNodeName());
            	}
                logger.error("reate empty project[" + confNodeInfo.getNodeName() + "] failly!", e);
                return ErrorUtil.errorResp(ErrorCode.code_9999);
            }
        }
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("nodeId", confNodeInfo.getNodeId());
        module.setModuleId(confNodeInfo.getNodeId());
        return ErrorUtil.successResp(body);
    }

	@Transactional
	public Map<String, ? extends Object> queryNodeList(Map<String, ? extends Object> data) {
	    logger.info("Begin to query node list!");
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
    public Map<String, ? extends Object> deleteNode(Map<String, ? extends Object> data)
    {
        logger.info("Begin to delete node!");
        String[] str = ToolsUtil.obj2Str(data.get("nodeId")).split(",");
        String[] nodeNames = ToolsUtil.obj2Str(data.get("nodeName")).split(",");
        
        ConfOperateInfoDto local = ConfContext.operateLocalGet();
        local.setRemark("节点删除");
        List<ModuleInfo> list = new ArrayList<>();
        local.setModule(list);
        
        if (nodeNames != null && nodeNames.length > 0) {
        	for (String nodeName : nodeNames) {
        		try {
					invokerService.deleteFile(nodeName);
				} catch (Exception e) {
					 logger.error("删除节点[" + nodeName + "]失败!", e);
	                 return ErrorUtil.errorResp(ErrorCode.code_9999);
				}
        	}
        }
        for (String nodeId : str)
        {
            logger.info("Now delete node[" + nodeId + "]!");
            ConfNodeInfo confNodeInfo = new ConfNodeInfo();
            confNodeInfo.setNodeId(Integer.parseInt(nodeId));
            confNodeInfo.setDeleteFlag(1);
            confNodeInfoMapper.updateDeleteFlagByPrimaryKey(confNodeInfo);
            
            ModuleInfo module = new ModuleInfo();
            module.setOperateType(Constants.OPERATE_TYPE_ADD);
            module.setOperateModule(Constants.OPERATE_MODULE_NODE);
            module.setModuleId(Integer.parseInt(nodeId));
            list.add(module);
        }
        Map<String, Object> body = new HashMap<String, Object>();
        return ErrorUtil.successResp(body);
    }

	@Transactional
	public Map<String, ? extends Object> queryRuleByNode(Map<String, ? extends Object> data) {
	    logger.info("Begin to query rule by node!");
		Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null);
		Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
		int startNum = (pageNum - 1) * pageSize;
		List<ConfRuleInfo> list = null;
		if (productId == null) {
		    logger.info("Field productId is null, query rule without, field productId!");
			list = confRuleInfoMapper.selectRecordListByPage(nodeId, startNum, pageSize);
		} else {
		    logger.info("Begin to query rule by node!");
			list = confRuleInfoMapper.selectEffectRecordListByPage(productId, nodeId, startNum, pageSize);
		}
		int totalNum = confRuleInfoMapper.queryCountByNodeId(nodeId);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("totalNum", totalNum);
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}

	@Transactional
	public Map<String, ? extends Object> queryRuleList(Map<String, ? extends Object> data) {
		String ruleName = ToolsUtil.obj2Str(data.get("ruleName"));
		String ruleType = ToolsUtil.obj2Str(data.get("ruleType"));
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
		int startNum = (pageNum - 1) * pageSize;
		int totalNum = confRuleInfoMapper.queryCountByName(ruleName, ruleType);
		List<ConfRuleInfo> list = confRuleInfoMapper.queryRuleListByName(ruleName, ruleType, startNum, pageSize);
		// Map<String,Object> map = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("totalNum", totalNum);
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}

	@Transactional
	@SuppressWarnings("unchecked")
    public Map<String, ? extends Object> addRuleByNode(Map<String, ? extends Object> data)
    {
        List<Map<String, Object>> ruleList = (List<Map<String, Object>>)data.get("ruleList");
        String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
        String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
        ConfNodeTemplate record = null;
        //规则名称
        String ruleName = null;
        //规则类型
        String ruleType = null;
        //原始规则路径
        String oldFullPath = null;
        //要创建的规则路径
        String newFullPath = null;
        
        ConfOperateInfoDto local = ConfContext.operateLocalGet();
        local.setRemark("节点关联组件添加");
        
        for (int i = 0; i < ruleList.size(); i++)
        {
            record = new ConfNodeTemplate();
            record.setNodeId(Integer.parseInt(nodeId));
            record.setOrg("");
            record.setTeller("");
            record.setUid(ToolsUtil.obj2Int(ruleList.get(i).get("uid"), null));
            ruleName = ToolsUtil.obj2Str(ruleList.get(i).get("ruleName"));
            ruleType = ToolsUtil.obj2Str(ruleList.get(i).get("ruleType"));
            oldFullPath = ToolsUtil.obj2Str(ruleList.get(i).get("rulePath"));
            
            ModuleInfo module = new ModuleInfo();
            module.setOperateType(Constants.OPERATE_TYPE_ADD);
            module.setOperateModule(Constants.OPERATE_MODULE_BUND_NODERULE);
            module.setModuleName(ruleName);
            List<ModuleInfo> list = new ArrayList<>();
            list.add(module);
            local.setModule(list);
            // 查询是否存在已删除状态的记录。如果有，则修改为正常状态；否则新增记录
            ConfNodeTemplate confNodeTemplate = confNodeTemplateMapper.selectByNodIdAndUid(record.getNodeId(), record.getUid(), 1);
            if (confNodeTemplate != null && confNodeTemplate.getId() != null) {
            	record.setId(confNodeTemplate.getId());
            	record.setDeleteFlag(0);
            	confNodeTemplateMapper.updateByPrimaryKeySelective(record);
            } else {
            	confNodeTemplateMapper.insertSelective(record);
            	if (!Constants.RULE_TYPE_ACTION.equals(ruleType) && StringUtils.isBlank(oldFullPath))
                {
                    logger.info("规则[" + ruleName + "]无规则路径，跳过复制！");
                } else if (!Constants.RULE_TYPE_ACTION.equals(ruleType)) {

                    ruleType = ToolsUtil.unParse(ruleType);
                    //规则复制
                    newFullPath = ToolsUtil.combPath(nodeName, ruleName + "." + ruleType);
                    try
                    {
                        if (oldFullPath.equals(newFullPath))
                        {
                            logger.info("规则[" + ruleName + "]存在当前组件，跳过复制！");
                        }
                        else
                        {
                            invokerService.copyFile(newFullPath, oldFullPath);
                        }
                    }
                    catch (Exception e)
                    {
                        logger.error("规则复制[" + newFullPath + "]失败!", e);
                        return ErrorUtil.errorResp(ErrorCode.code_9999);
                    }
                }
            }
            module.setModuleId(record.getId());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        return ErrorUtil.successResp(map);
    }

	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, ? extends Object> deleteRuleByNode(Map<String, ? extends Object> data) {
	    ConfOperateInfoDto local = ConfContext.operateLocalGet();
        local.setRemark("节点关联组件删除");
        
		List<Map<String, Object>> ruleList = (List<Map<String, Object>>) data.get("ruleList");
		Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null);
		for (Map<String, Object> map : ruleList) {
		    Integer uid = ToolsUtil.obj2Int(map.get("uid"), null);
		    ConfNodeTemplate template = confNodeTemplateMapper.selectByNodIdAndUid(nodeId, uid, Constants.DELETE_STATUS_NO);
            ModuleInfo module = new ModuleInfo();
            module.setOperateType(Constants.OPERATE_TYPE_DEl);
    	    module.setOperateModule(Constants.OPERATE_MODULE_BUND_NODERULE);
            module.setModuleName("");
            module.setModuleId(template.getId());
            List<ModuleInfo> list = new ArrayList<>();
            list.add(module);
            local.setModule(list);
			confNodeTemplateMapper.deleteForLogicByIdAndUid(nodeId, uid);
			//TODO:删除Urule文件
			String rulePath = ToolsUtil.obj2Str(map.get("rulePath"));
			String nodeName = ToolsUtil.obj2Str(map.get("nodeName"));
			String[] path = rulePath.split("/");
			if (!(path.length > 1 && path[1].equals(nodeName))) {
				try {
					invokerService.deleteFile(nodeName);
				} catch (Exception e) {
					return ErrorUtil.errorResp(ErrorCode.code_9999);
				}
			} 
		}

		Map<String, Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
	}

	@Transactional
	public Map<String, ? extends Object> deleteRule(Map<String, ? extends Object> data) {
		Map<String, Object> body = new HashMap<String, Object>();
		ConfOperateInfoDto local = ConfContext.operateLocalGet();
		local.setRemark("组件删除");
		Integer uid = ToolsUtil.obj2Int(data.get("uid"), null);
		ConfRuleInfo record = confRuleInfoMapper.selectByPrimaryKey(uid);
		if (record == null || StringUtils.isBlank(record.getRulePath())) {
			logger.info("rule info is null or rule path is null!");
			return ErrorUtil.successResp(body);
		}

		List<Map<String, Object>> list = confNodeTemplateMapper.queryNodeTemplateList(uid, Constants.DELETE_STATUS_NO);
		if (list != null && !list.isEmpty()) {
			logger.error("Rule has bunded some node,then can`t delete!");
			String nodeName = ToolsUtil.obj2Str(list.get(0).get("node_name"));
			return ErrorUtil.errorResp(ErrorCode.code_0013, nodeName);
		}

		try {
			logger.info("Begint to delete urule file[" + record.getRulePath() + "]");
			if (invokerService.existCheck(record.getRulePath()))
			{
				invokerService.deleteFile(record.getRulePath());
				logger.info("End to delete urule file");
			}
			ConfRuleInfo nodeInfo = new ConfRuleInfo();
			nodeInfo.setUid(uid);
			nodeInfo.setDeleteFlag(Constants.DELETE_STATUS_YES);
			confRuleInfoMapper.updateByPrimaryKeySelective(nodeInfo);
			return ErrorUtil.successResp(body);
		} catch (Exception e) {
			logger.info("Delete rule file failly!", e);
			return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
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
				// 查询是否存在已删除状态的数据，如果有，则更新为正常状态，否则新增数据
				ConfProductNode  confProductNode  = confProductNodeMapper.selectByCondition(record);
				if (confProductNode != null && confProductNode.getId() != null) {
					record.setId(confProductNode.getId());
					record.setDeleteFlag(0);
					confProductNodeMapper.updateByPrimaryKeySelective(record);
				} else {
					confProductNodeMapper.insertSelective(record);
				}
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
			confProductNodeMapper.deleteForLogicByProductAndNodeId(ToolsUtil.obj2Int(productId, null), nodeId);
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
			map.put("productName", product.getProductName());
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
	
	@Transactional
    public Map<String, ? extends Object> createRule(Map<String, ? extends Object> data)
    {
        ConfRuleInfo record = new ConfRuleInfo();
        String ruleType = ToolsUtil.obj2Str(data.get("ruleType"));
        String ruleName = ToolsUtil.obj2Str(data.get("ruleName"));
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        //String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
        String remark = ToolsUtil.obj2Str(data.get("remark"));
        
		List<ConfNodeInfo> nodeList = confNodeInfoMapper.queryNodeList(null, null, 0, 1);
        if(nodeList == null || nodeList.size() == 0)
        {
        	return ErrorUtil.errorResp(ErrorCode.code_0012);
        }
        String nodeName = nodeList.get(0).getNodeName();
        
        ruleName = Utils.decodeURL(ruleName).trim();
        String path = null;
        String url = Constants.RULE_URL_BASE;
        
        ConfOperateInfoDto local = ConfContext.operateLocalGet();
        local.setRemark("组件创建");
        
        ModuleInfo module = new ModuleInfo();
        module.setOperateType(Constants.OPERATE_TYPE_ADD);
        module.setOperateModule(Constants.OPERATE_MODULE_RULE);
        module.setModuleName(ruleName);
        List<ModuleInfo> list = new ArrayList<>();
        list.add(module);
        local.setModule(list);
        try
        {
            path = ToolsUtil.combPath(nodeName, ruleName + "." + ruleType);
            logger.info("Rule path is [" + ruleName + "]");
            //判断该规则名字是否存在
            if (invokerService.fileExistCheck(path))
            {
                return ErrorUtil.errorResp(ErrorCode.code_0005, path);
            }
            
            logger.info("Begin to create rule[" + ruleName + "] on Urule of system");
            invokerService.createFile(path, ruleType);
            FileType fileType = FileType.parse(ruleType);
            if (FileType.DecisionTable == fileType)
            {
                url += "decisiontableeditor?file=" + path;
            }
            else if (FileType.DecisionTree == fileType)
            {
                url += "decisiontreeeditor?file=" + path;
            }
            else if (FileType.Ruleset == fileType)
            {
                url += "ruleseteditor?file=" + path;
            }
            else if (FileType.Scorecard == fileType)
            {
                url += "scorecardeditor?file=" + path;
            }
            else
            {
            }
        }
        catch (Exception e)
        {
            logger.error("Create urle[" + ruleName + "] failly!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
        }
        
        record.setOrg(org);
        record.setRuleName(ruleName);
        record.setRulePath(path);
        ruleType = ToolsUtil.parse(ruleType);
        record.setRuleType(ruleType);
        record.setTeller(teller);
        record.setRemark(remark);
        
        logger.info("Begin to save rule[" + ruleName + "], Object is[" + JSONObject.toJSONString(record) + "]!");
        confRuleInfoMapper.insertSelective(record);
        
//        Map<String, Object> newData = new HashMap<>();
//        List<Map<String, Object>> ruleList = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("uid", record.getUid());
//        map.put("ruleType", ruleType);
//        map.put("ruleName", data.get("ruleName"));
//        ruleList.add(map);
//        newData.put("ruleList", ruleList);
//        newData.put("nodeName", data.get("nodeName"));
//        newData.put("nodeId", data.get("nodeId"));
//        addRuleByNode(newData);
        
        Map<String, Object> body = new HashMap<>();
        body.put("uid", record.getUid());
        body.put("url", url);
        module.setModuleId(record.getUid());
        return ErrorUtil.successResp(body);
    }
	
	/**
	 * 刷新规则，因为规则复用是通过拷贝方式，因此需要刷新其他工程下的组件
	 * 
	 * @param data
	 * @return
	 */
	@Transactional
    public Map<String, ? extends Object> refreshRule(Map<String, ? extends Object> data)
    {
		Map<String, Object> body = new HashMap<>();
		Integer uid = ToolsUtil.obj2Int(data.get("uid"), null);
		ConfRuleInfo ruleInfo = confRuleInfoMapper.selectByPrimaryKey(uid);
		if (ruleInfo == null || StringUtils.isBlank(ruleInfo.getRulePath())) {
			logger.info("rule info is null or rule path is null!");
			return ErrorUtil.successResp(body);
		}
		String nodeName = ruleInfo.getRulePath().split("/")[1];
		List<Map<String, Object>> list = confNodeTemplateMapper.queryNodeTemplateList(uid, Constants.DELETE_STATUS_NO);
		if (list == null || list.size() == 0) {
			logger.info("rule doesn`t bund any nodes,then skip!");
			return ErrorUtil.successResp(body);
		}
		try {
			for (Map<String, Object> map : list) {
				String newNodeName = ToolsUtil.obj2Str(map.get("node_name"));
				String ruleName = ToolsUtil.obj2Str(map.get("rule_name"));
				String ruleType = ToolsUtil.obj2Str(map.get("rule_type"));
				if (nodeName.equals(newNodeName)) {
					logger.info("Node [" + newNodeName + "] skip refresh");
					continue;
				}
				String path = ToolsUtil.combPath(nodeName, ruleName + "." + ruleType);
				logger.info("Begin to save urule file[" + path + "]!");
				Document doc = invokerService.getFileSource(path);
				String xml = doc.asXML();
				invokerService.saveFile(path, xml);
				logger.info("End to save urule file!");
			}
			return ErrorUtil.successResp(body);
		}catch (Exception e) {
			logger.error("Save urule file failly!", e);
			return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
    }
	
    public Map<String, ? extends Object> ruleflowdesigner(Map<String, ? extends Object> data)
    {
        String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
        String productName = ToolsUtil.obj2Str(data.get("productName"));
        String productId = ToolsUtil.obj2Str(data.get("productId"));
        String nodeId = ToolsUtil.obj2Str(data.get("nodeId"));
        String path = "/" + nodeName + "/" + productName + ".rl.xml";
        try
        {
            logger.info("Check Node ["+path+"] whether or not exist");
            //判断该决策流文件是否存在
            if (!invokerService.fileExistCheck(path))
            {
                logger.info("Node ["+path+"] don`t exist,bgin to create flow file!");
                //创建决策流文件
                invokerService.createFile(path, "rl.xml");
                logger.info("create urule flow file successlly!");
            }
        }
        catch (Exception e)
        {
            logger.error("create urule flow file failly!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
        }
        String url = Constants.RULE_URL_BASE
            + "ruleflowdesigner?file=".concat(path).concat("&productId=" + productId).concat("&nodeId=" + nodeId);
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("url", url);
        
        return ErrorUtil.successResp(body);
    }
	
    /**
     * 此方法需要加锁执行
     * 添加到内存，异步执行
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Map<String, ? extends Object> publishKnowledge(Map<String, ? extends Object> data)
	{
		logger.info("Begin to publish knowledge!");
		try {
			List<BuildXMlDto> bind = JSONObject.parseArray(JSONObject.toJSONString(data.get("bind")),
					BuildXMlDto.class);
			String nodeName = bind.get(0).getNodeName();
			List<ResourcePackage> packages = invokerService.loadProjectResourcePackages(nodeName);

			logger.info("Begin to generate RLXML!");
			invokerService.generateRLXML(bind, packages);
			String xml = invokerService.buildXML(packages).toString();
			//String xml = "<?xml version='1.0' encoding='utf-8'?><res-packages><res-package id='80000088' name='80000088' create_date='2018-07-30 14:06:00'><res-package-item  name='GeneralPrescreening' path='jcr:/资质审查/资质审查-预筛选/通用预筛选.rl.xml' version='LATEST'/></res-package>\r\n" + 
			//		"</res-packages>";
			logger.debug("RLXML context is [" + xml + "] !");

			logger.info("Begin to save packages!");
			invokerService.saveResourcePackages(nodeName, xml);
			logger.info("End to save packages!");

			for (BuildXMlDto dto : bind) {
				String files = dto.getPath().startsWith("/") ? "jcr:" + dto.getPath() : "jcr:/" + dto.getPath();
				logger.info("Begin to refresh packages, file path is [" + files + "] !");
				invokerService.refreshKnowledgeCache(files, dto.getProductId(), nodeName);
				logger.info("End to refresh packages!");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			return ErrorUtil.successResp(map);
		} catch (Exception e) {
			logger.error("Publish knowledge failly!", e);
			return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
	}
	
	/**
     * 根查询动作规则
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Map<String, ? extends Object> queryActionRule(Map<String, ? extends Object> data)
    {
        logger.info("Begin to query action rules!");
        //组件名称
        Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), 0);
        
        List<ConfRuleInfo> list = confRuleInfoMapper.selectRuleList(nodeId, Constants.RULE_TYPE_ACTION);
        Map<String, Object> body = new HashMap<>();
        body.put("list", list);
        return ErrorUtil.successResp(body);
    }
    
    /**
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * @param nodeId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public ConfNodeInfo queryNodeByStepId(Integer stepId) {
        return confNodeInfoMapper.queryNodeByStep(stepId);
    }
}
