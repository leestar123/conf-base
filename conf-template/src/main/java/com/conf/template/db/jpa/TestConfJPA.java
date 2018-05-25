package com.conf.template.db.jpa;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.conf.template.db.model.TestConf;

public interface TestConfJPA extends Serializable, JpaRepository<TestConf, Integer>, JpaSpecificationExecutor<TestConf> {
}
