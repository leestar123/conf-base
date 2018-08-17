package com.conf.template.process;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.conf.application.client.InvokerESBServer;
import org.conf.application.client.dto.LossWarningReq;
import org.conf.application.client.dto.LossWarningRes;
import org.conf.application.client.dto.ModelSystemReq;
import org.conf.application.client.dto.ModelSystemRes;
import org.conf.application.client.dto.QuotaPriceReq;
import org.conf.application.client.dto.QuotaPriceRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.runtime.event.ProcessAfterCompletedEvent;
import com.bstek.urule.runtime.event.ProcessAfterNodeTriggeredEvent;
import com.bstek.urule.runtime.event.ProcessAfterStartedEvent;
import com.bstek.urule.runtime.event.ProcessBeforeCompletedEvent;
import com.bstek.urule.runtime.event.ProcessBeforeNodeTriggeredEvent;
import com.bstek.urule.runtime.event.ProcessBeforeStartedEvent;
import com.conf.common.BeanUtils;
import com.conf.common.Constants;
import com.conf.common.ToolsUtil;
import com.conf.template.db.mapper.QualificationReviewInfoMapper;
import com.conf.template.db.model.QualificationReviewInfo;

/**
 * 资质审查前后处理
 * 
 * @author li_mingxing
 *
 */
public class QualificationInvokerAopProcess implements AbstractInvokerAopProcess{

	private final static Logger logger = LoggerFactory.getLogger(QualificationInvokerAopProcess.class);
	
	private static ThreadLocal<QualificationReviewInfo> thread = new ThreadLocal<>();
	
	@Autowired
	private QualificationReviewInfoMapper reviewInfoMapper;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void beforeProcess(Map<String, ? extends Object> data) {
		logger.info("Start to excute beforeProcess method!");
		thread.remove();
		QualificationReviewInfo info = BeanUtils.convert(data, QualificationReviewInfo.class);
		String teller = ToolsUtil.obj2Str(data.get("teller"));
		String org = ToolsUtil.obj2Str(data.get("org"));
		String processId = ToolsUtil.obj2Str(data.get("processId"));
		String tranSeqNo = ToolsUtil.obj2Str(data.get("tranSeqNo"));
		String stageType = ToolsUtil.obj2Str(data.get("stageType"));
		String creditProduct = ToolsUtil.obj2Str(data.get("creditProduct"));
		String sourceSystem = ToolsUtil.obj2Str(data.get("sourceSystem"));
		List<Map> array = JSONObject.parseArray(JSONObject.toJSONString(data.get("objList")), Map.class);
		for (Map map : array) {
			if (Constants.QUALITY_INFO_STR.equals(ToolsUtil.obj2Str(map.get("key"))))
			{
				String custName = ToolsUtil.obj2Str(map.get("CLIENT_NAME"));
				String custNo = ToolsUtil.obj2Str(map.get("CLIENT_NO"));
				String telephoneNo = ToolsUtil.obj2Str(map.get("PHONE_NO"));
				String globalId = ToolsUtil.obj2Str(map.get("GLOBAL_ID"));
				String globalType = ToolsUtil.obj2Str(map.get("GLOBAL_TYPE"));
				String custType = ToolsUtil.obj2Str(map.get("CLIENT_TYPE_CODE"));
				info.setCustName(custName);
				info.setCustNo(custNo);
				info.setTelephoneNo(telephoneNo);
				info.setCardNo(globalId);
				info.setCardType(globalType);
				info.setCustType(custType);
			}
		}
		info.setTellerNo(teller);
		info.setTellerOrg(org);
		info.setProcessId(processId);
		info.setTranSeqNo(tranSeqNo);
		info.setStageType(stageType);
		info.setCreditProduct(creditProduct);
		info.setSourceSystem(sourceSystem);
		thread.set(info);
	}

