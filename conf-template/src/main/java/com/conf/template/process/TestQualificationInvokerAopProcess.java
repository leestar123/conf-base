package com.conf.template.process;

import java.util.Map;

import com.bstek.urule.runtime.event.ProcessAfterCompletedEvent;
import com.bstek.urule.runtime.event.ProcessAfterNodeTriggeredEvent;
import com.bstek.urule.runtime.event.ProcessAfterStartedEvent;
import com.bstek.urule.runtime.event.ProcessBeforeCompletedEvent;
import com.bstek.urule.runtime.event.ProcessBeforeNodeTriggeredEvent;
import com.bstek.urule.runtime.event.ProcessBeforeStartedEvent;
import com.conf.common.BeanUtils;
import com.conf.template.db.model.QualificationReviewInfo;

public class TestQualificationInvokerAopProcess implements AbstractInvokerAopProcess {

	private static ThreadLocal<QualificationReviewInfo> thread = new ThreadLocal<>();
	
	@Override
	public void beforeProcess(Map<String, ? extends Object> data) {
		QualificationReviewInfo info = BeanUtils.convert(data, QualificationReviewInfo.class);
		thread.set(info);
	}

	@Override
	public void afterPorcess(Map<String, Object> data) {
		QualificationReviewInfo info = thread.get();
		data.put("failNode", info.getQualificationReviewFailCode());		
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
	public void afterProcessCompleted(ProcessAfterCompletedEvent event) {
		String lastNode = event.getProcessInstance().getCurrentNode().getName();
		QualificationReviewInfo info = thread.get();
		info.setQualificationReviewFailCode(lastNode);
	
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
