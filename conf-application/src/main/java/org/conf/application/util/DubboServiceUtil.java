package org.conf.application.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bosent.baseBean.InputBeanBase.CommonHeader;

public class DubboServiceUtil {

	private static String config_path="/dubbo-esb-consumer.xml";
	
	private static DubboServiceUtil dubboServiceUtil=null;
	
	private static ClassPathXmlApplicationContext appContext=null;
	
	private DubboServiceUtil() {
	}
	
	public static DubboServiceUtil getInstance()
	{
		if(dubboServiceUtil==null)
		{
			dubboServiceUtil=new DubboServiceUtil();
		}
		return dubboServiceUtil;
	}
	
	public static ClassPathXmlApplicationContext getContext()
	{
		if(appContext==null)
		{
			appContext = new ClassPathXmlApplicationContext(new String[]{config_path});
		}
		return appContext;
	}
	
	public static CommonHeader getCommonHeader()
	{
		CommonHeader commonHeader=new CommonHeader();
		return commonHeader;
	}
	
}
