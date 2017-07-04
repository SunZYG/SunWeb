package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	/**
	 * 读取相关文件中的value
	 * @param fileName
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String get(String fileName, String key) throws IOException {
		Properties props = new Properties();
		String url = new PropertiesUtil().getClass().getClassLoader().getResource(fileName).toString().substring(6);
		String empUrl = url.replace("%20", " ");
		InputStream in = null;
		in = new BufferedInputStream(new FileInputStream(File.separator + empUrl));
		props.load(in);
		return (String) props.get(key);
	}

	/**
	 * 根据配置文件名获取配置文件,绝对路径不能包含中文名
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Properties getProps(String fileName) throws IOException {
		Properties props = new Properties();
		String url = new PropertiesUtil().getClass().getClassLoader().getResource(fileName).toString().substring(5);
		InputStream in = null;
		in = new BufferedInputStream(new FileInputStream(File.separator + url));
		props.load(in);
		return props;
	}
}
