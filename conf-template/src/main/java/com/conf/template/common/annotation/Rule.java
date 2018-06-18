package com.conf.template.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义规则
 * 
 * @author lmx
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Rule {

    /**
     *	规则名称
     */
    String name();
    
    /**
     * 规则说明
     * @return
     */
    String remark();
    
    /**
     * 版本号
     * @return
     */
    String version();
}
