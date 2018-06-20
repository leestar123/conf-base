package com.conf.template.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.model.ConfNodeInfo;

public interface ConfNodeInfoMapper {
    int deleteByPrimaryKey(Integer nodeId);

    int insert(ConfNodeInfo record);

    int insertSelective(ConfNodeInfo record);

    ConfNodeInfo selectByPrimaryKey(Integer nodeId);

    int updateByPrimaryKeySelective(ConfNodeInfo record);

    int updateByPrimaryKey(ConfNodeInfo record);
    
    List<ConfNodeInfo> queryNodeList(@Param("nodeName") String nodeName
    									,@Param("nodeType") String nodeType,
    										@Param("startNum") Integer startNum
    										,@Param("endNum") Integer endNum);
}