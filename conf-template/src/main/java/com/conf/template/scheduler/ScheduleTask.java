package com.conf.template.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ScheduleTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	private static boolean process = false;
	
	public void schedule() {
		logger.info("Schedule task begin ....");
		if (process) {
			logger.warn("Schedule is already processing, this scheduler stops ....");
		}
			
		process = true;
		doTask();
		process = false;
		
		logger.info("Schedule task end ....");
	}
	
	public abstract void doTask();
}
