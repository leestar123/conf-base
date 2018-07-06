package com.conf.template.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.nanjingbank.api.riskmanagement.entity.PostLoanBeanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.urule.Utils;
import com.bstek.urule.console.repository.model.FileType;
import com.conf.client.RuleInvokerService;
import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
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
        
        try
        {
            logger.info("Urule创建空项目[" + confNodeInfo.getNodeName() + "]");
            invokerService.createProject(confNodeInfo.getNodeName());
        }
        catch (Exception e)
        {
            logger.error("创建组件[" + confNodeInfo.getNodeName() + "]失败!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
        }
        
        int result = confNodeInfoMapper.insertSelective(confNodeInfo);
        if (result != 1)
        {
            return ErrorUtil.errorResp(ErrorCode.code_0002);
        }
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("nodeId", confNodeInfo.getNodeId());
        return ErrorUtil.successResp(body);
    }

	@Transactional
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

	@Transactional
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

	@Transactional
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
            if (StringUtils.isBlank(oldFullPath))
            {
                logger.info("规则[" + ruleName + "]无规则路径，跳过复制！");
                continue;
            } 
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
    public Map<String, ? extends Object> createRule(Map<String, ? extends Object> data) {
        ConfRuleInfo record = new ConfRuleInfo();
		String ruleType = ToolsUtil.obj2Str(data.get("ruleType"));
        String ruleName = ToolsUtil.obj2Str(data.get("ruleName"));
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
        
        logger.info("Urule创建规则[" + ruleName + "]");
        ruleName=Utils.decodeURL(ruleName).trim();
        String path = null;   
        String url = Constants.RULE_URL_BASE;
        try
        {
            path = ToolsUtil.combPath(nodeName, ruleName + "." + ruleType);
            //判断该规则名字是否存在
            if(invokerService.fileExistCheck(path)) {
                return ErrorUtil.errorResp(ErrorCode.code_0005, path);
            }
            //创建目录
            //invokerService.createFlolder(ruleName, nodeName, ruleType);
            //创建规则
            invokerService.createFile(path, ruleType);
            FileType fileType=FileType.parse(ruleType);
            if (FileType.DecisionTable == fileType) {
                url += "decisiontableeditor?file=" + path;
            } else if (FileType.DecisionTree == fileType) {
                url += "decisiontreeeditor?file=" + path;
            } else if (FileType.Ruleset == fileType) {
                url += "ruleseteditor?file=" + path;
            } else if (FileType.Scorecard == fileType) {
                url += "scorecardeditor?file=" + path;
            } else {}
        }
        catch (Exception e)
        {
            logger.error("创建规则[" + ruleName + "]失败!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
        }
        
        //类型转换
        ruleType = ToolsUtil.parse(ruleType);
        record.setOrg(org);
        record.setRuleName(ruleName);
        record.setRulePath(path);
        record.setRuleType(ruleType);
        record.setTeller(teller);
		//入库操作
		confRuleInfoMapper.insertSelective(record);
	    Map<String, Object> body = new HashMap<String, Object>();
	    body.put("uid", record.getUid());
	    body.put("url", url);
        return ErrorUtil.successResp(body);
	}
	
	public Map<String, ? extends Object> ruleflowdesigner(Map<String, ? extends Object> data) {
		String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
		String productName = ToolsUtil.obj2Str(data.get("productName"));
		String path = "/" + nodeName + "/" + productName + ".rl.xml";
        try {
            //判断该决策流文件是否存在
			if(!invokerService.fileExistCheck(path)) {
			    //创建决策流文件
			    invokerService.createFile(path, "rl.xml");
			}
		} catch (Exception e) {
			logger.error("创建空决策流[" + path + "]失败!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
        //保存知识包并进行发布
        //saveAndRefreshKnowledge(data);
		String url = Constants.RULE_URL_BASE + "ruleflowdesigner?file=" +path;
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("url", url);
		
		return ErrorUtil.successResp(body);
	}
	
	public Map<String, ? extends Object> publishKnowledge(Map<String, ? extends Object> data) {
		//知识包编码id
		String packageId = String.valueOf(ToolsUtil.nextSeq());
		//组件名称
		String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
		//知识包名
		String packageName = ToolsUtil.obj2Str(data.get("packageName"));
		//添加知识包文件名
		String fileName = ToolsUtil.obj2Str(data.get("fileName"));
		//决策流文件名称
		String productName = ToolsUtil.obj2Str(data.get("productName"));
		String path = nodeName + "/" + productName + ".rl.xml";
		String xml = invokerService.generateRLXML(packageId, packageName, fileName, path).toString();
		//保存知识包
		try {
			invokerService.saveResourcePackages(false, nodeName, xml);
			//Jcr文件名称
			String files = "jcr:/" + path;
			invokerService.refreshKnowledgeCache(files, packageId, nodeName);
		} catch (Exception e) {
			logger.error("发布知识包[" + path + "]失败!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		return ErrorUtil.successResp(map);
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
        //产品id
        Integer productId = ToolsUtil.obj2Int(data.get("productId"), 0);
        //组件名称
        Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), 0);
        
        List<ConfRuleInfo> list = confRuleInfoMapper.selectRecordList(productId, nodeId, Constants.RULE_TYPE_ACTION, Constants.EFFECT_STATUS_VALID);
        Map<String, Object> body = new HashMap<>();
        body.put("list", list);
        return ErrorUtil.successResp(body);
    }
    
    /**
     * 调用知识包
     * @param data
     * @return
     */
    public Map<String, ? extends Object> excuteKnowledge(Map<String, ? extends Object> data)
    {
    	String packageId = ToolsUtil.obj2Str(data.get("packageId"));
		String processId = ToolsUtil.obj2Str(data.get("processId"));
		String nodeName = ToolsUtil.obj2Str(data.get("nodeName"));
		List<Object> objList = new ArrayList<Object>();
		PostLoanBeanResult postLoanBeanResult = new PostLoanBeanResult();
		objList.add(postLoanBeanResult);
		List<Object> objListUnCheck = new ArrayList<Object>();
    	try {
			invokerService.executeProcess(nodeName+"/"+packageId, objList, objListUnCheck, processId);
		} catch (Exception e) {
			logger.error("调用知识包[" + processId + "]失败!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
    	Map<String, Object> body = new HashMap<>();
        return ErrorUtil.successResp(body);
    }
}
