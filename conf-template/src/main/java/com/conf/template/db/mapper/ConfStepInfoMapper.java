package com.conf.template.db.mapper;

import java.util.List;

import com.conf.template.db.dto.ConfStepAndFLowInfo;
import com.conf.template.db.model.ConfStepInfo;

public interface ConfStepInfoMapper {
    int deleteByPrimaryKey(Integer stepId);

    int insert(ConfStepInfo record);

    int insertSelective(ConfStepInfo record);

    ConfStepInfo selectByPrimaryKey(Integer stepId);

    int updateByPrimaryKeySelective(ConfStepInfo record);

    int updateByPrimaryKey(ConfStepInfo record);
    
    List<ConfStepAndFLowInfo> selectStepAndFlowInfo(Integer nodeId);
}