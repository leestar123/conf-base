package com.conf.template.db.mapper;

import com.conf.template.db.model.ConfProductStep;

public interface ConfProductStepMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfProductStep record);

    int insertSelective(ConfProductStep record);

    ConfProductStep selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfProductStep record);

    int updateByPrimaryKey(ConfProductStep record);
}