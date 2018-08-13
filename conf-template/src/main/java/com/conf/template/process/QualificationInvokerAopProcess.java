package com.conf.template.process;

import java.util.List;
import java.util.Map;

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
	public void afterPorcess(Map<String, ? extends Object> data) {
		logger.info("Start to excute afterPorcess method!");
		QualificationReviewInfo info = thread.get();
		String failReason = ToolsUtil.obj2Str(data.get("failReason"));
		String failResult = ToolsUtil.obj2Str(data.get("failResult"));
		info.setQualificationReviewReason(failReason);
		info.setQualificationReviewResult(failResult);
		reviewInfoMapper.insertSelective(info);
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
