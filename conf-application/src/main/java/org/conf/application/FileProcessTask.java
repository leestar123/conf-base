package org.conf.application;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.conf.common.ConfContext;

public class FileProcessTask implements Callable<String>{

	private Integer start;
	
	private Integer end;
	
	private String []fileKey;
	
	FileProcessTask(Integer start, Integer end, String... fileKey) {
		this.start = start;
		this.end = end;
		this.fileKey = fileKey;
	}
	
	@Override
	public String call() throws Exception {
		FileProcess<?,?> process = ConfContext.getApplicationContext().getBean(FileProcess.class);
		try {
			return process.readFile(start, end, fileKey);
		} catch (IOException e) {
			return null;
		}
	}

}
