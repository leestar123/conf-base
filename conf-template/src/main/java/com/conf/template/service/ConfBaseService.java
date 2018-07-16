package com.conf.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.conf.client.RuleInvokerService;
import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.dto.ConfOperateInfoDto;
import com.conf.common.dto.ModuleInfo;
import com.conf.template.db.dto.ConfProductAndStepAndFLow;
import com.conf.template.db.dto.ConfStepAndFLowInfo;
import com.conf.template.db.mapper.ConfFlowInfoMapper;
import com.conf.template.db.mapper.ConfInvokInfoMapper;
import com.conf.template.db.mapper.ConfNodeInfoMapper;
import com.conf.template.db.mapper.ConfOperateInfoMapper;
import com.conf.template.db.mapper.ConfProductStepMapper;
import com.conf.template.db.mapper.ConfStepInfoMapper;
import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfInvokInfo;
import com.conf.template.db.model.ConfNodeInfo;
import com.conf.template.db.model.ConfProductStep;
import com.conf.template.db.model.ConfStepInfo;

@Service
public class ConfBaseService
{
    
    private final static Logger logger = LoggerFactory.getLogger(ConfBaseService.class);
    
    @Autowired
    private ConfStepInfoMapper confStepInfoMapper;
    
    @Autowired
    private ConfFlowInfoMapper confFlowInfoMapper;
    
    @Autowired
    private ConfNodeInfoMapper confNodeInfoMapper;
    
    @Autowired
    private ConfInvokInfoMapper confInvokInfoMapper;
    
    @Autowired
    private ConfOperateInfoMapper confOperateInfoMapper;
    
    @Autowired
    private RuleInvokerService invokerService;
    
    @Autowired
    private ConfProductStepMapper confProductStepMapper;
    
    public void setInvokerService(RuleInvokerService invokerService)
    {
        this.invokerService = invokerService;
    }

    private ConfOperateInfoDto local = ToolsUtil.operateLocalGet();
    
