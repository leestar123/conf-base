package com.conf.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil
{
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);  
    
    @SuppressWarnings("unchecked")
    public static Map<String, ? extends Object> getRequestData(HttpServletRequest request)
    {
        //if ("GET".equalsIgnoreCase(request.getMethod()) || "DELETE".equalsIgnoreCase(request.getMethod())) {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> result = new HashMap<>();
        for (String key : map.keySet())
        {
            String value = request.getParameter(key);
            result.put(key, value);
        }
        if (result.entrySet().size() > 0)
            return result;
        
        //} else {
        // 如果已参数方式无法获取数据,则读取流
        StringBuffer data = new StringBuffer();
        BufferedReader reader = null;
        try
        {
            reader = request.getReader();
            String line = null;
            while (null != (line = reader.readLine()))
            {
                data.append(line);
            }
            return JSONObject.parseObject(data.toString(), Map.class);
        }
        catch (Exception e)
        {
            //TODO:异常处理
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                //TODO:异常处理
            }
        }
        // }
        return null;
    }
    
    public static void write(HttpServletResponse response, String responseStr) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter().write(responseStr);
        } catch (IOException e) {
            logger.error("http write error:", e);
        }
    }
}
