package org.conf.application.client;

import java.util.Map;

import org.conf.application.client.dto.OuterInfoQueryReq;
import org.conf.application.client.dto.OuterInfoQueryRes;
import org.conf.application.client.dto.QuotaPriceReq;
import org.conf.application.client.dto.QuotaPriceRes;
import org.conf.application.util.DubboServiceUtil;
import org.conf.application.util.XmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.bosent.rpcinterface.esb.common.service.CopServices;
import com.bosent.rpcinterface.esb.common.service.CopServicesAddNameListInputBean;
import com.bosent.rpcinterface.esb.common.service.CopServicesAddNameListOutputBean;
import com.bosent.rpcinterface.esb.common.service.CopServicesVerifyElementInputBean;
import com.bosent.rpcinterface.esb.common.service.CopServicesVerifyElementOutputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServices;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCalcConsumeLoanCreditScoreInputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCalcConsumeLoanCreditScoreOutputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCalcCustCreditScoreCardInputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCalcCustCreditScoreCardOutputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCalcCustCreditScoreInputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCalcCustCreditScoreOutputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCreditCardCreditScoreInputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCreditCardCreditScoreOutputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCreditCardPrescreeningScoreInputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesCreditCardPrescreeningScoreOutputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesQueryCreditInfoInputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesQueryCreditInfoOutputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesQueryNameListInputBean;
import com.bosent.rpcinterface.esb.common.service.EsbServicesQueryNameListOutputBean;
import com.conf.common.HttpUtil;

/**
 * 调用ESB服务
 * 
 * @author li_mingxing
 *
 */
public class InvokerESBService {

	private static EsbServices esbService = DubboServiceUtil.getContext().getBean(EsbServices.class);
	
	private static CopServices copServices = DubboServiceUtil.getContext().getBean(CopServices.class);
	
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
	 * 计算客户信用评分卡
	 * 
	 * @param input
	 * @return
	 */
	public static EsbServicesCalcCustCreditScoreCardOutputBean calcCustCreditScoreCard(EsbServicesCalcCustCreditScoreCardInputBean input) {
		return esbService.calcCustCreditScoreCard(input);
	}
	
	/**
	 * 客户信审评分计算
	 * 
	 * @param input
	 * @return
	 */
	public static EsbServicesCalcCustCreditScoreOutputBean calcCustCreditScore(EsbServicesCalcCustCreditScoreInputBean input) {
		return esbService.calcCustCreditScore(input);
	}
	
	/**
	 * 消费贷信审评分计算
	 * 
	 * @param input
	 * @return
	 */
	public static EsbServicesCalcConsumeLoanCreditScoreOutputBean calcConsumeLoanCreditScore(EsbServicesCalcConsumeLoanCreditScoreInputBean input) {
		return esbService.calcConsumeLoanCreditScore(input);
	}
	
	/**
	 * 信用卡信审评分
	 * @param input
	 * @return
	 */
	public static EsbServicesCreditCardCreditScoreOutputBean creditCardCreditScore (EsbServicesCreditCardCreditScoreInputBean input) {
		return esbService.creditCardCreditScore(input);
	}
	
	/**
	 * 信用卡预筛选评分
	 * 
	 * @param input
	 * @return
	 */
	public static EsbServicesCreditCardPrescreeningScoreOutputBean creditCardPrescreeningScore (EsbServicesCreditCardPrescreeningScoreInputBean input) {
		return esbService.creditCardPrescreeningScore(input);
	}
	
	/**
	 * 外部数据查询
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static OuterInfoQueryRes outerInfoQuery (OuterInfoQueryReq req) {
		Map<String, String> outMap =null;
		try {
			String outputxml = HttpUtil.doPost(req.getUrl(), req.getClientNo());
			Map<String,Object> retMap=XmlUtils.getHeaderDoc2MapMini("body", outputxml);
			Map<String,Object> resultMap=(Map<String, Object>) retMap.get("body");
			outMap = (Map<String, String>)resultMap.get("BODY");
			
			OuterInfoQueryRes res = new OuterInfoQueryRes();
			res.setArbLowNum(outMap.get("ARB_LOW_NUM"));
			res.setP2pBlackNum(outMap.get("P2P_BLACK_NUM"));
			res.setBreakInfoNum(outMap.get("BREAK_INFO_NUM"));
			res.setCaseNum(outMap.get("CASE_NUM"));
			res.setCourtNoticeNum(outMap.get("COURT_NOTICE_NUM"));
			res.setExeInfoNum(outMap.get("EXE_INFO_NUM"));
			res.setExposureNum(outMap.get("EXPOSURE_NUM"));
			res.setTrialNoticeNum(outMap.get("TRIAL_NOTICE_NUM"));
			return res;
		} catch (Exception e) {
			System.out.println("xml报文转换失败");
			return null;
		}
	} 
	
	/**
	 * 身份验真
	 * 
	 * @param input
	 * @return
	 */
	public static CopServicesVerifyElementOutputBean verifyElement (CopServicesVerifyElementInputBean input) {
		return copServices.verifyElement(input);
	}
	
	/**
	 * 查询个人征信信息
	 * 
	 * @param input
	 * @return
	 */
	public static EsbServicesQueryCreditInfoOutputBean queryCreditInfo (EsbServicesQueryCreditInfoInputBean input) {
		return esbService.queryCreditInfo(input);
	}
	
	/**
	 * 查询名单制信息
	 * @param input
	 * @return
	 */
	public static EsbServicesQueryNameListOutputBean queryNameList (EsbServicesQueryNameListInputBean input ) {
		return esbService.queryNameList(input);
	}
	
	/**
	 * 名单制登记
	 * 
	 * @param input
	 * @return
	 */
	public static CopServicesAddNameListOutputBean addNameList (CopServicesAddNameListInputBean input) {
		return copServices.addNameList(input);
	}
}
