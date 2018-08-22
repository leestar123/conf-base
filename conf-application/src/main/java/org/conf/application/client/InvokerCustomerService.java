package org.conf.application.client;

import org.conf.application.client.dto.CustomerByTopicIdReq;
import org.conf.application.client.dto.CustomerByTopicIdRes;
import org.conf.application.client.dto.LossWarningReq;
import org.conf.application.client.dto.LossWarningRes;
import org.conf.application.client.dto.ModelSystemReq;
import org.conf.application.client.dto.ModelSystemRes;
import org.conf.application.client.dto.QualificationAddReq;
import org.conf.application.client.dto.QualificationAddRes;
import org.conf.application.client.dto.QualificationQueryReq;
import org.conf.application.client.dto.QualificationQueryRes;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.conf.common.HttpUtil;

/**
 * 调用客户运营系统
 * 
 * @author li_mingxing
 *
 */
@Service
public class InvokerCustomerService {

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
	 * 获取信息总条数
	 * 
	 * @param marketingCampaigntId
	 * @return
	 */
	public static Integer getCustListNum(String marketingCampaigntId) {
		try {
			CustomerByTopicIdReq req = new CustomerByTopicIdReq();
			req.setMarketingCampaigntId(marketingCampaigntId);
			req.setPageNum(1);
			req.setCountNum(10);
			String result = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/marketingCampaigntId", JSONObject.toJSONString(req));
			CustomerByTopicIdRes res = JSONObject.parseObject(result, CustomerByTopicIdRes.class);
			return res.getTotalNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取信息列表
	 * 
	 * @param marketingCampaigntId
	 * @return
	 */
	public static CustomerByTopicIdRes getCustList(CustomerByTopicIdReq req) {
		try {
			String result = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/marketingCampaigntId", JSONObject.toJSONString(req));
			return JSONObject.parseObject(result, CustomerByTopicIdRes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 名单制查询
	 * 
	 * @param req
	 * @return
	 */
	public static QualificationQueryRes listQualificationQuery(QualificationQueryReq req) {
		return null;
	}


	/**
	 * 名单制登记
	 * 
	 * @param req
	 * @return
	 */
	public static QualificationAddRes listQualificationAdd(QualificationAddReq req) {
		return null;
	}
}
