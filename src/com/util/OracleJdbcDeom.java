package com.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class OracleJdbcDeom {
    /**
     * 一个非常标准的连接Oracle数据库的示例代码
     */
    public static Integer testOracle(String sql) throws Exception {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        String count = "0";

        //com.mysql.jdbc.Driver
        //jdbc:oracle:thin:@192.168.191.2:1521:orcl
        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        //System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.32.60:1521:ftpl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        String user = "ftpl_fpview_user";// 用户名,
        String password = "fpviewuser";// 密码
        con = DriverManager.getConnection(url, user, password);// 获取连接
        //System.out.println("连接成功！");
        pre = con.prepareStatement(sql);// 实例化预编译语句
        // pre.setString(1, "刘显安");// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
        while (result.next()) {
            // 当结果集不为空时
            count = result.getString(1);
        }
        // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
        // 注意关闭的顺序，最后使用的最先关闭
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        // System.out.println("数据库连接已关闭！");
        return Integer.valueOf(count);
    }

    public static String testOracle1(String sql) throws Exception {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        String count = "0";

        //com.mysql.jdbc.Driver
        //jdbc:oracle:thin:@192.168.191.2:1521:orcl
        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        //System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.32.60:1521:ftpl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        String user = "ftpl_fpview_user";// 用户名,
        String password = "fpviewuser";// 密码
        con = DriverManager.getConnection(url, user, password);// 获取连接
        // System.out.println("连接成功！");
        pre = con.prepareStatement(sql);// 实例化预编译语句
        // pre.setString(1, "刘显安");// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
        while (result.next()) {
            // 当结果集不为空时
            count = result.getString(1);

        }
        // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
        // 注意关闭的顺序，最后使用的最先关闭
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        // System.out.println("数据库连接已关闭！");
        return count;
    }

    public static Double testOracle2(String sql) throws Exception {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        Double count = 0.0;

        //com.mysql.jdbc.Driver
        //jdbc:oracle:thin:@192.168.191.2:1521:orcl
        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        // System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.32.60:1521:ftpl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        String user = "ftpl_fpview_user";// 用户名,
        String password = "fpviewuser";// 密码
        con = DriverManager.getConnection(url, user, password);// 获取连接
        //System.out.println("连接成功！");
        pre = con.prepareStatement(sql);// 实例化预编译语句
        // pre.setString(1, "刘显安");// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
        while (result.next()) {
            // 当结果集不为空时
            count = result.getDouble(1);

        }
        // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
        // 注意关闭的顺序，最后使用的最先关闭
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        // System.out.println("数据库连接已关闭！");
        return count;
    }

    public static Integer kamsOracle(String sql) throws Exception {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        String count = "0";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.176.84:1531:KAMS";
        String user = "KAMS";
        String password = "kams_1234";
        con = DriverManager.getConnection(url, user, password);
        //System.out.println("连接成功！");
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        while (result.next()) {
            count = result.getString(1);
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        // System.out.println("数据库连接已关闭！");
        if (count != null && count.length() > 0)

        {
            return Integer.valueOf(count);
        } else {
            return 0;
        }

    }

    public static Integer eastOracle(String sql) throws Exception {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        String count = "0";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.192.155:1531/ECSMART";
        String user = "ecs_report";
        String password = "ecs_report01";
        con = DriverManager.getConnection(url, user, password);
        //System.out.println("连接成功！");
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        while (result.next()) {
            count = result.getString(1);
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        // System.out.println("数据库连接已关闭！");
        return Integer.valueOf(count);
    }

    public static String[] racOracle(String sql) throws Exception {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.35.28:1531/AMS";//  旧 地址 jdbc:oracle:thin:@172.20.32.164:1521/rac  muams_bi  muams_bi123
        String user = "muams_bi";
        String password = "muams_bi123";
        con = DriverManager.getConnection(url, user, password);
        //System.out.println("连接成功！");
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        String[] str = new String[3];
        while (result.next()) {
            str[0] = result.getString(1);
            str[1] = result.getString(2);
            str[2] = result.getString(3);
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        //  System.out.println("数据库连接已关闭！");
        return str;
    }

    //预测准确率
    public static Double yczqlOracle() throws Exception {
        //		String sql="SELECT 1-abs(sum(t.rev_odm)-sum(t.rev_rpt))/sum(t.rev_rpt) "+
        //                   "FROM datareport_if.v_odm_flight_dt_zhunquelv t";
        String sql = " select rsk_rate from DATAREPORT_IF.V_ODM_ZHUNQUELV t";
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        Double count = 0.0;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //  System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.176.109:1531/outbound";
        String user = "datareport_if";
        String password = "datareport_if2016_1a2b";
        con = DriverManager.getConnection(url, user, password);
        // System.out.println("连接成功！");
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        while (result.next()) {
            count = result.getDouble(1);
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        // System.out.println("数据库连接已关闭！");
        // System.out.println("比率"+count);
        return count;
    }

    public static Double dd() throws Exception {
        //		String sql="SELECT 1-abs(sum(t.rev_odm)-sum(t.rev_rpt))/sum(t.rev_rpt) "+
        //                   "FROM datareport_if.v_odm_flight_dt_zhunquelv t";
        String sql = "  select psg_rate from v_pnl_msg_res t where t.flight_dt = trunc(sysdate-2) and t.carrier = 'CEA' ";
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        Double count = 0.0;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.176.109:1531/outbound";
        String user = "datareport_if";
        String password = "datareport_if2016_1a2b";
        con = DriverManager.getConnection(url, user, password);
        //  System.out.println("连接成功！");
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        while (result.next()) {
            count = result.getDouble(1);
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        BigDecimal db = new BigDecimal(count);
        return count;
    }

    public static Double bjgx(String sql) throws Exception {
        //		String sql="SELECT 1-abs(sum(t.rev_odm)-sum(t.rev_rpt))/sum(t.rev_rpt) "+
        //                   "FROM datareport_if.v_odm_flight_dt_zhunquelv t";
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        Double count = 0.0;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.176.109:1531/outbound";
        String user = "datareport_if";
        String password = "datareport_if2016_1a2b";
        con = DriverManager.getConnection(url, user, password);
        //  System.out.println("连接成功！");
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        while (result.next()) {
            count = result.getDouble("MARGIN");
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        BigDecimal db = new BigDecimal(count);
        return count;
    }

    /**
     * 运营品质
     */
    public static List<String[]> yypzOracle(String sql) throws Exception {
        Vector content = new Vector();
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        String count = "0";

        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        // System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.32.59:1531:kamsrpt";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        String user = "DC3";// 用户名,
        String password = "dc3";// 密码
        con = DriverManager.getConnection(url, user, password);// 获取连接
        // System.out.println("连接成功！");
        pre = con.prepareStatement(sql);// 实例化预编译语句
        // pre.setString(1, "刘显安");// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数

        ResultSetMetaData rsMeta = result.getMetaData();
        int columnNum;
        String[] field;
        String fieldValue;
        while (result.next()) {
            // 当结果集不为空时
            //count = result.getString(1);

            columnNum = rsMeta.getColumnCount();
            field = new String[columnNum];
            fieldValue = null;
            for (int i = 1; i <= columnNum; i++) {
                fieldValue = result.getString(i);
                if (fieldValue == null) {
                    fieldValue = "";
                }
                field[i - 1] = fieldValue;
            }
            content.add(field);

        }
        // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
        // 注意关闭的顺序，最后使用的最先关闭
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        // System.out.println("数据库连接已关闭！");
        return content;
    }

    /**
     * 商务中心数据库  座公里收入，客公里收入
     * @return
     * @throws Exception
     */
    public static Double SWZXOracle(String sql) throws Exception {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        Double count = 0.0;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@172.28.176.109:1531/outbound";
        String user = "datareport_if";
        String password = "datareport_if2016_1a2b";
        con = DriverManager.getConnection(url, user, password);
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        while (result.next()) {
            count = result.getDouble(1);
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        return count;
    }

    /**
     * 商务中心数据库  座公里收入，客公里收入 航线
     * @return
     * @throws Exception
     */
    public static List<Object[]> SWZXByHXOracle(String sql) throws Exception {
        List<Object[]> obj = new ArrayList<Object[]>();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@172.28.176.109:1531/outbound";
        String user = "datareport_if";
        String password = "datareport_if2016_1a2b";
        con = DriverManager.getConnection(url, user, password);
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        while (result.next()) {
            obj.add(new Object[] { result.getString(1), result.getString(2), result.getString(3) });
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        return obj;
    }
    
    /**
     * 收入表现（日期，销售收入  座公里收入，客公里收入, 客座率）
     * @return
     * @throws Exception
     */
    public static List<String[]> SRBXByHXOracle(String sql) throws Exception {
        List<String[]> obj = new ArrayList<String[]>();
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet result = null;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@172.28.176.109:1531/outbound";
        String user = "datareport_if";
        String password = "datareport_if2016_1a2b";
        con = DriverManager.getConnection(url, user, password);
        pre = con.prepareStatement(sql);
        result = pre.executeQuery();
        while (result.next()) {
            obj.add(new String[] { result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5) });
        }
        if (result != null)
            result.close();
        if (pre != null)
            pre.close();
        if (con != null)
            con.close();
        return obj;
    }
    /**
     * 利润表现（日期，利润总额， 主营业务收入，主营业务利润,）
     * @return
     * @throws Exception
     */
    public static List<String[]> LRBXByHXOracle(String sql) throws Exception {
    	List<String[]> obj = new ArrayList<String[]>();
    	Connection con = null;
    	PreparedStatement pre = null;
    	ResultSet result = null;
    	Class.forName("oracle.jdbc.driver.OracleDriver");
    	String url = "jdbc:oracle:thin:@172.28.176.109:1531/outbound";
    	String user = "datareport_if";
    	String password = "datareport_if2016_1a2b";
    	con = DriverManager.getConnection(url, user, password);
    	pre = con.prepareStatement(sql);
    	result = pre.executeQuery();
    	while (result.next()) {
    		obj.add(new String[] { result.getString(1), result.getString(2), result.getString(3), result.getString(4) });
    	}
    	if (result != null)
    		result.close();
    	if (pre != null)
    		pre.close();
    	if (con != null)
    		con.close();
    	return obj;
    }

    public static void main(String[] args) throws Exception {
        Connection con = null;// 创建一个数据库连接
        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
        //System.out.println("开始尝试连接数据库！");
        String url = "jdbc:oracle:thin:@172.28.32.59:1531:kamsrpt";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
        String user = "DC3";// 用户名,
        String password = "dc3";// 密码
        con = DriverManager.getConnection(url, user, password);// 获取连接
        System.out.println("连接成功！");
    }

}
