package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.model.ConfInvokInfo;
import com.conf.template.db.model.ConfOperateInfo;

public interface ConfOperateInfoMapper
{
    int deleteByPrimaryKey(Integer id);
    
    int insert(ConfOperateInfo record);
    
    int insertSelective(ConfOperateInfo record);
    
    ConfOperateInfo selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(ConfOperateInfo record);
    
    int updateByPrimaryKey(ConfOperateInfo record);
    
    int selectTotalRecord(@Param("operateType") Integer operateType, @Param("operateModule") Integer operateModule);
    
    List<ConfInvokInfo> selectRecordWithPage(@Param("operateType") Integer operateType,
        @Param("operateModule") Integer operateModule, @Param("pageNum") Integer pageNum,
        @Param("pageSize") Integer pageSize);
}