package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SqlServer数据库连接
 * 
 * @author em.D
 * 
 */
public class SqlServerJdbcUtil {
	public static Log logger = LogFactory.getLog("SqlServerJdbcUtil");

	public static Integer excuteSql(String sql) throws Exception {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		String count = "0";
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		logger.info("开始尝试连接数据库(SqlServer)！");
		// System.out.println("开始尝试连接数据库！");
		String url = "jdbc:sqlserver://172.28.176.44:1433;databaseName=trdatab2g";
		String user = "b2gmon";// 用户名,
		String password = "b2g1009";// 密码
		con = DriverManager.getConnection(url, user, password);// 获取连接
		logger.info("连接成功！");
		pre = con.prepareStatement(sql);// 实例化预编译语句
		// pre.setString(1, "刘显安");// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
		result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
		while (result.next()) {
			// 当结果集不为空时
			count = result.getString(1);
		}
		// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
		// 注意关闭的顺序，最后使用的最先关闭
		if (result != null) {
			result.close();
		}
		if (pre != null) {
			pre.close();
		}
		if (con != null) {
			con.close();
		}
		logger.info("数据库连接已关闭(SqlServer)！");
		return Integer.valueOf(count);
	}

	public static void main(String[] args) {
		try {
			int count = SqlServerJdbcUtil
					.excuteSql("SELECT COUNT(1) FROM TRRORDD WHERE request_dt >'2015-10-07'");
			System.out.println("查询结果：" + count);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
