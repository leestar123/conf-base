package com.conf.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.console.repository.model.FileType;
import com.bstek.urule.model.GeneralEntity;
import com.conf.client.RuleInvokerService;
import com.conf.common.ConfContext;
import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.dto.ConfOperateInfoDto;
import com.conf.common.dto.ModuleInfo;
import com.conf.common.process.InvokerAopProcess;
import com.conf.template.db.dto.ConfProductAndStepAndFLow;
import com.conf.template.db.dto.ConfStepAndFLowInfo;
import com.conf.template.db.dto.ConfStepInfoDto;
import com.conf.template.db.mapper.ConfFlowInfoMapper;
import com.conf.template.db.mapper.ConfInvokInfoMapper;
import com.conf.template.db.mapper.ConfNodeInfoMapper;
import com.conf.template.db.mapper.ConfOperateInfoMapper;
import com.conf.template.db.mapper.ConfProductStepMapper;
import com.conf.template.db.mapper.ConfRuleInfoMapper;
import com.conf.template.db.mapper.ConfStepInfoMapper;
import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfInvokInfo;
import com.conf.template.db.model.ConfNodeInfo;
import com.conf.template.db.model.ConfProductStep;
import com.conf.template.db.model.ConfRuleInfo;
import com.conf.template.db.model.ConfStepInfo;


@Service
public class ConfBaseService
{
    
    private final static Logger logger = LoggerFactory.getLogger(ConfBaseService.class);
    
    private static Map<String, String> clazzMap = new HashMap<>();
    
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
    private ConfRuleInfoMapper confRuleInfoMapper;
    
    @Autowired
    private RuleInvokerService invokerService;
    
    @Autowired
    private ConfProductStepMapper confProductStepMapper;
    
    @Autowired
    private InvokerAopProcess invokerAopProcess;
    
    public void setInvokerService(RuleInvokerService invokerService)
    {
        this.invokerService = invokerService;
    }

