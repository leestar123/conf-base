package org.conf.application.client;

import org.conf.application.client.dto.ApplyUserInfoRes;
import org.conf.application.client.dto.CommonBorrowerInfoRes;
import org.conf.application.client.dto.CustomerByTopicIdReq;
import org.conf.application.client.dto.CustomerByTopicIdRes;
import org.conf.application.client.dto.GuaranteeInfoReq;
import org.conf.application.client.dto.GuaranteeInfoRes;
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
	 * 获取授信申请编号
	 * 
	 * @param applyNo
	 * @return
	 */
	public static ApplyUserInfoRes getApplyUserList(String applyNo) {
		return null;
	}
	
	/**
	 * 查询担保人信息
	 * 
	 * @param applyNo
	 * @return
	 */
	public static GuaranteeInfoRes getGuaranteeInfoList(String applyNo) {
		try {
			GuaranteeInfoReq req = new GuaranteeInfoReq();
			req.setApplyNo(applyNo);
			req.setQuestionType("guaranteeInfo");
			String result = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/marketingCampaigntId", JSONObject.toJSONString(req));
			return JSONObject.parseObject(result, GuaranteeInfoRes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询共同借款人信息
	 * 
	 * @param applyNo
	 * @return
	 */
	public static CommonBorrowerInfoRes getCommonBorrowerInfoList(String applyNo) {
		try {
			GuaranteeInfoReq req = new GuaranteeInfoReq();
			req.setApplyNo(applyNo);
			req.setQuestionType("commonLoanInfo");
			String result = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/marketingCampaigntId", JSONObject.toJSONString(req));
			return JSONObject.parseObject(result, CommonBorrowerInfoRes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
