package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;



public class TestB2C {
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	
	public static void insertDataBase(String date) throws IOException, JSONException {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = ft.format(new Date());
//		DAO dao = BaseBO.dao;
//		JSONObject obj = readJsonFromUrl("http://172.31.68.217:8001/uniorder/ws/stastistics/DB2C/" + date);
//		if(obj.getString("message") != null){
//			throw new JSONException(obj.getString("message"));
//		}
//		JSONObject data = obj.getJSONObject("data");
//		if(!dao.execute("insert into activity_b2c (ID,Y_DATE,SALE,INLAND_ORDER,INTERNATIONAL_ORDER,INLAND_TICKET,INTERNATIONAL_TICKET) values ('" + Util.getUUID() + "','" + time + "','" + data.getInt("totalAmount") + "','" + data.getInt("domesticOrders") + "','" + data.getInt("interOrders") + "','" + data.getInt("domesticIssuedTickets") + "','" + data.getInt("interIssuedTickets") + "')")){
//        	throw new IOException("插入数据库错误");
//        }
	}
	
	public static void start() throws IOException, JSONException{
		SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		insertDataBase(fd.format(new Date()));
	}
}