    /**
     * 查询录入阶段的详细信息
     * 
     * @param data
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public Map<String, ? extends Object> queryFlowDetail(Map<String, ? extends Object> data)
    {
        String flowPath = ToolsUtil.obj2Str(data.get("flowPath"));
        List<String> nameList = new ArrayList<>();
        List<String> beanList = new ArrayList<>();
        
        Document document = null;
        try {
            document = invokerService.getFileSource(flowPath);
        } catch (Exception e) {
            return ErrorUtil.errorResp(ErrorCode.code_9999);
        }
        Element root = document.getRootElement();
        //查询规则
        List<Element> ruleList = root.elements("rule");
        for (Element element : ruleList)
        {
            String file = element.attributeValue("file");
            file = file.substring(file.lastIndexOf("/") + 1, file.length());
            file = file.substring(0, file.indexOf("."));
            nameList.add(file);
        }
        //查询动作
        List<Element> actionList = root.elements("action");
        for (Element element : actionList)
        {
            String bean = element.attributeValue("action-bean");
            beanList.add(bean);
        }
        List<ConfRuleInfo> list = confRuleInfoMapper.selectList(nameList, beanList);
        Map<String, Object> body = new HashMap<>();
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
        
        ConfOperateInfoDto local = ConfContext.operateLocalGet();
        local.setRemark("流程新增");
        ModuleInfo module = new ModuleInfo();
        module.setOperateType(Constants.OPERATE_TYPE_ADD);
        module.setOperateModule(Constants.OPERATE_MODULE_FLOW);
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
        String path = "/" + nodeInfo.getNodeName() + "/"
            + nodeInfo.getNodeName().concat("-").concat(stepInfo.getStepName()).concat("/").concat(flowName).concat(
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
        String url =
            Constants.RULE_URL_BASE + "ruleflowdesigner?file=".concat(path).concat("&nodeId=" + stepInfo.getNodeId());
        Map<String, Object> body = new HashMap<>();
        body.put("url", url);
        return ErrorUtil.successResp(body);
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
        Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
        String businessType = ToolsUtil.obj2Str(data.get("businessType"));
        List<ConfStepAndFLowInfo> queryList = confStepInfoMapper.selectStepAndFlowInfo(nodeId);
        Map<String, Object> body = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        body.put("array", list);
        
        for (ConfStepAndFLowInfo confStepAndFLowInfo : queryList)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("stepId", confStepAndFLowInfo.getStepId());
            map.put("stepName", confStepAndFLowInfo.getStepName());
            List<ConfFlowInfo> flowList = confStepAndFLowInfo.getFlowList();
            if (productId != null || businessType != null) {
	            flowList.stream().forEach(flow ->{
	            	ConfProductStep cps = confProductStepMapper.queryIdByCondition(confStepAndFLowInfo.getStepId(), flow.getFlowId(), productId, businessType);
	            	
	            	//if (confProductStepMapper.queryCountByStep(confStepAndFLowInfo.getStepId(), flow.getFlowId(), productId, businessType) > 0){
	            	if (cps == null) {
	            		// 未绑定
	            		flow.setBind(1);
	            		flow.setId(0); // 默认值
	            	} else if (cps.getDeleteFlag() == 1) {
	            		// 存在数据，但标识是已删除
	            		flow.setBind(1);
	            		flow.setId(cps.getId());
	            	} else if (cps.getDeleteFlag() == 0) {
	            		// 已绑定
	            		flow.setBind(0);
	            		flow.setId(cps.getId());
	            	}
	            });
            }
            map.put("list", flowList);
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
		Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null);
		confStepInfo.setNodeId(nodeId); // 组件编号
		confStepInfo.setStepName((String)data.get("stepName")); // 阶段名称
		confStepInfo.setRemark((String)data.get("remark")); // 组件描述
		confStepInfo.setTeller((String)data.get("teller")); // 操作柜员
		confStepInfo.setOrg((String)data.get("org")); // 操作机构
		
		ConfOperateInfoDto local = ConfContext.operateLocalGet();
        local.setRemark("阶段新增");
        ModuleInfo module = new ModuleInfo();
        module.setOperateType(Constants.OPERATE_TYPE_ADD);
        module.setOperateModule(Constants.OPERATE_MODULE_STEP);
        module.setModuleName(confStepInfo.getStepName());
        List<ModuleInfo> list = new ArrayList<>();
        list.add(module);
        local.setModule(list);
        
		// 调用urule创建工程
        try
        {
            ConfNodeInfo nodeInfo = confNodeInfoMapper.selectByPrimaryKey(nodeId);
            String path = "/" + nodeInfo.getNodeName() + "/" + nodeInfo.getNodeName() + "-" + confStepInfo.getStepName();
            if (invokerService.fileExistCheck(path)) {
                logger.info("Floder[" + path + "]is already existing");
                return ErrorUtil.errorResp(ErrorCode.code_0005, path);
            }
            logger.info("Begin to  create empty floder[" + path + "] on Urule system");
            invokerService.createFlolder(path);
            logger.info("End to  create empty floder!");
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
        module.setModuleId(confStepInfo.getNodeId());
        
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
		List<ConfStepInfoDto> list = confStepInfoMapper.queryStep(nodeId, nodeName, stepId, startNum, pageSize);
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
		Integer productId = ToolsUtil.obj2Int(data.get("productId"), null); // 节点名称
		String businessType = ToolsUtil.obj2Str(data.get("businessType"));
        // 参数拼装
		ConfProductStep confProductStep = new ConfProductStep();
		confProductStep.setProductName((String)data.get("productName")); // 产品名称
		confProductStep.setProductId(productId); // 产品编号
		confProductStep.setBusinessType(businessType); // 业务类型
		confProductStep.setTeller((String)data.get("teller")); // 操作柜员
		confProductStep.setOrg((String)data.get("org")); // 操作机构
		
		ConfOperateInfoDto local = ConfContext.operateLocalGet();
        local.setRemark("产品或业务类型绑定流程");
        List<ModuleInfo> moduleList = new ArrayList<>();
        local.setModule(moduleList);
        
		// 根据产品编号和业务类型查询列表
		List<ConfProductStep> infoList = confProductStepMapper.queryListByProductIdAndBusinessType(productId, businessType);
		
		JSONArray jsonArr = JSONObject.parseArray(JSONObject.toJSONString(data.get("array")));
		//用于返回做自动发布处理
		List<Map<String, Object>> filter = new ArrayList<>();
		for(int i=0; i<jsonArr.size(); i++){
		    Map<String, Object> map = new HashMap<>();
		    
			JSONObject json = jsonArr.getJSONObject(i);
			Integer stepId = ToolsUtil.obj2Int(json.get("stepId"), null); 
			confProductStep.setStepId(stepId);
			
			List<JSONObject> list =  JSONObject.parseArray(JSONObject.toJSONString(json.get("list")), JSONObject.class);
			
			// 需要新增的绑定关系列表
			List<JSONObject> addList = list.stream().filter(obj -> Integer.parseInt(obj.get("bind").toString()) == 0).collect(Collectors.toList());
			map.put("bind", addList);
			// 过滤已存在的数据,然后新增绑定关系
			Map<String, List<JSONObject>> filterMap = filterExistData(infoList, addList);
			if (filterMap.get("trueAddList") != null && !filterMap.get("trueAddList").isEmpty()){
				filterMap.get("trueAddList").stream().forEach(obj -> {
					confProductStep.setFlowId(ToolsUtil.obj2Int( obj.get("flowId"), null));
					// 保存数据
					confProductStepMapper.insertSelective(confProductStep);
					
				    ModuleInfo module = new ModuleInfo();
				    module.setOperateType(Constants.OPERATE_TYPE_ADD);
				    module.setOperateModule(Constants.OPERATE_MODULE_BUND_PRODUCTSTEP);
				    module.setModuleName("");
				    module.setModuleId(ToolsUtil.obj2Int( obj.get("flowId"), null));
				    moduleList.add(module);
				});
			}
			
			// 修改绑定关系
			if (filterMap.get("updateList") != null && !filterMap.get("updateList").isEmpty()){
				ConfProductStep record = new ConfProductStep();
				filterMap.get("updateList").stream().forEach(obj -> {
					Integer id = ToolsUtil.obj2Int(obj.get("id"), null);
					record.setId(id);
					record.setDeleteFlag(0);
					confProductStepMapper.updateByPrimaryKeySelective(record);
					
				    ModuleInfo module = new ModuleInfo();
				    module.setOperateType(Constants.OPERATE_TYPE_MOD);
				    module.setOperateModule(Constants.OPERATE_MODULE_BUND_PRODUCTSTEP);
				    module.setModuleName("");
				    module.setModuleId(ToolsUtil.obj2Int( obj.get("flowId"), null));
				    moduleList.add(module);
				});
			}
			
			// 需要删除的绑定关系列表
			List<JSONObject> deleteList = list.stream().filter(obj -> Integer.parseInt(obj.get("bind").toString()) == 1).collect(Collectors.toList());
			map.put("delete", deleteList);
			ConfProductStep record = new ConfProductStep();
			deleteList.stream().forEach(delObj -> {
				Integer id = ToolsUtil.obj2Int(delObj.get("id"), null);
				if (id != null && id != 0){
					record.setId(id);
					record.setDeleteFlag(1);
					confProductStepMapper.updateByPrimaryKeySelective(record);
					
				    ModuleInfo module = new ModuleInfo();
				    module.setOperateType(Constants.OPERATE_TYPE_DEl);
				    module.setOperateModule(Constants.OPERATE_MODULE_BUND_PRODUCTSTEP);
				    module.setModuleName("");
				    module.setModuleId(ToolsUtil.obj2Int( delObj.get("flowId"), null));
				    moduleList.add(module);
				}
			});
			filter.add(map);
		}
        Map<String, Object> body = new HashMap<>();
        body.put("productId", productId);
        body.put("filter", filter);
        return ErrorUtil.successResp(body);
    }
	
	/**
	 * 过滤已存在的数据
	 * @param infoList
	 * @param list
	 * @return
	 */
	private Map<String, List<JSONObject>> filterExistData(List<ConfProductStep> infoList, List<JSONObject> list) {
		
		HashSet<Integer> idSet =  (HashSet<Integer>) infoList.stream().map(ConfProductStep :: getId).collect(Collectors.toSet());
		
		// 返回的Map数据包括 需要新增的List（数据库里不存在，本次新增）  和 需要修改的List(数据库里存在，但状态是已删除，本次修改状态)
		Map<String, List<JSONObject>> filterMap = new HashMap<>();
		
		// 过滤之后需要新增的List
		List<JSONObject> trueAddList = list.stream().filter(json -> json.get("id") == null || Integer.parseInt(json.get("id").toString()) == 0).collect(Collectors.toList());
		
		// 过滤之后需要更新的List
		List<JSONObject> updateList = list.stream().filter(json -> json.get("id") != null && idSet.contains(Integer.parseInt(json.get("id").toString()))).collect(Collectors.toList());
		filterMap.put("trueAddList", trueAddList);
		filterMap.put("updateList", updateList);
		return filterMap;
	}

