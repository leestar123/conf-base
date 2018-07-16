package com.conf.template.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.conf.template.db.model.ConfInvokInfo;

public interface ConfInvokInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfInvokInfo record);

    int insertSelective(ConfInvokInfo record);

    ConfInvokInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfInvokInfo record);

    int updateByPrimaryKey(ConfInvokInfo record);
    
    int selectTotalRecord(@Param("service") String service);
    
    List<ConfInvokInfo> selectRecordWithPage(@Param("service") String service, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}