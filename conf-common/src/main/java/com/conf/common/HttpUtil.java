package com.conf.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
	private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * get请求
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String doGet(String url) throws ClientProtocolException, IOException {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
			if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			} else if (response != null) {
				logger.error("Response Code:" + response.getStatusLine().getStatusCode() + "(" + url + ")");
			} else {
				logger.error("Response Object is empty");
			}
			return result;
		} finally {
			httpClient.close();
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * post请求（用于请求json格式的参数）
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, String jsonStr) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(jsonStr, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;

		try {

			response = httpclient.execute(httpPost);
			if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			} else if (response != null) {
				logger.error("Response Code:" + response.getStatusLine().getStatusCode() + "(" + url + ")");
			} else {
				logger.error("Response Object is empty");
			}
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
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
