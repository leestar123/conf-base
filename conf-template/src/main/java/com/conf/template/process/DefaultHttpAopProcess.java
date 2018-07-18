package com.conf.template.process;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.conf.client.process.HttpAopProcess;
import com.conf.common.Constants;
import com.conf.common.ErrorUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.dto.ConfOperateInfoDto;
import com.conf.common.dto.ModuleInfo;
import com.conf.template.db.mapper.ConfOperateInfoMapper;
import com.conf.template.db.model.ConfOperateInfo;

public class DefaultHttpAopProcess implements HttpAopProcess
{
    private final static Logger logger = LoggerFactory.getLogger(DefaultHttpAopProcess.class);
    
    @Autowired
    private ConfOperateInfoMapper confOperateInfoMapper;
    
    @Override
    public void beforeProcess(Map<String, ? extends Object> data)
    {
        ConfOperateInfoDto dto = ToolsUtil.operateLocalGet();
        //设置请求报文及请求时间
        dto.setTeller(ToolsUtil.obj2Str(data.get(Constants.TELLER)));
        dto.setOrg(ToolsUtil.obj2Str(data.get(Constants.ORG)));
        if (StringUtils.isBlank(dto.getOrg()))
        {
            
        }
        if (StringUtils.isBlank(dto.getTeller()))
        {
            
        }
        dto.setSerialNo(MDC.get("serialNo") + "");
        dto.setRequest(JSONObject.toJSONString(data));
        dto.setCreateTime(new Date(System.currentTimeMillis()));
    }
    
    @Override
    public void afterPorcess(Map<String, ? extends Object> data)
    {
        ConfOperateInfoDto dto = ToolsUtil.operateLocalGet();
        //保存请求消息
        Boolean success = ErrorUtil.isSuccess(data);
        dto.setSuccess(success ? Constants.EXCUTE_STATUS_SUCCESS : Constants.EXCUTE_STATUS_FAIL);
        //查询操作不添加记录
        if (dto.getOperateType() != null)
        {
            for (ModuleInfo module : dto.getModule())
            {
                try
                {
                    ConfOperateInfo record =
                        JSONObject.parseObject(JSONObject.toJSONString(dto), ConfOperateInfo.class);
                    record.setModuleName(module.getModuleName());
                    record.setModuleId(module.getModuleId());
                    confOperateInfoMapper.insertSelective(record);
                }
                catch (Exception e)
                {
                    logger.warn("操作日志表插入记录失败！", e);
                }
            }
        }
    }
    
}
