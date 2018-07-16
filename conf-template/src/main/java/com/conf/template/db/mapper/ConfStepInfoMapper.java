package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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

    List<ConfStepInfo> queryStepList(@Param("nodeId") String nodeId,
			@Param("nodeName") String nodeName,
			@Param("stepId") String stepId,
			@Param("startNum") Integer startNum,
			@Param("pageSize") Integer pageSize);
    
    int queryCount(@Param("nodeId") String nodeId,
    		@Param("nodeName") String nodeName,
			@Param("stepId") String stepId);
}