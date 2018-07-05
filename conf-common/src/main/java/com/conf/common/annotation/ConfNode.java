package com.conf.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置项目定义组件
 * 
 * @author lmx
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfNode {

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
     * 组件类型
     * @return
     */
    String type();
    
    /**
     * 版本号
     * @return
     */
    String version();
}
