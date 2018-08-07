package org.conf.application.util;

import java.io.FileReader;
import java.io.LineNumberReader;

public class FileUtil {

	/**
	 * 获取文件总行数
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static int getFileLineCount(String filename) throws Exception{
		int cnt = 0;
		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new FileReader(filename));
			@SuppressWarnings("unused")
			String lineRead = "";
			while ((lineRead = reader.readLine()) != null) {
			}
			cnt = reader.getLineNumber();
		} finally {
			reader.close();
		}
		return cnt;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "C:\\Users\\li_mingxing\\Desktop\\application.properties";
		try {
			System.out.println(FileUtil.getFileLineCount(fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}