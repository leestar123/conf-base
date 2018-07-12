package com.conf.template.db.mapper;

import com.conf.template.db.model.ConfOperateInfo;

public interface ConfOperateInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfOperateInfo record);

    int insertSelective(ConfOperateInfo record);

    ConfOperateInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfOperateInfo record);

    int updateByPrimaryKey(ConfOperateInfo record);
}