	/**
	 * 查询阶段列表
	 * @param data
	 * @return
	 */
	public Map<String, ? extends Object> queryStepList(Map<String, ? extends Object> data) {
		Integer productId = ToolsUtil.obj2Int(data.get("productId"), null); // 节点名称
		Integer nodeId = ToolsUtil.obj2Int(data.get("nodeId"), null); // 节点编号
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
		Integer pageSize = ToolsUtil.obj2Int(data.get("pageSize"), 10); // 分页大小
		Integer pageNum = ToolsUtil.obj2Int(data.get("pageNum"), 1);// 当前页数
		
		List<Map<String,Object>> resultList = new ArrayList<>(); // 返回结果
		List<ConfProductAndStepAndFLow> list = confStepInfoMapper.queryNodeConfList(productName, productId, stepId);
		list.stream().forEach(entity -> {
			List<ConfFlowInfo> flowList = confFlowInfoMapper.selectByStep(entity.getStepId());
			ConfStepInfo confStepInfo = confStepInfoMapper.selectByPrimaryKey(entity.getStepId());
			entity.setConfStepInfo(confStepInfo);
			flowList.stream().forEach( flow -> {
				Map<String, Object> map = new HashMap<>();
				map.put("productId", entity.getProductId());
				map.put("productName", entity.getProductName());
				map.put("stepName", entity.getConfStepInfo().getStepName());
				map.put("flowName", flow.getFlowName());
				map.put("teller", entity.getTeller());
				map.put("org", entity.getOrg());
				map.put("createTime", entity.getCreateTime());
				resultList.add(map);
			});
		});
		Map<String, Object> body = new HashMap<>();
		List<Map<String, Object>> pageData = ToolsUtil.getListPageData(pageNum, pageSize, resultList);
		body.put("list", pageData);
		body.put("totalNum", resultList.size());
		return ErrorUtil.successResp(body);
	}
	
