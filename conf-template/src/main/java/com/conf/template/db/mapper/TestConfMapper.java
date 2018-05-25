package com.conf.template.db.mapper;

import org.springframework.stereotype.Component;

import com.conf.template.db.model.TestConf;

@Component
public interface TestConfMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(TestConf record);

    int insertSelective(TestConf record);

    TestConf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestConf record);

    int updateByPrimaryKey(TestConf record);
}