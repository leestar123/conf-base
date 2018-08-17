package com.conf.template.db.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.conf.template.db.model.QualificationReviewInfo;

public interface QualificationReviewInfoMapper {
    int deleteByPrimaryKey(Integer qualificationReviewId);

    int insert(QualificationReviewInfo record);

    int insertSelective(QualificationReviewInfo record);

    QualificationReviewInfo selectByPrimaryKey(Integer qualificationReviewId);

    int updateByPrimaryKeySelective(QualificationReviewInfo record);

    int updateByPrimaryKey(QualificationReviewInfo record);
    
    /**
     * 查询列表
     * @param custNo
     * @param custName
     * @param advocateManagePerson
     * @param tellerOrg
     * @param startDate
     * @param endDate
     * @param startNum
     * @param pageSize
     * @return
     */
    List<QualificationReviewInfo> queryQualificationReviewInfoList(@Param("custNo") String custNo,
			@Param("custName") String custName,
			@Param("advocateManagePerson") String advocateManagePerson,
			@Param("tellerOrg") String tellerOrg,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("startNum") Integer startNum,
			@Param("pageSize") Integer pageSize);
    
    /**
     * 查询总记录数
     * @param custNo
     * @param custName
     * @param advocateManagePerson
     * @param tellerOrg
     * @param startDate
     * @param endDate
     * @param startNum
     * @param pageSize
     * @return
     */
    Integer queryCount(@Param("custNo") String custNo,
			@Param("custName") String custName,
			@Param("advocateManagePerson") String advocateManagePerson,
			@Param("tellerOrg") String tellerOrg,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("startNum") Integer startNum,
			@Param("pageSize") Integer pageSize);
}