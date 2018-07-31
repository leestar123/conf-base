package com.conf.template.db.mapper;

import com.conf.template.db.model.QualificationReviewInfo;

public interface QualificationReviewInfoMapper {
    int deleteByPrimaryKey(Integer qualificationReviewId);

    int insert(QualificationReviewInfo record);

    int insertSelective(QualificationReviewInfo record);

    QualificationReviewInfo selectByPrimaryKey(Integer qualificationReviewId);

    int updateByPrimaryKeySelective(QualificationReviewInfo record);

    int updateByPrimaryKey(QualificationReviewInfo record);
}