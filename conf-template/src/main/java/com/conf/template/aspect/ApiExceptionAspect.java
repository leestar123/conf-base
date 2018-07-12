package com.conf.template.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.conf.common.ErrorCode;
import com.conf.common.ErrorUtil;

@Aspect
@Component
public class ApiExceptionAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Around("@annotation(com.conf.common.annotation.ApiException)")
	public Object apiCheck(ProceedingJoinPoint joinPoint) {
	    logger.info("Enter into aspect....");
		try {
			return joinPoint.proceed();
		} catch (Throwable e) {
			logger.error("Excute method[" + joinPoint.getSignature().getName() + "] exceptionally", e);
			return ErrorUtil.errorResp(ErrorCode.code_9999);
		}
	}
}
