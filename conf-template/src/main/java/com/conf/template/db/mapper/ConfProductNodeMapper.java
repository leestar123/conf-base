package com.conf.template.db.mapper;

import com.conf.template.db.model.ConfProductNode;

public interface ConfProductNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfProductNode record);

    int insertSelective(ConfProductNode record);

    ConfProductNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfProductNode record);

    int updateByPrimaryKey(ConfProductNode record);
}