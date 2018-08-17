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
	 * @param startNum	开始页
	 * @param pageCount 总共业数
	 * @param pageSize	每页大小
	 * @param filekey	查询关键数据
	 * @return
	 * @throws IOException
	 */
	public String readFile(Integer startNum, Integer pageCount, Integer pageSize, Map<String, ? extends Object> filekey) throws IOException {
		LineNumberReader reader = null;
		String returnStr = null;
		try {
			int count = 0;
			do {
				List<T> list = readData(startNum, pageSize, filekey);
				if (list == null || list.isEmpty())
				{
					return null;
				}
				
				Integer lineNum = (startNum - 1) * pageSize + 1;
				List<B> resultList = new ArrayList<>();
				for (T t : list) {
					lineNum ++;
					B b = lineProcess(lineNum, t, filekey);
					resultList.add(b);
				}
				
				if (resultList.size() > 0) {
					writeData(resultList, filekey);
				}
				count ++;
				startNum ++;
			} while (count <= pageCount);
			
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
	public abstract List<T> readData(Integer pageNum, Integer pageSize, Map<String, ? extends Object> filekey);
	
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
