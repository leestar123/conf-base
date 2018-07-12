package com.conf.template.db.mapper;

import com.conf.template.db.model.ConfInvokInfo;

public interface ConfInvokInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfInvokInfo record);

    int insertSelective(ConfInvokInfo record);

    ConfInvokInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfInvokInfo record);

    int updateByPrimaryKey(ConfInvokInfo record);
}