    /**
     * 查询录入阶段的详细信息
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public Map<String, ? extends Object> queryFlowDetail(Map<String, ? extends Object> data)
    {
        String flowPath = ToolsUtil.obj2Str(data.get("flowPath"));
        try {
            List<String> nameList = new ArrayList<>();
            List<String> beanList = new ArrayList<>();
            
            Document document = invokerService.getFileSource(flowPath);
            Element root = document.getRootElement();
            //查询规则
            List<Element> ruleList = root.elements("rule");
            for (Element element : ruleList)
            {
                String file = element.attributeValue("file");
                file = file.substring(file.lastIndexOf("/") +  1, file.length());
                file.substring(0, file.indexOf("."));
                nameList.add(file);
            }
            //查询动作
            List<Element> actionList = root.elements("action");
            for (Element element : actionList)
            {
                String bean = element.attributeValue("action-bean");
                beanList.add(bean);
            }
            
        } catch (Exception e) {
            
        }
        return null;
    }
    
    /**
     * 查询调用日志
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public Map<String, ? extends Object> queryOperateLog(Map<String, ? extends Object> data)
    {
        Integer operateType = ToolsUtil.obj2Int(data.get("operateType"), null);
        Integer operateModule = ToolsUtil.obj2Int(data.get("operateModule"), null);
        Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
        Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
        Integer totalNum = confOperateInfoMapper.selectTotalRecord(operateType, operateModule);
        List<ConfInvokInfo> list =
            confOperateInfoMapper.selectRecordWithPage(operateType, operateModule, (pageNum - 1) * pageSize, pageSize);
        
        Map<String, Object> body = new HashMap<>();
        body.put("totalNum", totalNum);
        body.put("list", list);
        return ErrorUtil.successResp(body);
    }
    
    /**
     * 查询调用日志
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public Map<String, ? extends Object> queryInvokLog(Map<String, ? extends Object> data)
    {
        String service = ToolsUtil.obj2Str(data.get("service"));
        Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10);
        Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);
        Integer totalNum = confInvokInfoMapper.selectTotalRecord(service);
        List<ConfInvokInfo> list = confInvokInfoMapper.selectRecordWithPage(service, (pageNum - 1)*pageSize, pageSize);
       
        Map<String, Object> body = new HashMap<>();
        body.put("totalNum", totalNum);
        body.put("list", list);
        return ErrorUtil.successResp(body);
    }
    
    /**
     *  在阶段上创建流程
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public Map<String, ? extends Object> createFlow(Map<String, ? extends Object> data)
    {
        Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null);
        String flowName = ToolsUtil.obj2Str(data.get("flowName"));
        String remark = ToolsUtil.obj2Str(data.get("remark"));
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        
        local.setOperateType(Constants.OPERATE_TYPE_ADD);
        local.setOperateModule(Constants.OPERATE_MODULE_FLOW);
        ModuleInfo module = new ModuleInfo();
        module.setModuleName(flowName);
        List<ModuleInfo> list = new ArrayList<>();
        list.add(module);
        local.setModule(list);
        
        ConfStepInfo stepInfo = confStepInfoMapper.selectByPrimaryKey(stepId);
        if (stepInfo == null)
        {
            logger.error(ErrorCode.code_0007.getMsg(stepId));
            return ErrorUtil.errorResp(ErrorCode.code_0007, stepId);
        }
        
        ConfNodeInfo nodeInfo = confNodeInfoMapper.selectByPrimaryKey(stepInfo.getNodeId());
        if (nodeInfo == null)
        {
            logger.error(ErrorCode.code_0008.getMsg(stepInfo.getNodeId()));
            return ErrorUtil.errorResp(ErrorCode.code_0008, stepInfo.getNodeId());
        }
        String path =
            "/" + nodeInfo.getNodeName().concat("-").concat(stepInfo.getStepName()).concat("/").concat(flowName).concat(
                ".rl.xml");
        try
        {
            logger.info("Check Node [" + path + "] whether or not exist");
            //判断该决策流文件是否存在
            if (!invokerService.fileExistCheck(path))
            {
                logger.info("Node [" + path + "] don`t exist,bgin to create flow file!");
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
        
        ConfFlowInfo record = new ConfFlowInfo();
        record.setFlowName(flowName);
        record.setFlowPath(path);
        record.setRemark(remark);
        record.setStepId(stepId);
        record.setOrg(org);
        record.setTeller(teller);
        confFlowInfoMapper.insertSelective(record);
        module.setModuleId(record.getFlowId());
        
        return ErrorUtil.successResp(new HashMap<>());
    }
    
    /**
     * 查询节点下的各个阶段，以及各个阶段下的流程
     * 
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public Map<String, ? extends Object> queryStepFlow(Map<String, ? extends Object> data)
    {
        Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null);
        List<ConfStepAndFLowInfo> queryList = confStepInfoMapper.selectStepAndFlowInfo(nodeId);
        Map<String, Object> body = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        body.put("array", list);
        for (ConfStepAndFLowInfo confStepAndFLowInfo : queryList)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("stepId", confStepAndFLowInfo.getStepId());
            map.put("stepName", confStepAndFLowInfo.getStepName());
            map.put("list", confStepAndFLowInfo.getFlowList());
            list.add(map);
        }
        return ErrorUtil.successResp(body);
    }
	
    /**
     * 阶段创建
     * @param data
     * @return
     */
	@Transactional
    public Map<String, ? extends Object> createConfStepInfo(Map<String, ? extends Object> data)
    {
        // 参数拼装
		ConfStepInfo confStepInfo = new ConfStepInfo();
		confStepInfo.setNodeId((Integer)data.get("nodeId")); // 组件编号
		confStepInfo.setStepName((String)data.get("stepName")); // 阶段名称
		confStepInfo.setRemark((String)data.get("remark")); // 组件描述
		confStepInfo.setTeller((String)data.get("teller")); // 操作柜员
		confStepInfo.setOrg((String)data.get("org")); // 操作机构
		
		// 调用urule创建工程
        try
        {
            logger.info("Begin to  create empty project[" + confStepInfo.getStepName() + "] on Urule system");
            invokerService.createProject(confStepInfo.getStepName());
            logger.info("End to  create empty project!");
        }
        catch (Exception e)
        {
            logger.error("reate empty project[" + confStepInfo.getStepName() + "] failly!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
        }
        logger.error("Begin to save node info, object is [" + JSONObject.toJSONString(confStepInfo) + "]!");
        
        // 保存数据
        int result = confStepInfoMapper.insertSelective(confStepInfo);
        if (result != 1)
        {
            logger.error("Save node failly, because result doesn`t equal one");
            return ErrorUtil.errorResp(ErrorCode.code_0002);
        }
        Map<String, Object> body = new HashMap<>();
        body.put("nodeId", confStepInfo.getNodeId());
        return ErrorUtil.successResp(body);
    }
	
	/**
	 * 阶段查询
	 * @param data
	 * @return
	 */
	public Map<String, ? extends Object> queryStep(Map<String, ? extends Object> data) {
		String nodeName = ToolsUtil.obj2Str(data.get("nodeName")); // 节点名称
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId")); // 节点编号
		String stepId = ToolsUtil.obj2Str(data.get("stepId")); // 阶段编号
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10); // 分页大小
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);// 当前页数
		int startNum = (pageNum - 1) * pageSize;
		List<ConfStepInfo> list = confStepInfoMapper.queryStep(nodeId, nodeName, stepId, startNum, pageSize);
		int totalNum = confStepInfoMapper.queryCount(nodeId, nodeName, stepId);
		Map<String, Object> body = new HashMap<>();
		body.put("totalNum", totalNum);
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}
	
	 /**
     * 产品或业务类型绑定阶段流程
     * @param data
     * @return
     */
	@Transactional
    public Map<String, ? extends Object> addFlowByProduct(Map<String, ? extends Object> data)
    {
        // 参数拼装
		ConfProductStep confProductStep = new ConfProductStep();
		confProductStep.setProductName((String)data.get("productName")); // 产品名称
		confProductStep.setProductId((Integer)data.get("productId")); // 产品编号
		confProductStep.setBusinessType((String)data.get("businessType")); // 业务类型
		confProductStep.setStepId((Integer)data.get("stepId")); // 阶段编号
		confProductStep.setFlowId((Integer)data.get("flowId")); // 流程编号
		confProductStep.setTeller((String)data.get("teller")); // 操作柜员
		confProductStep.setOrg((String)data.get("org")); // 操作机构
        
        // 保存数据
        int result = confProductStepMapper.insertSelective(confProductStep);
        if (result != 1)
        {
            logger.error("Save node failly, because result doesn`t equal one");
            return ErrorUtil.errorResp(ErrorCode.code_0002);
        }
        Map<String, Object> body = new HashMap<>();
        body.put("productId", confProductStep.getProductId());
        return ErrorUtil.successResp(body);
    }
	
	/**
	 * 查询阶段列表
	 * @param data
	 * @return
	 */
	public Map<String, ? extends Object> queryStepList(Map<String, ? extends Object> data) {
		String productId = ToolsUtil.obj2Str(data.get("productId")); // 节点名称
		String nodeId = ToolsUtil.obj2Str(data.get("nodeId")); // 节点编号
		List<ConfStepInfo> list = confStepInfoMapper.queryStepList(productId, nodeId);
		Map<String, Object> body = new HashMap<>();
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}
	
	/**
	 * 节点查配置查询
	 * @param data
	 * @return
	 */
	public Map<String, ? extends Object> queryNodeConfList(Map<String, ? extends Object> data) {
		
		String productName = ToolsUtil.obj2Str(data.get("productName")); // 节点名称
		Integer productId = ToolsUtil.obj2Int(data.get("productId"), null); // 节点编号
		Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null); // 节点编号
		String useable = ToolsUtil.obj2Str(data.get("useable")); // 节点编号
		List<ConfProductAndStepAndFLow> list = confStepInfoMapper.queryNodeConfList(productName, productId, stepId, useable);
		Map<String, Object> body = new HashMap<>();
		body.put("list", list);
		return ErrorUtil.successResp(body);
	}
}
