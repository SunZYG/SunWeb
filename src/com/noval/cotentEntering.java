package com.noval;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.commonality.MySqlJdbc;
import com.commonality.Util;
import com.myBatis.bean.Catalogue;
import com.myBatis.model.impl.CatalogBusinessImpl;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * 小说内容爬取
 * @author 85366
 *
 */
public class cotentEntering  extends BreadthCrawler{
	public static Map<String,String > contexts = new ConcurrentHashMap<String, String>();
	public static List<Catalogue> lists =null;
	public cotentEntering(String crawlPath, boolean autoParse) {
		super("crawl", true);
		/* 种子页面 */
		this.addSeed(crawlPath);
		/* 正则规则设置 */
		/* 爬取符合 http://news.hfut.edu.cn/show-xxxxxxhtml的URL */
		this.addRegex(crawlPath);
		/* 不要爬取 jpg|png|gif */
		this.addRegex("-.*\\.(jpg|png|gif).*");
		/* 不要爬取包含 # 的URL */
		this.addRegex("-.*#.*");
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		try {
			if(CatalogBusinessImpl.is_stop==true){
				this.stop();
				System.out.println("**************************线程4结束****************************");
				return;
			}
			//缓存过大 休息一分钟
			if(contexts.size()>2000){
				System.out.println("**************************缓存过大，休息一分钟****************************");
				Thread.sleep(60*1000);
			}
			String url = page.getUrl();
			@SuppressWarnings({ "deprecation", "unused" })
			Document doc = page.getDoc();
			/* extract title and content of news by css selector */
			Elements content = page.select("div[id=htmlContent] ");
			if(lists!=null && lists.size()>0){
				lists.remove(0);
				next.add(lists.get(0).getHref());
			}
			String text=content.first().html().replace("'", "");
			text=text.replace("*", "");
			contexts.put(url, text.substring(text.indexOf("</center>")+9));
			System.out.println("contexts size:"+contexts.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新 数据库中 文本内容为空的小说
	 */
	public static void updateContent(){
		String sql="select href from catalogue where content is  null";
		try {
			List<String> list =MySqlJdbc.selectMySql(sql);
			//http://www.yznn.com/modules/article/reader.php?aid=1&cid=717640
//			List<String> list =new ArrayList<String>();
//			list.add("http://www.yznn.com/modules/article/reader.php?aid=1&cid=717640");
			for (int i = 0; i < list.size(); i++) {
				cotentEntering crawler=new cotentEntering(list.get(i),false);
				crawler.setThreads(50);
				crawler.setTopN(5000);
				try {
					crawler.start(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
				cotentEntering crawler=new cotentEntering("http://www.yznn.com/modules/article/reader.php?aid=1&cid=717640",false);
				crawler.setThreads(50);
				crawler.setTopN(5000);
				try {
					crawler.start(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
