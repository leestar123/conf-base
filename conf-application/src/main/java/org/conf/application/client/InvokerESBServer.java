package org.conf.application.client;

import org.conf.application.client.dto.LossWarningReq;
import org.conf.application.client.dto.LossWarningRes;
import org.conf.application.client.dto.ModelSystemReq;
import org.conf.application.client.dto.ModelSystemRes;
import org.conf.application.client.dto.QuotaPriceReq;
import org.conf.application.client.dto.QuotaPriceRes;

import com.alibaba.fastjson.JSONObject;
import com.conf.common.HttpUtil;

/**
 * 调用ESB服务
 * 
 * @author li_mingxing
 *
 */
public class InvokerESBServer {

	/**
	 * 流失预警
	 * 
	 * @return
	 */
	public static LossWarningRes lossWarning(LossWarningReq req) {
		try {
			String res = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/lossWarning", JSONObject.toJSONString(req));
			return JSONObject.parseObject(res, LossWarningRes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 模型系统
	 * 
	 * @return
	 */
	public static ModelSystemRes modelSystem(ModelSystemReq req) {
		try {
			String res = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/modelSystem", JSONObject.toJSONString(req));
			return JSONObject.parseObject(res, ModelSystemRes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 定额定价
	 * 
	 * @return
	 */
	public static QuotaPriceRes quotaPrice(QuotaPriceReq req) {
		try {
			String res = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/quotaPrice", JSONObject.toJSONString(req));
			return JSONObject.parseObject(res, QuotaPriceRes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
