package com.conf.template.db.mapper;

import com.conf.template.db.model.ConfRuleInfo;

public interface ConfRuleInfoMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(ConfRuleInfo record);

    int insertSelective(ConfRuleInfo record);

    ConfRuleInfo selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(ConfRuleInfo record);

    int updateByPrimaryKey(ConfRuleInfo record);
}