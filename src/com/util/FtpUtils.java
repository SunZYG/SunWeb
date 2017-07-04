package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * @describe 读取FTP上的文件
 * @auto li.wang
 * @date 2013-11-18 下午4:07:34
 */
public class FtpUtils {

	private FTPClient ftpClient;
	@SuppressWarnings("unused")
	private String fileName, strencoding;
	private String ip = "edw-etl.ceair.com"; // 服务器IP地址
	private String userName = "FTP_SALE_CHANNEL"; // 用户名
	private String userPwd = "FTP_SALE_CHANNEL"; // 密码
	private int port = 21; // 端口号
	private String path = "/"; // 读取文件的存放目录

	/**
	 * init ftp servere
	 */
	public FtpUtils() throws Exception {
		this.reSet();
	}

	public void reSet() throws Exception {
		// 以当前系统时间拼接文件名
		fileName = "20131112114850793835861000010161141169.txt";
		strencoding = "UTF-8";
		this.connectServer(ip, port, userName, userPwd, path);
	}

	/**
	 * @param ip
	 * @param port
	 * @param userName
	 * @param userPwd
	 * @param path
	 * @throws SocketException
	 * @throws IOException
	 *             function:连接到服务器
	 */
	public void connectServer(String ip, int port, String userName,
			String userPwd, String path) throws Exception {
		ftpClient = new FTPClient();

		// 连接
		ftpClient.connect(ip, port);
		// 登录
		ftpClient.login(userName, userPwd);
		if (path != null && path.length() > 0) {
			// 跳转到指定目录
			ftpClient.changeWorkingDirectory(path);
		}

	}

	/**
	 * @throws IOException
	 *             function:关闭连接
	 */
	public void closeServer() throws Exception {
		if (ftpClient.isConnected()) {
			ftpClient.logout();
			ftpClient.disconnect();

		}
	}

	/**
	 * @param path
	 * @return function:读取指定目录下的文件名
	 * @throws IOException
	 */
	public List<String> getFileList(String path) throws Exception {
		List<String> fileLists = new ArrayList<String>();
		// 获得指定目录下所有文件名
		FTPFile[] ftpFiles = null;
		// 在linux上每次链接数据前 先打开一个端口 用于传输数据
		ftpClient.enterLocalPassiveMode();
		ftpFiles = ftpClient.listFiles(path);
		for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
			FTPFile file = ftpFiles[i];
			if (file.isFile()) {
				fileLists.add(file.getName());
			}
		}
		return fileLists;
	}

	/**
	 * @param fileName
	 * @return function:从服务器上读取指定的文件
	 * @throws ParseException
	 * @throws IOException
	 */
	public String readFile(String fname) throws Exception {
		InputStream ins = null;
		StringBuilder builder = null;
		// System.out.println("常客执行 进入读取文件信息");
		// 从服务器上读取指定的文件
		ftpClient.enterLocalPassiveMode();
		ins = ftpClient.retrieveFileStream(fname);
		BufferedReader reader = new BufferedReader(new InputStreamReader(ins,
				"GBK"));
		String line;
		builder = new StringBuilder(150);
		while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			builder.append(line);
		}
		reader.close();
		if (ins != null) {
			ins.close();
		}
		// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
		ftpClient.getReply();
		return builder.toString();
	}

	/**
	 * @param fileName
	 *            function:删除文件
	 */
	public void deleteFile(String fileName) throws Exception {
		ftpClient.deleteFile(fileName);

	}

	/**
	 * @param fileName
	 * @return function:从服务器上读取指定的文件
	 * @throws ParseException
	 * @throws IOException
	 */
	public String readFileLine(String fname) throws Exception {
		InputStream ins = null;
		StringBuilder builder = null;
		// 从服务器上读取指定的文件
		// 打开端口
		ftpClient.enterLocalPassiveMode();
		ins = ftpClient.retrieveFileStream(fname);
		if(ins==null){
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(ins,"GBK"));
		String line=null;
		builder = new StringBuilder(150);
		while ((line = reader.readLine()) != null) {
			builder.append(line + "\n");
			/* System.out.println("常客执行 "+builder.toString()); */
		}
		reader.close();
		if (ins != null) {
			ins.close();
		}
		// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
		ftpClient.getReply();

		return builder.toString();
	}

}
