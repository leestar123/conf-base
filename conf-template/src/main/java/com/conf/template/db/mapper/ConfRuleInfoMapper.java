package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.model.ConfRuleInfo;

public interface ConfRuleInfoMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(ConfRuleInfo record);

    int insertSelective(ConfRuleInfo record);

    ConfRuleInfo selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(ConfRuleInfo record);

    int updateByPrimaryKey(ConfRuleInfo record);
    
    List<ConfRuleInfo> selectRecordList(@Param("productId") Integer productId ,@Param("nodeId")Integer nodeId);
    
    List<ConfRuleInfo> selectRecordListByPage(@Param("nodeId")Integer nodeId,@Param("startNum")Integer startNum,@Param("endNum")Integer endNum);

    List<ConfRuleInfo> queryRuleListByName(@Param("ruleName")String ruleName,@Param("startNum")Integer startNum,@Param("endNum")Integer endNum);
}