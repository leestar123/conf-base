package com.conf.template.process;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
    private ConfOperateInfoDto dto = ToolsUtil.operateLocalGet();
    
    @Autowired
    private ConfOperateInfoMapper confOperateInfoMapper;
    
    @Override
    public void beforeProcess(Map<String, ? extends Object> data)
    {
        //设置请求报文及请求时间
        dto.setTeller(ToolsUtil.obj2Str(data.get(Constants.TELLER)));
        dto.setOrg(ToolsUtil.obj2Str(data.get(Constants.ORG)));
        if (StringUtils.isBlank(dto.getOrg())) {
            
        }
        if (StringUtils.isBlank(dto.getTeller())) {
            
        }
        dto.setSerialNo(MDC.get("serialNo") + "");
        dto.setRequest(JSONObject.toJSONString(data));
        dto.setCreateTime(new Date(System.currentTimeMillis()));
    }

    @Override
    public void afterPorcess(Map<String, ? extends Object> data)
    {
        //保存请求消息
        Boolean success = ErrorUtil.isSuccess(data);
        dto.setSuccess(success ? 0 : 1);
        //查询操作不添加记录
        if(dto.getOperateType() != null)
        {
            for (ModuleInfo module : dto.getModule())
            {
                ConfOperateInfo record = JSONObject.parseObject(JSONObject.toJSONString(dto), ConfOperateInfo.class);
                record.setModuleName(module.getModuleName());
                record.setModuleId(module.getModuleId());
                confOperateInfoMapper.insertSelective(record);
            }
        }
    }
    
}
