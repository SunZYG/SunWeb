package com.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.AccessException;



/**
 *
 * @author ZhuZhaoHua
 * 2012 下午8:38:36
 *
 */
public class Post extends Request {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Post.class);
	
	public Post(String uri) {
		super(uri);
		setMessage(new HttpPost(uri));
	}
	
	@Override
	protected void fillParam() throws AccessException {
		//创建表单参数列表   
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();   
		for(String name : getParams().keySet()) {
			String value = getParams().get(name);
			qparams.add(new BasicNameValuePair(name, value));   
		}
		
		//填充表单   
		try {
			if(getHeaders().size()==0){
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(qparams, getDefaultCharset().name());
				((HttpPost)getMessage()).setEntity(entity);
				Header contentType = entity.getContentType();
				getMessage().addHeader(contentType);
			}
//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(qparams, getDefaultCharset().name());
//			((HttpPost)getMessage()).setEntity(entity);
////			entity.setContentType("text/xml;charset=UTF-8");
//			Header contentType = entity.getContentType();
//			getMessage().addHeader(contentType);
			
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
			throw new AccessException("HTTP Get请求异常!填充表单编码异常!", e);
		}
	}

	@Override
	protected void doRequest() throws AccessException {
		try {
			setResponse(getHttpClient().execute((HttpPost)getMessage()));
		} catch (ClientProtocolException e) {
			LOGGER.error(e.getMessage());
			throw new AccessException("HTTP Post请求异常!客户端协议异常!", e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new AccessException("HTTP Post请求异常!IOE!", e);
		}
	}

	@Override
	protected void setEntity() {
		if(!this.getEntityStr().equals("")){
//			try {
//				StringEntity entity = new StringEntity(this.getEntityStr(), "UTF-8");
////				entity.setContentType("text/xml;charset=UTF-8");
//				((HttpPost)this.getMessage()).setEntity(entity);
//				entity = (StringEntity)((HttpPost)this.getMessage()).getEntity();
//			} catch (UnsupportedEncodingException e) {
//				LOGGER.error(e.getMessage());
//			}
		}		
	}
	
	@Override
	protected void getTimeOut() {
		getHttpClient().getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.getTimeOutInt());
		getHttpClient().getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.getTimeOutInt());
	}
	
}
