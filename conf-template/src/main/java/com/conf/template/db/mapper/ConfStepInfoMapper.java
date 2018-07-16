package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.dto.ConfProductAndStepAndFLow;
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

    List<ConfStepInfo> queryStep(@Param("nodeId") String nodeId,
			@Param("nodeName") String nodeName,
			@Param("stepId") String stepId,
			@Param("startNum") Integer startNum,
			@Param("pageSize") Integer pageSize);
    
    int queryCount(@Param("nodeId") String nodeId, @Param("nodeName") String nodeName, @Param("stepId") String stepId);
    
    List<ConfStepInfo> queryStepList(@Param("productId") String productId, @Param("nodeId") String nodeId);
    
    /**
     * 节点查配置查询
     * @param productName
     * @param productId
     * @param stepId
     * @param useable
     * @return
     */
    List<ConfProductAndStepAndFLow> queryNodeConfList(@Param("productName") String productName,
    		@Param("productId") Integer productId,
    		@Param("stepId") Integer stepId,
    		@Param("useable") String useable);
    
}