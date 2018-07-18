package com.conf.template.db.mapper;

import java.util.List;

import com.conf.template.db.model.ConfFlowInfo;

public interface ConfFlowInfoMapper {
    int deleteByPrimaryKey(Integer flowId);

    int insert(ConfFlowInfo record);

    int insertSelective(ConfFlowInfo record);

    ConfFlowInfo selectByPrimaryKey(Integer flowId);

    ConfFlowInfo selectBySelective(ConfFlowInfo record);
    
    List<ConfFlowInfo> selectByStep(Integer stepId);
    
    int updateByPrimaryKeySelective(ConfFlowInfo record);

    int updateByPrimaryKey(ConfFlowInfo record);
}