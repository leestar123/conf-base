package com.conf.template.process;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bstek.urule.runtime.event.ProcessAfterCompletedEvent;
import com.bstek.urule.runtime.event.ProcessAfterNodeTriggeredEvent;
import com.bstek.urule.runtime.event.ProcessAfterStartedEvent;
import com.bstek.urule.runtime.event.ProcessBeforeCompletedEvent;
import com.bstek.urule.runtime.event.ProcessBeforeNodeTriggeredEvent;
import com.bstek.urule.runtime.event.ProcessBeforeStartedEvent;
import com.conf.common.BeanUtils;
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
	
	@Override
	public void beforeProcess(Map<String, ? extends Object> data) {
		logger.info("Start to excute beforeProcess method!");
		thread.remove();
		QualificationReviewInfo info = BeanUtils.convert(data, QualificationReviewInfo.class);
		String teller = ToolsUtil.obj2Str(data.get("teller"));
		String org = ToolsUtil.obj2Str(data.get("org"));
		info.setTellerNo(teller);
		info.setTellerOrg(org);
		thread.set(info);
	}

	@Override
	public void afterPorcess(Map<String, ? extends Object> data) {
		logger.info("Start to excute afterPorcess method!");
		QualificationReviewInfo info = thread.get();
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
