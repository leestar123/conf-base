package org.conf.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.conf.common.ConfContext;

/**
 * 数据处理的线程池
 * 
 * @author li_mingxing
 *
 */
public class FileThreadPoolExecutor {
	
	private int corePoolSize;
	
	private long keepAliveTime;
	
	private ThreadPoolExecutor executor;
	
	private FileThreadPoolExecutor() {
	}
	
	public void init() {
		executor = new ThreadPoolExecutor(corePoolSize, corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
				new SynchronousQueue<Runnable>()) ;
	}
	/**
	 * 执行
	 * 
	 * @param fileKey
	 */
	public void doExecutor(Map<String, ? extends Object> fileKey) {
		try {
			FileProcess<?,?> process = ConfContext.getApplicationContext().getBean(FileProcess.class);
			Integer interval = process.getCommitInterval();
			Integer totalNum = process.getTotalNum(fileKey);
			
			Integer pageSize = totalNum / interval;
			if (pageSize * corePoolSize - totalNum < 0) {
				pageSize = pageSize + 1;
			}

			Integer avg = pageSize / corePoolSize;
			if (avg * corePoolSize - pageSize < 0) {
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
				Future<String> futrue= executor.submit(new FileProcessTask(start, (end - start) +1, interval, fileKey));
				list.add(futrue);
			}
			
			List<String> files = new ArrayList<>();
			for (Future<String> future : list) {
				files.add(future.get());
			}
			process.combineData(files);
			
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

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
}
