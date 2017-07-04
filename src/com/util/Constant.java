package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constant  {
    private static final Logger LOGGER = LoggerFactory.getLogger(Constant.class);

    public static int year = Calendar.getInstance().get(Calendar.YEAR); // 得到年份
    public static String bm = "utf-8";

    public static DecimalFormat df = new DecimalFormat("#,###");

    public static String getYesTerSs() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = ft.format(cal.getTime());
        return yesterday;
    }

    public static String getYesTerDay() {
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = fd.format(cal.getTime());
        return yesterday;
    }
    /**
     * 
     * @return
     */
    public static String getDayByNums(Integer nums) {
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, nums);
        String yesterday = fd.format(cal.getTime());
        return yesterday;
    }

    /**
     * 返回系统当前时间，Format: yyyy-MM-dd
     * @return
     */
    public static String getSysNowDate() {
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        return fd.format(new Date());
    }

    /**
     *  返回系统当前时间，Format: yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ft.format(new Date());
    }

    /**
     * 日期增长
     * @return
     */
    public static String getDateIncrease(String olddate, String type, String interval) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date;
        try {
            date = format.parse(olddate);
            c.setTime(date); // 设置当前日期
            if (type.equals("day")) {
                c.add(Calendar.DATE, Integer.valueOf(interval)); // 日期加1天
            } else if (type.equals("month")) {
                c.add(Calendar.MONTH, Integer.valueOf(interval));
            } else if (type.equals("minute")) {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                c.add(Calendar.MINUTE, Integer.valueOf(interval));
            } else {
                c.add(Calendar.YEAR, Integer.valueOf(interval));
            }

            return format.format(c.getTime()).toString();
        } catch (ParseException e) {

            LOGGER.error(e.getMessage());
            return null;
        }

    }

    public static final String DATA_BASE_NAME = "yxw";

    /** 
     * 取得当月天数 等信息
     * */
    public static String getCurrentMonthLastDay() {
        String date = Constant.getSysNowDate();
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return year + ":" + month + ":" + day + ":" + maxDate;
    }

    /** 
     * 取得所传日期上一月天数 等信息
     * */
    public static String getLastMonthLastDay(String date, String lastornext) {
        if (lastornext.equals("1")) {
            date = Constant.getDateIncrease(date, "month", "-1");
        } else {
            date = Constant.getDateIncrease(date, "month", "1");
        }
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, Integer.valueOf(year));
        a.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return year + ":" + month + ":" + day + ":" + maxDate;
    }

    /** 
     * 取得当月天数 
     * */
    public static Integer getMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /** 
     * 得到指定月的天数 
     * */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到日期相差天数
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 得到日期大小比较
     *   smdate 是否 大于 bdate
     * @return
     * @throws ParseException
     */
    public static boolean daysCompare(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        return time1 - time2 > 0;
    }

    /**
     * 得到 xxxx年xx月xx日 xx:xx:xx 格式数据
     */
    public static String getYesrTosFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return sdf.format(Date.parse(date + " "));

    }

    /**
     * 输出 当前服务器时间
     * @param args
     */
    public static void printDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println(sdf.format(Date.parse(date + " ")));

    }

	/**  
	  * 根据日期字符串判断当月第几周  ，第几天
	  * @param str  
	  * @return  
	  * @throws Exception  
	  */  
	 public static int getWeek(String str) throws Exception{  
	     SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");  
	     Date date =sdf.parse(str);  
	     Calendar calendar = Calendar.getInstance();  
	     calendar.setTime(date);  
	     //第几周  
	     int week = calendar.get(Calendar.WEEK_OF_MONTH);  
	     //第几天，从周日开始  
	     int day = calendar.get(Calendar.DAY_OF_WEEK);  
	     //日期类 以周日为第一天  固 再减一，一周一为第一天
	     if(day==1){
	    	 day=7;
	     }else{
	    	 day=day-1;
	     }
	     return day;  
	 }
	 /**
	  *  解析 文件  返回字符串
	  * @param fname
	  * @return
	  */
		public static String getFile(String fname){
			File file =new File(fname);
		  InputStream ins = null;
		  StringBuilder builder = null;
		   BufferedReader reader;
		try {
			ins= new FileInputStream(file);  
			reader = new BufferedReader(new InputStreamReader(ins, "GBK"));

		   String line;
		   builder = new StringBuilder(150);
		   while ((line = reader.readLine()) != null) {
		    builder.append(line+"\n");
		    /*System.out.println("常客执行 "+builder.toString());*/
		   }
		   reader.close();
		   if (ins != null) {
		    ins.close();
		   }
			return builder.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}


}