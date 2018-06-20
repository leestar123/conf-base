package com.conf.template.db.mapper;

import com.conf.template.db.model.ConfNodeTemplate;

public interface ConfNodeTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfNodeTemplate record);

    int insertSelective(ConfNodeTemplate record);

    ConfNodeTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfNodeTemplate record);

    int updateByPrimaryKey(ConfNodeTemplate record);
    
    
}