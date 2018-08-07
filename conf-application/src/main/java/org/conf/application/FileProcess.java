package org.conf.application;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

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
	public String readFile(Integer startLine, Integer endLine, String... filekey) throws IOException {
		LineNumberReader reader = null;
		String line = null;
		try {
			String newFileName = null;
			File file = readData(filekey);
			reader = new LineNumberReader(new FileReader(file));
			while ((line = reader.readLine()) != null || reader.getLineNumber() >= endLine) {
				Integer lineNum = reader.getLineNumber();
				if (lineNum >= startLine && lineNum < endLine) {
					T t = lineParase(line);
					B b = lineProcess(lineNum, t);
					newFileName = writeData(b);
				}
			}
			return newFileName;
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
	public abstract File readData(String... filekey);
	
	/**
	 * 单行根据执行结果写入结果
	 * 
	 * @param b
	 * @return
	 */
	public abstract String writeData(B b);
	
	/**
	 * 每行记录的转行成对象
	 * 
	 * @param line
	 * @return
	 */
	public abstract T lineParase(String line);
	
	/**
	 * 每行数据的处理
	 * 
	 * @param lineNum
	 * @param t
	 * @return
	 */
	public abstract B lineProcess(Integer lineNum, T t);
	
}
