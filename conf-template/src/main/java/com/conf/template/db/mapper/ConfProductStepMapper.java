package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.model.ConfProductStep;
import com.conf.template.db.model.ConfStepInfo;

public interface ConfProductStepMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfProductStep record);

    int insertSelective(ConfProductStep record);

    ConfProductStep selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfProductStep record);

    int updateByPrimaryKey(ConfProductStep record);
    
    List<ConfStepInfo> queryStep(@Param("nodeId") String nodeId,
			@Param("nodeName") String nodeName,
			@Param("stepId") String stepId,
			@Param("startNum") Integer startNum,
			@Param("pageSize") Integer pageSize);
    
    int queryCount(@Param("nodeId") String nodeId, @Param("nodeName") String nodeName, @Param("stepId") String stepId);
    
    List<ConfStepInfo> queryStepList(@Param("productId") String productId, @Param("nodeId") String nodeId);
}