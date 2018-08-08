package com.conf.client;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.console.servlet.RequestHolder;
import com.conf.common.ConfContext;
import com.conf.common.ErrorUtil;
import com.conf.common.HttpUtil;
import com.conf.common.ToolsUtil;
import com.conf.common.process.HttpAopProcess;

public class ConfBaseServlet extends HttpServlet
{

    private static final long serialVersionUID = -3925944343406551909L;
    
    private Map<String, CommController> handlerMap = new HashMap<String,CommController>();;
    
    private HttpAopProcess process;
    
    private final static Logger logger = LoggerFactory.getLogger(ConfBaseServlet.class);
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext applicationContext=getWebApplicationContext(config);
        ConfContext.setApplicationContext(applicationContext);
        Collection<CommController> handlers=applicationContext.getBeansOfType(CommController.class).values();
        for(CommController handler:handlers){
            String url=handler.url();
            if(handlerMap.containsKey(url)){
                throw new RuntimeException("Handler ["+url+"] already exist.");
            }
            handlerMap.put(url, handler);
        }
        process = applicationContext.getBean(HttpAopProcess.class);
    }
    
    protected WebApplicationContext getWebApplicationContext(ServletConfig config){
        return WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected void service(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException
    {
        MDC.put("serialNo", ToolsUtil.nextSeq() + "");
        RequestHolder.set(req, rep);
        Map<String, ? extends Object> data = HttpUtil.getRequestData(req);
        StringBuffer url = req.getRequestURL();
        logger.info("Request URL[" + url.toString() + "],receive data [" + JSONObject.toJSONString(data) + "]");
        
        int lastIndex = url.toString().lastIndexOf("/");
        String service = url.substring(lastIndex + 1, url.length());
        
        String fullPath = url.toString().substring(0, lastIndex);
        CommController controller = handlerMap.get(fullPath.substring(fullPath.lastIndexOf("/"), fullPath.length()));
        Class<?> clazz = controller.getClass();
        Object result = null;
        try
        {
            process.beforeProcess(data);
            Method method = clazz.getMethod(service, Map.class);
            logger.info("Begin to excute service[" + service + "]");
            result = method.invoke(controller, data);
            logger.info("excute over ,return data [" + JSONObject.toJSONString(result) + "]");
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
            logger.error("Service[" + service + "] excute exceptionally!", e);
            HttpUtil.write(rep, JSONObject.toJSONString(ErrorUtil.errorResp("9999", "系统执行异常")));
        } finally {
            Map<String, ? extends Object> value = result == null ? new HashMap<>() : (Map<String, ? extends Object>)result;
            process.afterPorcess(value);
        }
    }
}
