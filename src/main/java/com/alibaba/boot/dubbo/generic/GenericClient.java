package com.alibaba.boot.dubbo.generic;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Map;

/**
 * Created by wujianjiang on 2017-12-20.
 */
public class GenericClient {

    private GenericService genericService;

    public GenericClient(GenericService genericService) {
        this.genericService = genericService;
    }

    public Object invoke(String methodName, Map<String, Object> params) {
        return genericService.$invoke(methodName, new String[]{Map.class.getTypeName()}, new Object[]{params});
    }

    public Object invoke(String methodName, Object[] args) throws IOException {
        String[] params = null;
        if (null != args && args.length > 0) {
            params = new String[]{JSON.json(args)};
        }
        return genericService.$invoke(methodName, new String[]{Array.class.getTypeName()}, params);
    }
}
