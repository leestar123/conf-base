package com.conf.template.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.conf.template.common.ErrorCode;
import com.conf.template.common.ErrorUtil;

@Aspect
@Component
public class ApiExceptionAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Around("@annotation(com.conf.template.common.annotation.ApiException)")
	public Object apiCheck(ProceedingJoinPoint joinPoint) {
		try {
			return joinPoint.proceed();
		} catch (Throwable e) {
			logger.error("执行异常方法[" + joinPoint.getSignature().getName() + "],异常信息[" + e.getMessage() + "]");
			return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
	}
}
