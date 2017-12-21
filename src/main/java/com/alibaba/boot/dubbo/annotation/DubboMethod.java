package com.alibaba.boot.dubbo.annotation;

import java.lang.annotation.*;

/**
 * dubbo consumer
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DubboMethod {
  String value();
}
