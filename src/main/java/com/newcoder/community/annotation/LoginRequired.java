package com.newcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: LoginRequired
 * Package: com.newcoder.community.annotation
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/20 18:29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {



}
