package com.conf.template.process;

import java.util.Map;

import com.bstek.urule.runtime.event.ProcessAfterCompletedEvent;
import com.bstek.urule.runtime.event.ProcessAfterNodeTriggeredEvent;
import com.bstek.urule.runtime.event.ProcessAfterStartedEvent;
import com.bstek.urule.runtime.event.ProcessBeforeCompletedEvent;
import com.bstek.urule.runtime.event.ProcessBeforeNodeTriggeredEvent;
import com.bstek.urule.runtime.event.ProcessBeforeStartedEvent;

/**
 * 资质审查前后处理
 * 
 * @author li_mingxing
 *
 */
public class QualificationInvokerAopProcess implements AbstractInvokerAopProcess{

	@Override
	public void beforeProcess(Map<String, ? extends Object> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPorcess(Map<String, ? extends Object> data) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		System.out.println("afterProcessCompleted");
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
