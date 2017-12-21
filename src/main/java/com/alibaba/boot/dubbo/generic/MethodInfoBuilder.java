package com.alibaba.boot.dubbo.generic;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by wujianjiang on 2017-3-24.
 */
public class MethodInfoBuilder {

    private Method method;
    private Object instance;

    public static MethodInfoBuilder create() {
        return new MethodInfoBuilder();
    }

    public MethodInfo build() {
        MethodInfo info = new MethodInfo();
        if (null == method) {
            throw new NullPointerException("method could not be null, please set it");
        }
        if (null == instance) {
            throw new NullPointerException("instance could not be null, please set it");
        }
        info.method = method;
        info.instance = instance;
        ReflectionUtils.makeAccessible(method);
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        Class[] parameterTypes = method.getParameterTypes();
        info.paramTypes = parameterTypes;

        String[] parameterNames = null;
        if (null != parameterTypes && parameterTypes.length > 0) {
            parameterNames = discoverer.getParameterNames(method);
        }
        info.paramNames = parameterNames;

        Annotation[][] annotations = method.getParameterAnnotations();
        info.argsAnnotations = annotations;

        for (int i = 0; i < annotations.length; i++) {
            int length = annotations[i].length;
            if (0 == length) { // 无注解
                continue;
            }
            for (int k = 0; k < length; k++) {
                Annotation anno = annotations[i][k];
                if (null != anno && anno.annotationType().getName().equals(Validated.class.getName())) {
                    Validated validated = (Validated) anno;
                    info.paramValidated.put(parameterNames[i], validated);
                }
            }
        }
        return info;
    }

    public MethodInfoBuilder setMethod(Method method) {
        this.method = method;
        return this;
    }

    public MethodInfoBuilder setInstance(Object instance) {
        this.instance = instance;
        return this;
    }
}
