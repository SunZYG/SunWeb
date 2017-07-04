package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.expression.AccessException;

/**
 *
 * @author ZhuZhaoHua
 * 2012 下午8:58:37
 *
 */
public abstract class Request {

    private Logger logger = LogManager.getLogger(getClass());

    /** 是否已经完成请求 */
    private boolean isDone = false;

    private DefaultHttpClient httpClient = new DefaultHttpClient();

    private String uri;

    private HttpResponse response;

    private Charset defaultCharset = Charset.forName("utf-8");

    private List<Cookie> cookies = new ArrayList<Cookie>();

    private List<Header> headers = new ArrayList<Header>();

    private Map<String, String> params = new HashMap<String, String>();

    private HttpMessage message;

    private String proxyIP = ""; // 代理ip
    private String entityStr = ""; // content
    private Integer timeOut = 20000; // 超时时间，默认3秒
    private Integer proxyHost = new Integer(0);

    public Request(String uri) {
        this.uri = uri;
    }

    public void request() throws AccessException {

        if (isDone)
            return;
        // HttpHost proxy = new HttpHost("172.31.1.246", 8080);
        // httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
        // proxy);
        fillParam();
        fillHeader();
        fillCookie();
        setProxy();
        setEntity();
        getTimeOut();
        doRequest();
        isDone = true;
    }

    protected abstract void doRequest() throws AccessException;

    public String getContent() throws AccessException {

        if (!isDone)
            return null;

        HttpEntity entity = response.getEntity();
        String content = null;
        if (entity != null) {
            InputStream is = null;
            try {
                try {
                    is = entity.getContent();
                } catch (IllegalStateException e) {
                    logger.error("HTTP请求异常!状态不合法!", e);
                    throw new AccessException("HTTP请求异常!状态不合法!", e);
                } catch (IOException e) {
                    
                    logger.error("HTTP请求异常!IOE!", e);
                    throw new AccessException("HTTP请求异常!IOE!", e);
                }
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(is, getCharset()));
                } catch (UnsupportedEncodingException e) {
                    
                    logger.error("HTTP请求异常!不支持的编码!", e);
                    throw new AccessException("HTTP请求异常!不支持的编码!", e);
                }
                String line = null;
                StringBuilder sb = new StringBuilder();
                do {
                    try {
                        line = reader.readLine();
                    } catch (IOException e) {
                        
                        logger.error("HTTP请求异常!读取流异常!IOE!", e);
                        continue;
                    }
                    sb.append(null == line ? "" : line);
                    // sb.append("\r\n");
                } while (null != line);
                content = sb.toString();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (IOException e) {
                    
                    logger.error("HTTP请求异常!关闭流异常!IOE!", e);
                }
            }
        }

        return content;
    }

    private String getCharset() {

        if (!isDone)
            return getDefaultCharset().name();

        Header header = response.getEntity().getContentType();

        if (null == header)
            return defaultCharset.name();

        String value = header.getValue();
        int index = value.indexOf("charset=");
        if (-1 == index)
            return defaultCharset.name();
        return value.substring(index + 8);// FIXME 只考虑了"text/html; charset=GBK"
                                          // 这种情况
        // return "text/xml;charset=UTF-8";
    }

    public String getUri() {
        return uri;
    }

    public List<Cookie> getAllStoredCookes() {
        return getHttpClient().getCookieStore().getCookies();
    }

    protected abstract void fillParam() throws AccessException;

    private void fillHeader() {
        if (null == message)
            return;
        for (Header header : getHeaders()) {
            if ("Content-Type".equalsIgnoreCase(header.getName())) {
                StringEntity entity;
                try {
                    entity = new StringEntity(this.getEntityStr(), "UTF-8");
                    entity.setContentType(header.getValue());
                    ((HttpPost) this.getMessage()).setEntity(entity);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } else {
                message.addHeader(header);
            }
        }
    }

    // content
    protected abstract void setEntity();

    // 超时时间
    protected abstract void getTimeOut();

    private void fillCookie() {
        for (Cookie cookie : getCookies()) {
            getHttpClient().getCookieStore().addCookie(cookie);
        }
    }

    private void setProxy() {
        if (!this.proxyIP.equals("") && this.proxyHost.intValue() != 0) {
            HttpHost proxy = new HttpHost(this.proxyIP, this.proxyHost);
            getHttpClient().getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public Charset getDefaultCharset() {
        return defaultCharset;
    }

    public void setDefaultCharset(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void addHeader(Header header) {
        this.headers.add(header);
    }

    public void addHeader(String name, String value) {
        Header header = new BasicHeader(name, value);
        this.headers.add(header);
    }

    public HttpMessage getMessage() {
        return message;
    }

    public void setMessage(HttpMessage message) {
        this.message = message;
    }

    public DefaultHttpClient getHttpClient() {
        return httpClient;
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProxyIP() {
        return proxyIP;
    }

    public void setProxyIP(String proxyIP) {
        this.proxyIP = proxyIP;
    }

    public Integer getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(Integer proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getEntityStr() {
        return entityStr;
    }

    public void setEntityStr(String entityStr) {
        this.entityStr = entityStr;
    }

    public Integer getTimeOutInt() {
        return timeOut;
    }

    public void setTimeOutInt(Integer timeOut) {
        this.timeOut = timeOut;
    }

}
