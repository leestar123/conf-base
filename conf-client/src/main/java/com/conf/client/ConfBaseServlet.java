package com.conf.client;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.console.servlet.RequestHolder;
import com.conf.client.common.ErrorUtil;

public class ConfBaseServlet extends HttpServlet
{

    private static final long serialVersionUID = -3925944343406551909L;
    
    private CommController controller;
    
    private final static Logger logger = LoggerFactory.getLogger(ConfBaseServlet.class);
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext applicationContext=getWebApplicationContext(config);
        controller =applicationContext.getBean(CommController.class);
    }
    
    protected WebApplicationContext getWebApplicationContext(ServletConfig config){
        return WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException
    {
        RequestHolder.set(req, rep);
        Map<String, ? extends Object> data = HttpUtil.getRequestData(req);
        StringBuffer url = req.getRequestURL();
        int lastIndex = url.toString().lastIndexOf("/");
        String service = url.substring(lastIndex + 1, url.length());
        Class<?> clazz = controller.getClass();
        try
        {
            Method method = clazz.getMethod(service, Map.class);
            Object result = method.invoke(controller, data);
            HttpUtil.write(rep, JSONObject.toJSONString(result));
//            Boolean redirect = false;
//            if (Map.class.isInstance(result)) {
//                Map<String, Object> map = (Map<String, Object>)result;
//                if (ErrorUtil.isSuccess(map)) {
//                    Map<String, Object> body = (Map<String, Object>)map.get("body");
//                    String location = body.get("url") == null ? "" : body.get("url").toString();
//                    if (StringUtils.isNotBlank(location)) {
//                        redirect = true;
//                        rep.sendRedirect(location);
//                    }
//                }
//            }
//            if (!redirect)
//                HttpUtil.write(rep, JSONObject.toJSONString(result));
        }
        catch (Exception e)
        { 
            logger.error("服务[" + service + "]执行异常!", e);
            HttpUtil.write(rep, JSONObject.toJSONString(ErrorUtil.errorResp("9999", "系统执行异常")));
        }
    }
}