	/**
	 * 查询流程列表
	 * 
	 * @param data
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    public Map<String, ? extends Object> queryFlowList(Map<String, ? extends Object> data)
    {
        Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null); // 节点编号
        ConfStepInfo confStepInfo = confStepInfoMapper.selectByPrimaryKey(stepId);
        List<ConfFlowInfo> list =confFlowInfoMapper.selectByStep(stepId);
        List<JSONObject> jsonList = new ArrayList<>();
        Map<String, Object> body = new HashMap<>();
        for (ConfFlowInfo confFlowInfo : list)
        {
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(confFlowInfo));
            String url =
                Constants.RULE_URL_BASE + "ruleflowdesigner?file=".concat(confFlowInfo.getFlowPath()).concat("&nodeId=" + confStepInfo.getNodeId());
            json.put("key", url);
            jsonList.add(json);
        }
        body.put("list", jsonList);
        return ErrorUtil.successResp(body);
    }
    
    /**
     * 根据flowId查询流程信息
     * 
     * @param flowId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public ConfFlowInfo queryFlowById(Integer flowId)
    {
        return confFlowInfoMapper.selectByPrimaryKey(flowId);
    }
    
    /**
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * @param flowId
     * @param stepId
     * @param productId
     * @param businessType
     * @return
     * @see [类、类#方法、类#成员]
     */
    public ConfProductStep queryProductStep(Integer flowId, Integer stepId, Integer productId, String businessType)
    {
        return confProductStepMapper.queryIdByCondition(stepId, flowId, productId, businessType);
    }
    
