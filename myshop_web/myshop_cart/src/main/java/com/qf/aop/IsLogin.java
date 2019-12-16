package com.qf.aop;

import java.lang.annotation.*;

/**
 * @Auth RuanHao
 * @Time 2019/12/13 17:06
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {

    boolean mustLogin() default false;
}
