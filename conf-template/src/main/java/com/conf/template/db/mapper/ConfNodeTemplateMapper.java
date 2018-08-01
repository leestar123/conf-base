package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.model.ConfNodeTemplate;

public interface ConfNodeTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfNodeTemplate record);

    int insertSelective(ConfNodeTemplate record);

    ConfNodeTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfNodeTemplate record);

    int updateByPrimaryKey(ConfNodeTemplate record);
    
    int deleteByIdAndUid(@Param("nodeId") Integer nodeId,@Param("uid") Integer uid);
    
    int deleteForLogicByIdAndUid(@Param("nodeId") Integer nodeId,@Param("uid") Integer uid);
    
    List<ConfNodeTemplate> confNodeTemplateList(Integer nodeId);
    
    ConfNodeTemplate selectByNodIdAndUid(@Param("nodeId") Integer nodeId,@Param("uid") Integer uid);
}