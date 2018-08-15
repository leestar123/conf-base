package org.conf.application.client;

import org.conf.application.client.dto.LossWarningReq;
import org.conf.application.client.dto.LossWarningRes;
import org.conf.application.client.dto.ModelSystemReq;
import org.conf.application.client.dto.ModelSystemRes;
import org.conf.application.client.dto.QuotaPriceReq;
import org.conf.application.client.dto.QuotaPriceRes;

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
		return null;
	}
	
	/**
	 * 模型系统
	 * 
	 * @return
	 */
	public static ModelSystemRes modelSystem(ModelSystemReq req) {
		return null;
	}
	
	/**
	 * 定额定价
	 * 
	 * @return
	 */
	public static QuotaPriceRes quotaPrice(QuotaPriceReq req) {
		return null;
	}
}
