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
    
    List<ConfRuleInfo> selectRecordList(@Param("productId") Integer productId ,@Param("nodeId")Integer nodeId, @Param("ruleType")String ruleType, @Param("effect")String effect);
    
    List<ConfRuleInfo> selectRuleList(@Param("nodeId")Integer nodeId, @Param("ruleType")String ruleType);
    
    List<ConfRuleInfo> selectRecordListByPage(@Param("nodeId")Integer nodeId,@Param("startNum")Integer startNum,@Param("pageSize")Integer pageSize);
    
    List<ConfRuleInfo> selectEffectRecordListByPage(@Param("productId")Integer productId, @Param("nodeId")Integer nodeId,@Param("startNum")Integer startNum,@Param("pageSize")Integer pageSize);

    List<ConfRuleInfo> queryRuleListByName(@Param("uid")Integer uid, @Param("ruleName")String ruleName, @Param("ruleType")String ruleType, @Param("startNum")Integer startNum,@Param("pageSize")Integer pageSize);

    int queryCountByNodeId(@Param("nodeId")Integer nodeId);
    
    int queryCountByName(@Param("uid")Integer uid, @Param("ruleName")String ruleName, @Param("ruleType")String ruleType);
    
    ConfRuleInfo selectByName(@Param("ruleName")String ruleName);
    
    List<ConfRuleInfo> selectList(@Param(value = "ruleNames")List<String> ruleNames, @Param(value = "beans")List<String> beans);
    
    List<ConfRuleInfo> selectParamList();
}