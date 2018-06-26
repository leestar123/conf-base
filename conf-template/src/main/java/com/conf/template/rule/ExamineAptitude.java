package com.conf.template.rule;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.conf.template.common.annotation.Rule;

@Service
public class ExamineAptitude {

	@Rule(name = "反欺诈查询", remark = "有反欺诈记录,则资质审查不通过", version = "1.1")
	public void testMehod(Map str)
	{
		
	}
	
	@Rule(name = "身份审查校验", remark = "身份证检查不通过,则资质审查不通过", version = "1.1")
	public void testMehod1(Map str)
	{
		
	}
	
	@Rule(name = "名单制查询", remark = "名单制查询,黑名单客户资质审查不通过", version = "1.1")
	public void testMehod2(Map str)
	{
		
	}
	
	@Rule(name = "客户信息完整性校验", remark = "客户信息完整性校验", version = "1.1")
	public void testMehod3(Map str)
	{
		
	}
	@Rule(name = "申请信息完整性校验", remark = "申请信息完整性校验", version = "1.1")
	public void testMehod4(Map str)
	{
		
	}
	@Rule(name = "客户总额度限制", remark = "客户总额度不超过30万", version = "1.1")
	public void testMehod5(Map str)
	{
		
	}
	@Rule(name = "同客户申请限制", remark = "同客户信用类产品申请不超过3个", version = "1.1")
	public void testMehod6(Map str)
	{
		
	}
	@Rule(name = "客户产品互斥关系校验", remark = "客户产品互斥关系校验", version = "1.1")
	public void testMehod7(Map str)
	{
		
	}
	@Rule(name = "同产品申请限制", remark = "同产品不允许申请多次", version = "1.1")
	public void testMehod8(Map str)
	{
		
	}
}
