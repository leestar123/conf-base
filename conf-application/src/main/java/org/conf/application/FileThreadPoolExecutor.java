package org.conf.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.conf.application.util.FileUtil;

public class FileThreadPoolExecutor {
	
	private int corePoolSize;
	
	//private int maxPoolSize;
	
	private long keepAliveTime;
	
	private ThreadPoolExecutor executor;
	
	public FileThreadPoolExecutor() {
		executor = new ThreadPoolExecutor(corePoolSize, corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
				new SynchronousQueue<Runnable>()) ;
	}
	
	/**
	 * 执行
	 * 
	 * @param fileKey
	 */
	public void doExecutor(String... fileKey) {
		try {
			String fileName = downloadFile(fileKey);
			Integer totalNum = FileUtil.getFileLineCount(fileName);
			Integer avg = totalNum / corePoolSize;
			if (avg * corePoolSize - totalNum < 0) {
				avg = avg + 1;
			}

			List<Future<String>> list = new ArrayList<>();
			for (int i = 0; i < corePoolSize; i++) {
				int start = i * avg + 1;
				int end = (i + 1) * avg;
				if (start > totalNum) {
					break;
				}
				if (end > totalNum) {
					end = totalNum;
				}
				Future<String> futrue= executor.submit(new FileProcessTask(start, end, fileKey));
				list.add(futrue);
			}
			
			List<String> files = new ArrayList<>();
			for (Future<String> future : list) {
				files.add(future.get());
			}
			combineFile(files);
			
			while (list.size() > 0) {
				if (list.get(0).isDone())
				{
					list.remove(0);
				}
					
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 下载文件
	 * 
	 * @param fileKey 下载文件的关键要素
	 * @return
	 */
	private String downloadFile(String... fileKey) {
		//TODO:获取文件
		return null;
	}
	
	/**
	 * 整合文件
	 * 
	 * @param files
	 */
	private void combineFile(List<String> files) {
		//TODO:整合文件
	}
	
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
}
