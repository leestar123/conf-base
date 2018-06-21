package com.conf.template.scan.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.conf.template.scan.ScanMgr;
import com.conf.template.scheduler.ScheduleTask;

/**
 * 定时扫描
 * 
 * @author li_mingxing
 *
 */
@Configuration
@Component
@EnableScheduling
public class ScheduleScanMgrImpl extends ScheduleTask implements ScanMgr{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Override
	public void firstScan() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadableScan() throws Exception {
		// TODO Auto-generated method stub
		logger.debug("reloadScan...");
	}
	
	@Override
	public void doTask() {
		try {
			reloadableScan();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
