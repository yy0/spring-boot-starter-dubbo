package com.alibaba.boot.dubbo.generic;

import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wujianjiang on 2017-3-24.
 */
public class MethodInfo {
    protected Method method;
    protected Object instance;
    protected String[] paramNames;
    protected Class[] paramTypes;
    protected Annotation[][] argsAnnotations;
    protected Map<String, Validated> paramValidated = new HashMap<>();

    public Method getMethod() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public Annotation[][] getArgsAnnotations() {
        return argsAnnotations;
    }

    public Map<String, Validated> getParamValidated() {
        return paramValidated;
    }
}
