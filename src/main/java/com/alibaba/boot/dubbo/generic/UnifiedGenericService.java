package com.alibaba.boot.dubbo.generic;

import com.alibaba.boot.dubbo.annotation.DubboMethod;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wujianjiang on 2017-12-20.
 */
public class UnifiedGenericService implements GenericService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnifiedGenericService.class);

    private Object bean;

    private Service service;

    private Map<String, MethodInfo> methodMap = new HashMap();

    public UnifiedGenericService(Object bean, Service service) {
        this.bean = bean;
        this.service = service;
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (method.getDeclaringClass() == Object.class || Modifier.isStatic(method.getModifiers()) ||
                    Modifier.isPrivate(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
                continue;
            }
            String methodName = method.getName();
            DubboMethod dubboMethod = AnnotationUtils.findAnnotation(method, DubboMethod.class);
            if (null != dubboMethod) {
                methodName = dubboMethod.value();
            }
            MethodInfo cacheMethod = methodMap.get(methodName);
            if (cacheMethod != null) {
                LOGGER.error("duplication of registration with method name: {}, service name: {}", methodName, service.interfaceName());
                throw new RuntimeException("duplication of registration with method name: " + methodName + ", service name: " + service.interfaceName());
            }
            cacheMethod = MethodInfoBuilder.create().setInstance(bean).setMethod(method).build();
            methodMap.put(methodName, cacheMethod);
            LOGGER.info("service:{} regist method: {}", service.interfaceName(), methodName);
        }
    }

    @Override
    public Object $invoke(String s, String[] strings, Object[] objects) throws GenericException {
        LOGGER.debug("invoke>>>: service name: {}, method:{}, types: {}, args:{}", service.interfaceName(), s, strings[0], objects);
        Objects.requireNonNull(s, "method name is null");
        MethodInfo methodInfo = methodMap.get(s);
        if (null == objects || objects.length == 0) {
            invokeMethod(methodInfo, null);
        }

        String json = objects[0].toString();
        String type = strings[0];
        Object[] args = null;
        if (Array.class.getTypeName().equals(type)) {
            try {
                args = JSON.parse(json, methodInfo.paramTypes);
            } catch (ParseException e) {
                LOGGER.error("JSON 转换对应参数时发生异常! JSON: {}", json);
                throw new GenericException(e);
            }
        }
        return invokeMethod(methodInfo, args);
    }

    private Object invokeMethod(MethodInfo methodInfo, Object[] args) {
        try {
            return methodInfo.getMethod().invoke(bean, args);
        } catch (IllegalAccessException e) {
            throw new GenericException(e);
        } catch (InvocationTargetException e) {
            throw new GenericException(e);
        }
    }
}
