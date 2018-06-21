package com.conf.template.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ScheduleTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	private static boolean process = false;
	
	public void schedule() {
		//TODO: 集群部署只允许master执行
		boolean isMaster = isMaster();
		if (!isMaster) {
			logger.warn("Current machine is not master, this scheduler stops ....");
		}
			
		logger.info("Schedule task begin ....");
		if (process) {
			logger.warn("Schedule is already processing, this scheduler stops ....");
		}
			
		process = true;
		doTask();
		process = false;
		
		logger.info("Schedule task end ....");
	}
	
	/**
	 * 具体任务执行
	 */
	public abstract void doTask();
	
	/**
	 * 判断当前运行JVM是否为主机
	 * 
	 * @return
	 */
	public abstract boolean isMaster();
}
