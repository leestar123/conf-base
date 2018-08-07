package org.conf.application;

import java.util.ArrayList;
import java.util.List;
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
	
	private ThreadPoolExecutor executor;
	
	private FileProcess<?,?> process;

	
	public FileThreadPoolExecutor(int corePoolSize, long keepAliveTime) {
		this.corePoolSize = corePoolSize;
		executor = new ThreadPoolExecutor(corePoolSize, corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
				new SynchronousQueue<Runnable>()) ;
		process = ConfContext.getApplicationContext().getBean(FileProcess.class);
	}
	
	/**
	 * 执行
	 * 
	 * @param fileKey
	 */
	public void doExecutor(String... fileKey) {
		try {
			Integer totalNum = process.getTotalNum(fileKey);
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
}
