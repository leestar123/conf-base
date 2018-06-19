package com.conf.template.db.mapper;

import com.conf.template.db.model.ConfNodeInfo;

public interface ConfNodeInfoMapper {
    int deleteByPrimaryKey(Integer nodeId);

    int insert(ConfNodeInfo record);

    int insertSelective(ConfNodeInfo record);

    ConfNodeInfo selectByPrimaryKey(Integer nodeId);

    int updateByPrimaryKeySelective(ConfNodeInfo record);

    int updateByPrimaryKey(ConfNodeInfo record);
}