package org.conf.application.client;

import java.util.List;

import org.conf.application.client.dto.CommonBorrowerInfoRes;
import org.conf.application.client.dto.GuaranteeInfoReq;
import org.conf.application.client.dto.GuaranteeInfoRes;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.conf.common.HttpUtil;

/**
 * 调用授信系统
 * 
 * @author li_mingxing
 *
 */
@Service
public class InvokerCreditService {

	/**
	 * 查询担保人信息
	 * 
	 * @param applyNo
	 * @return
	 */
	public static List<GuaranteeInfoRes> getGuaranteeInfoList(String applyNo) {
		try {
			GuaranteeInfoReq req = new GuaranteeInfoReq();
			req.setApplyNo(applyNo);
			req.setQuestionType("guaranteeInfo");
			String result = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/getGuaranteeInfoList", JSONObject.toJSONString(req));
			String list = JSONObject.parseObject(result).getString("list");
			return JSONObject.parseArray(list, GuaranteeInfoRes.class);
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
	public static List<CommonBorrowerInfoRes> getCommonBorrowerInfoList(String applyNo) {
		try {
			GuaranteeInfoReq req = new GuaranteeInfoReq();
			req.setApplyNo(applyNo);
			req.setQuestionType("commonLoanInfo");
			String result = HttpUtil.doPost("http://192.168.30.174:12000/mock/5b73c9ad49521a06e27fb16f/getCommonBorrowerInfoList", JSONObject.toJSONString(req));
			String list = JSONObject.parseObject(result).getString("list");
			return JSONObject.parseArray(list, CommonBorrowerInfoRes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
