/**  
* @Title: BaseAction.java
* @Package com.yxw.common
* @Description: TODO(用一句话描述该文件做什么)
* @author A18ccms A18ccms_gmail_com  
* @date 2014-10-22 下午5:14:06
* @version V1.0  
*/ 
package com.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName: BaseAction
 * @Description: TODO(基本操作：getCurrentUser()可以直接获取到当前登录用户的所有信息)
 * 
 * @author zhaoh
 * @date 2014-10-22 下午5:14:06
 *
 */

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAction.class);

	private HttpServletRequest request;
	private HttpServletResponse response;
    private HttpSession session;

	public String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		setSession(request.getSession());
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
//	public String getPageJson(Page page){
//		String ret = "";
//		try{
//			JSONObject jobj = new JSONObject();
//			JSONArray ja = new JSONArray();
//			for(String [] temp : page.getContent()){
//				JSONArray row = new JSONArray();
//				for(int i = 0;i < temp.length;i++){
//					row.put(temp[i]);
//				}
//				ja.put(row);
//			}
//			jobj.put("pageNo", page.getPageNo());
//			jobj.put("pageSize", page.getPageSize());
//			jobj.put("totalCount", page.getRecordCount());
//			jobj.put("pageCount", page.getPageCount());
//			jobj.put("rows", ja);
//			ret = jobj.toString();
//		}
//		catch(Exception e){
//			LOGGER.error(e.getMessage());
//		}
//		return ret;
//	}
	
    public void print(String srt) {
        try {
            this.getResponse().setCharacterEncoding("UTF-8");

            this.getResponse().setContentType("text/html");

            this.getResponse().getWriter().print(srt);
            this.getResponse().getWriter().close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        ;
    }
}
