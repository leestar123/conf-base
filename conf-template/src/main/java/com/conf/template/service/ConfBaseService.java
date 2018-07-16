package com.conf.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conf.client.RuleInvokerService;
import com.conf.common.Constants;
import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.dto.ConfOperateInfoDto;
import com.conf.template.db.dto.ConfStepAndFLowInfo;
import com.conf.template.db.mapper.ConfFlowInfoMapper;
import com.conf.template.db.mapper.ConfInvokInfoMapper;
import com.conf.template.db.mapper.ConfNodeInfoMapper;
import com.conf.template.db.mapper.ConfOperateInfoMapper;
import com.conf.template.db.mapper.ConfStepInfoMapper;
import com.conf.template.db.model.ConfFlowInfo;
import com.conf.template.db.model.ConfInvokInfo;
import com.conf.template.db.model.ConfNodeInfo;
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
    
    public void setInvokerService(RuleInvokerService invokerService)
    {
        this.invokerService = invokerService;
    }

    private ConfOperateInfoDto local = ToolsUtil.operateLocalGet();
    
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
        local.setModuleName(flowName);
        local.setOrg(org);
        local.setTeller(teller);
        
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
        local.setModuleId(record.getFlowId());
        
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
}
