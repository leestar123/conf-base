package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.model.ConfNodeInfoAndProduct;
import com.conf.template.db.model.ConfProductNode;

public interface ConfProductNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfProductNode record);

    int insertSelective(ConfProductNode record);

    ConfProductNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfProductNode record);

    int updateByPrimaryKey(ConfProductNode record);
    
    int deleteByProductAndNodeId(@Param(value = "productId") Integer productId,
    								@Param(value = "nodeId") Integer nodeId);
    
    List<ConfNodeInfoAndProduct> batchQueryNodeByProduct(@Param(value = "pageSize") Integer pageSize,
													@Param(value = "pageNum") Integer pageNum);
    
    int queryProductIdCount(@Param(value = "pageSize") Integer pageSize,
			@Param(value = "pageNum") Integer pageNum);
    
    int updateEffectStatus(@Param(value = "productId") Integer productId,
			@Param(value = "nodeId") Integer nodeId,
			@Param(value = "uid") Integer uid,
			@Param(value = "effect") String effect);
}