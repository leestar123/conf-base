package org.conf.application;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

import com.conf.common.ConfContext;

public class FileProcessTask implements Callable<String>{

	private Integer startNum;
	
	private Integer pageCount;
	
	private Integer pageSize;
	
	private Map<String, ? extends Object> fileKey;
	
	FileProcessTask(Integer startNum, Integer pageCount, Integer pageSize, Map<String, ? extends Object> fileKey) {
		this.startNum = startNum;
		this.pageCount = pageCount;
		this.fileKey = fileKey;
	}
	
	@Override
	public String call() throws Exception {
		FileProcess<?,?> process = ConfContext.getApplicationContext().getBean(FileProcess.class);
		try {
			return process.readFile(startNum, pageCount, pageSize, fileKey);
		} catch (IOException e) {
			return null;
		}
	}

}
