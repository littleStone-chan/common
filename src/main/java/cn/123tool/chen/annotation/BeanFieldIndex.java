package com.chen.tools.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
 
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
 
/**
 * 实体model属性字段的位置顺序 ,从0开始
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface BeanFieldIndex {
    public int index();
}