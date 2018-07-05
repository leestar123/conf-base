package com.conf.template.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.conf.common.annotation.ConfNode;
import com.conf.common.annotation.Rule;

/**
 * 针对注解进行处理
 * 
 * @author Administrator
 *
 */
@Aspect
public class ConfAspect {

	/**
	 * 组件处理
	 * 
	 * @param pjp
	 * @param confNode
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(ConfNode)")
    public Object decideAccess(ProceedingJoinPoint pjp, ConfNode confNode) throws Throwable {
		return null;
	}
	
	/**
	 * 规则处理
	 * 
	 * @param pjp
	 * @param rule
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(rule)")
    public Object decideAccess(ProceedingJoinPoint pjp, Rule rule) throws Throwable {
		return null;
	}
}
