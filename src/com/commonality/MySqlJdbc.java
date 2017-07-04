package com.commonality;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySqlJdbc {
	static String username="root";
	static String password="853664930";
	static String urls="127.0.0.1";
	static Integer port=3306;
	static String server_name="sun";
	
    @SuppressWarnings("unused")
	public static void excuteMySql(String sql) throws Throwable {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        Class.forName("com.mysql.jdbc.Driver");// 加载Oracle驱动程序
        String url = "jdbc:mysql://"+urls+":"+port+"/"+server_name+"?user="+username+"&password="+password+"";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        con = DriverManager.getConnection(url);// 获取连接
        pre = con.prepareStatement(sql);// 实例化预编译语句
        pre.execute();// 执行查询，注意括号中不需要再加参数
        // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
        // 注意关闭的顺序，最后使用的最先关闭
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
    }
    
    public static List<String> selectMySql(String sql) throws Throwable {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        Class.forName("com.mysql.jdbc.Driver");// 加载Oracle驱动程序
        String url = "jdbc:mysql://"+urls+":"+port+"/"+server_name+"?user="+username+"&password="+password+"";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        con = DriverManager.getConnection(url);// 获取连接
        pre = con.prepareStatement(sql);// 实例化预编译语句
        result=  pre.executeQuery(sql);// 执行查询，注意括号中不需要再加参数
        List<String> list =new ArrayList<String>();
        while (result.next()) {
        	list.add(result.getString(1));
        }

        // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
        // 注意关闭的顺序，最后使用的最先关闭
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
		return list;

    }
    public static void main(String[] args) {
    	try {
			excuteMySql("select 1 from catalogue");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
