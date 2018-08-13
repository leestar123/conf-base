package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.model.ConfFlowInfo;

public interface ConfFlowInfoMapper {
    int deleteByPrimaryKey(Integer flowId);

    int insert(ConfFlowInfo record);

    int insertSelective(ConfFlowInfo record);

    ConfFlowInfo selectByPrimaryKey(Integer flowId);

    ConfFlowInfo selectBySelective(ConfFlowInfo record);
    
    List<ConfFlowInfo> selectByStep(Integer stepId);
    
    List<ConfFlowInfo> selectByPage(@Param("flowId")Integer flowId, @Param("flowName")String flowName, @Param("startNum")Integer startNum, @Param("pageSize")Integer pageSize);
    
    int queryCount(@Param("flowId")Integer flowId, @Param("flowName")String flowName);
    
    int updateByPrimaryKeySelective(ConfFlowInfo record);

    int updateByPrimaryKey(ConfFlowInfo record);
}