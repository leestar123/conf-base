package com.conf.template.rule;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.conf.template.common.annotation.Rule;

@Service
public class ExamineAptitude {

	@Rule(name = "反欺诈查询", remark = "调用外部反欺诈查询系统", version = "1.1")
	public void testMehod(Map<String,Object> str)
	{
		
	}
}