    /**
     * 调用知识包
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, ? extends Object> excuteKnowledge(Map<String, ? extends Object> data)
    {
        logger.info("Begin to excute knowledge service!");
        
        Integer productId = ToolsUtil.obj2Int(data.get("productId"), null);
        String businessType = ToolsUtil.obj2Str(data.get("businessType"));
        Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null);
        Integer flowId = ToolsUtil.obj2Int(data.get("flowId"), null);
        String teller = ToolsUtil.obj2Str(data.get("teller"));
        String org = ToolsUtil.obj2Str(data.get("org"));
        ConfProductStep product = confProductStepMapper.queryIdByCondition(stepId, flowId, productId, businessType);
        if (product == null)
            return ErrorUtil.errorResp(ErrorCode.code_0011, flowId);
        
        ConfFlowInfo flowInfo = confFlowInfoMapper.selectByPrimaryKey(flowId);
        if (flowInfo == null) 
        {
            return ErrorUtil.errorResp(ErrorCode.code_0009, flowId);
        }
        ConfNodeInfo nodeInfo = confNodeInfoMapper.queryNodeByStep(flowInfo.getStepId());
        if (nodeInfo == null)
        {
            return ErrorUtil.errorResp(ErrorCode.code_0010, flowInfo.getStepId());
        }
        
        ConfInvokInfo invokInfo = new ConfInvokInfo();
        invokInfo.setRequest(JSONObject.toJSONString(data));
        invokInfo.setService(flowInfo.getFlowName());
        invokInfo.setSuccess(Constants.EXCUTE_STATUS_FAIL);
        invokInfo.setTeller(teller);
        invokInfo.setOrg(org);
        
        try
        {
        	invokerAopProcess.beforeProcess(data);
            List<GeneralEntity> entityList = new ArrayList<>();
            List<Map<String, Object>> objList = new ArrayList<>();
            Object obj = data.get("objList");
            if (obj != null && List.class.isInstance(obj))
            {
                //此处必须为实体对象
                objList = (List<Map<String, Object>>)obj;
                for (Map<String, Object> map : objList)
                {
                    String key = ToolsUtil.obj2Str(map.get("key"));
                    map.remove("key");
                    if (StringUtils.isBlank(clazzMap.get(key))) {
                        buildKnowledgeObject("/" + nodeInfo.getNodeName(), key);
                    }
                    String clazz = clazzMap.get(key);
                    GeneralEntity entity = new GeneralEntity(clazz);
                    for (String set : map.keySet())
                    {
                        entity.put(set, map.get(set));
                    }
                    entityList.add(entity);
                }
            }
            logger.info("Excute knowledge service actually, file is [" + flowInfo.getFlowPath() + "]!");
            Document doc = invokerService.getFileSource(flowInfo.getFlowPath());
            logger.debug("After parase flow document");
            String processId = doc.getRootElement().attributeValue("id");

            logger.debug("Begin to excute Konwledge");
            String files = "jcr:".concat(flowInfo.getFlowPath()).concat(",LATEST");
            Map<String, Object> params = invokerService.executeProcess(files, entityList, processId);
            invokerAopProcess.afterPorcess(params);
            logger.info("End to excute knowledge service");
            invokInfo.setDetail(ConfContext.invokerLocalGet());
            invokInfo.setSuccess(Constants.EXCUTE_STATUS_SUCCESS);
        }
        catch (Exception e)
        {
        	invokInfo.setErrorMessage(e.getMessage());
            logger.error("Excute knowledge [" + flowInfo.getFlowPath() + "] failly!", e);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
        }
        finally
        {
            try
            {
                confInvokInfoMapper.insertSelective(invokInfo);
            }
            catch (Exception e)
            {
                logger.warn("调用日志表插入失败！", e);
            }
        }
        Map<String, Object> body = new HashMap<>();
        return ErrorUtil.successResp(body);
    }
    
    @SuppressWarnings("unchecked")
    private synchronized void buildKnowledgeObject (String path, String key) throws Exception {
        if (StringUtils.isNotBlank(clazzMap.get(key)))
            return;
        
        //TODO:遍历项目下的变量库
        FileType[] types = new FileType[] {FileType.VariableLibrary};
        List<String> fileList = invokerService.getDirectories(path, types, null);
        for (String file : fileList)
        {
            Document doc = invokerService.getFileSource(file);
            List<Element> elements = doc.getRootElement().elements("category");
            for (Element element : elements)
            {
                String clazz = element.attributeValue("clazz");
                String newkey = clazz.substring(clazz.lastIndexOf(".") + 1, clazz.length());
                clazzMap.put(newkey, clazz);
                
            }
        }
    }
    
    /**
     * 刷新知识包
     * @param data
     * @return
     */
    public Map<String, ? extends Object> refreshKnowledgeCacheByStepAndFlow(Map<String, ? extends Object> data)
    {
    	Map<String, Object> body = new HashMap<>();
    	Integer stepId = ToolsUtil.obj2Int(data.get("stepId"), null);
    	Integer flowId = ToolsUtil.obj2Int(data.get("flowId"), null);
    	List<ConfProductStep> productList = confProductStepMapper.queryListByStepAndFlow(stepId, flowId);
    	if (productList == null || productList.isEmpty()) {
    		return ErrorUtil.successResp(body);
    	}
    	String nodeName = confNodeInfoMapper.queryNodeNameByStepId(stepId);
    	ConfFlowInfo confFlowInfo = confFlowInfoMapper.selectByPrimaryKey(flowId);
    	String path = confFlowInfo.getFlowPath();
    	
    	// 判断是否空文件，空文件不做任何处理
    	Document doc;
		try {
			doc = invokerService.getFileSource(path);
			String processId = doc.getRootElement().attributeValue("id");
	    	if (processId != null) {
	    		String files = path.startsWith("/") ? "jcr:" + path : "jcr:/" + path;
		        logger.info("Begin to refresh packages, file path is [" + files + "] !");
		    	productList.stream().forEach(product -> {
					try {
						invokerService.refreshKnowledgeCache(files, product.getProductId().toString(), nodeName);
					} catch (Exception e) {
						logger.error("Publish knowledge [" + path + "] failly!", e);
			            return;
					}
		    	});
	    	}
		} catch (Exception e1) {
			logger.error("Publish knowledge [" + path + "] failly!", e1);
            return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
    	logger.info("End to refresh packages!");
        return ErrorUtil.successResp(body);
    } 
}
