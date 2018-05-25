package com.conf.client.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfGroup {

    /**
     *	组名称，必输项
     */
    String groupname();
    
    /**
     * 是否拉取保存本地
     * @return
     */
    boolean localStore();
    
    /**
     * 本地保存刷新时间
     * @return
     */
    int refreshTime();
}
