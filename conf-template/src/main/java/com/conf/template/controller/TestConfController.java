package com.conf.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conf.template.db.jpa.TestConfJPA;
import com.conf.template.db.mapper.TestConfMapper;

@RestController
public class TestConfController {

	@Autowired
	private TestConfMapper testConfMapper;

	@Autowired
	private TestConfJPA testConfJPA;
	
	@RequestMapping(value="test", method=RequestMethod.GET)
	public String getName(@RequestParam Integer id) {
		testConfJPA.findById(id).get().getName();
		return testConfMapper.selectByPrimaryKey(id).getName();
	}
}
