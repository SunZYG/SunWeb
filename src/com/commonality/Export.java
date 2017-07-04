package com.commonality;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Export extends HttpServlet {
	public void writeToTxt( String exchangeId, String packageId, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException { 
		   response.setContentType("text/plain");// 一下两行关键的设置
		   response.addHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode("","UTF-8")+".txt");
		     // filename指定默认的名字
		   BufferedOutputStream buff = null; 
		   StringBuffer write = new StringBuffer(); 
		   OutputStream outSTr = null; 
		   try { 
		    outSTr = response.getOutputStream();// 建立 
		    buff = new BufferedOutputStream(outSTr); 
		    for (int i = 0; i < 10; i++) { 
		     write.append(i+1); //序号 
		    } 
		    buff.write(write.toString().getBytes("UTF-8")); 
		    buff.flush(); 
		    buff.close(); 
		   } catch (Exception e) { 
		    e.printStackTrace(); 
		   } finally { 
		    try { 
		     buff.close(); 
		     outSTr.close(); 
		    } catch (Exception e) { 
		     e.printStackTrace(); 
		    } 
		   } 
		  } 
		}
	
	