	@Override
	public void afterPorcess(String custNo, String custType, Map<String, ? extends Object> data, Map<String, Object> params) {
		logger.info("Start to excute afterPorcess method!");
  		//如果是预筛选阶段，则需要特殊处理
		if (StringUtils.isNotBlank(custNo) && Constants.CUST_TYPE_LOAN.equals(custType)
				&& Constants.STAGE_TYPE_02.equals(ToolsUtil.obj2Str(data.get("stageType"))))
  		{
  			ModelSystemReq modelSystem = new ModelSystemReq();
  			modelSystem.setCustNo(custNo);
  			ModelSystemRes modelSystemRes =InvokerESBServer.modelSystem(modelSystem);
  			LossWarningReq lossWarn = new LossWarningReq();
  			lossWarn.setCustNo(custNo);
  			LossWarningRes lossWarningRes = InvokerESBServer.lossWarning(lossWarn);
  			QuotaPriceReq quota = new QuotaPriceReq();
  			quota.setCLIENT_NO(custNo);
  			QuotaPriceRes quotaProces = InvokerESBServer.quotaPrice(quota);
  			buildParam(modelSystemRes, lossWarningRes, quotaProces, params);
  		}
		QualificationReviewInfo info = thread.get();
		String failReason = ToolsUtil.obj2Str(data.get("failReason"));
		String failResult = ToolsUtil.obj2Str(data.get("failResult"));
		//调查方式
		String investType = ToolsUtil.obj2Str(data.get("investType"));
		//报表编制
		String reportType = ToolsUtil.obj2Str(data.get("reportType"));
		//流失等级
		String lossLevel = ToolsUtil.obj2Str(data.get("lossLevel"));
		//贷款额度
		String loanAdvice = ToolsUtil.obj2Str(data.get("loanAdvice"));
		//贷款利率
		String loanRate = ToolsUtil.obj2Str(data.get("loanRate"));
		
		info.setQualificationReviewReason(failReason);
		info.setQualificationReviewResult(failResult);
		info.setInvestType(investType);
		info.setReportType(reportType);
		info.setLossLevel(lossLevel);
		info.setLoanQuota(loanAdvice);
		info.setLoanRate(loanRate);
		params.put("failNode", info.getQualificationReviewFailCode());
		reviewInfoMapper.insertSelective(info);
	}
	
    /**
     * 根据模型系统、流失预警、定额定价对象，构建返回参数
     * 
     * @param modelSystemRes
     * @param lossWarningRes
     * @param quotaProces
     * @param params
     */
	private void buildParam(ModelSystemRes modelSystemRes, LossWarningRes lossWarningRes, QuotaPriceRes quotaProces,
			Map<String, Object> params) {
		if (modelSystemRes != null && !modelSystemRes.getStrategyList().isEmpty())
		{
			//调查方式
			params.put("investType", modelSystemRes.getStrategyList().get(0).getInvestType());
			//报表编制
			params.put("reportType", modelSystemRes.getStrategyList().get(0).getReportType());
		}
		if (lossWarningRes != null && !lossWarningRes.getStrategyList().isEmpty())
		{
			//流失等级
			params.put("lossLevel", lossWarningRes.getStrategyList().get(0).getLossLevel());
		}
		if (quotaProces != null )
		{
			//贷款额度
			params.put("loanAdvice", quotaProces.getSYS_ADVICE());
			//贷款利率
			params.put("loanRate", quotaProces.getSYS_RATE());
		}
	}
	
	@Override
	public void afterProcessCompleted(ProcessAfterCompletedEvent event) {
		logger.info("Start to excute afterProcessCompleted method!");
		String lastNode = event.getProcessInstance().getCurrentNode().getName();
		QualificationReviewInfo info = thread.get();
		info.setQualificationReviewFailCode(lastNode);
	}

	@Override
	public void beforeProcessStarted(ProcessBeforeStartedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterProcessStarted(ProcessAfterStartedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeProcessCompleted(ProcessBeforeCompletedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNodeTriggered(ProcessBeforeNodeTriggeredEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNodeTriggered(ProcessAfterNodeTriggeredEvent event) {
		// TODO Auto-generated method stub
		
	}

}
