package com.conf.template.db.mapper;

import org.apache.ibatis.annotations.Param;

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
}