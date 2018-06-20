package com.conf.template.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conf.template.db.jpa.TestConfJPA;
import com.conf.template.db.mapper.TestConfMapper;
import com.conf.template.scan.impl.ScanMgrImpl;
import com.conf.template.service.RuleService;

@RestController
public class TestConfController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Autowired
	private TestConfMapper testConfMapper;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private ScanMgrImpl scanMgrImpl;
	
	@Autowired
	private TestConfJPA testConfJPA;
	
	@RequestMapping(value="test", method=RequestMethod.GET)
	public String getName(@RequestParam String id) {
		logger.info("开始测试方法getName()");
//		testConfJPA.findById(id).get().getName();
//		return testConfMapper.selectByPrimaryKey(id).getName();
//		ScanMgrImpl ScanMgrImpl =new ScanMgrImpl();
		ruleService.doRule(1, 1);
		return "";
	}
}
