package com.conf.template.db.mapper;

import com.conf.template.db.model.ConfFlowInfo;

public interface ConfFlowInfoMapper {
    int deleteByPrimaryKey(Integer flowId);

    int insert(ConfFlowInfo record);

    int insertSelective(ConfFlowInfo record);

    ConfFlowInfo selectByPrimaryKey(Integer flowId);

    ConfFlowInfo selectBySelective(ConfFlowInfo record);
    
    int updateByPrimaryKeySelective(ConfFlowInfo record);

    int updateByPrimaryKey(ConfFlowInfo record);
}