package org.conf.application;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FileProcess<T,B> {

	/**
	 * 读文件处理
	 * 
	 * @param startLine
	 * @param endLine
	 * @param filekey
	 * @return
	 * @throws IOException
	 */
	public String readFile(Integer startLine, Integer endLine, Map<String, ? extends Object> filekey) throws IOException {
		LineNumberReader reader = null;
		String returnStr = null;
		try {
			Integer interval = getCommitInterval();
			do {
				if ((startLine + startLine) > endLine) {
					interval = (endLine - startLine);
				}
				List<T> list = readData(startLine, interval, filekey);
				if (list == null || list.isEmpty())
				{
					return null;
				}
				startLine += interval;
				
				Integer lineNum = startLine;
				List<B> resultList = new ArrayList<>();
				int count = 0;
				for (T t : list) {
					lineNum ++;
					count ++;
					B b = lineProcess(lineNum, t, filekey);
					resultList.add(b);
					if (count == getCommitInterval()) {
						returnStr = writeData(resultList, filekey);
						count = 0;
						resultList = new ArrayList<>();
					}
				}
				
				if (resultList.size() > 0) {
					writeData(resultList, filekey);
				}
			} while ((startLine + startLine) < endLine);
			
			return returnStr;
		} finally {
			if (reader != null)
				reader.close();
		}
	}
	
	/**
	 * 根据文件要素读取数据
	 * 
	 * @param filekey
	 * @return
	 */
	public abstract List<T> readData(Integer startLine, Integer endLine, Map<String, ? extends Object> filekey);
	
	/**
	 * 单行根据执行结果写入结果
	 * 
	 * @param b
	 * @return
	 */
	public abstract String writeData(List<B> list, Map<String, ? extends Object> filekey);
	
	/**
	 * 每行数据的处理
	 * 
	 * @param lineNum
	 * @param t
	 * @return
	 */
	public abstract B lineProcess(Integer lineNum, T t, Map<String, ? extends Object> filekey);
	
	/**
	 * 获取数据总条数
	 * 
	 * @param filekey
	 * @return
	 */
	public abstract Integer getTotalNum(Map<String, ? extends Object> filekey);
	
	/**
	 * 整合返回Future中的值
	 * 
	 * @param returnData
	 */
	public abstract void combineData(List<String> returnData);
	
	/**
	 * 整合返回Future中的值
	 * 
	 * @param returnData
	 */
	public abstract Integer getCommitInterval();
	
}
