package com.noval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.commonality.ChineseToEnglish;
import com.commonality.MySqlJdbc;
import com.commonality.Util;
import com.myBatis.bean.Catalogue;
import com.myBatis.model.impl.CatalogBusinessImpl;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * 
 * @author 85366
 *
 */
public class novalEntering extends BreadthCrawler {
	static String urls="http://www.yznn.com/modules/article/reader.php?aid=";
	public static Integer sums=null;
	static Integer max=38000;
	static List<Catalogue> cats=new ArrayList<Catalogue>();
	public static Map<String,String []> lists = new ConcurrentHashMap<String, String[]>();
	public static Map<String,Elements> index_lists = new ConcurrentHashMap<String, Elements>();
	public static Map<String,Elements> pages = new ConcurrentHashMap<String, Elements>();
	public novalEntering(String crawlPath, boolean autoParse) {
	super("crawl", true);
	this.addSeed(crawlPath);
	this.addRegex(crawlPath);

	this.addRegex("-.*\\.(jpg|png|gif).*");

	this.addRegex("-.*#.*");
	}

	@SuppressWarnings("unused")
	public void visit(Page page, CrawlDatums next) {
		try {
			if(sums==null){
				sums=CatalogBusinessImpl.sums;
			}
			System.out.println("*****************************sums:"+sums+"*********************");
			String url = page.getUrl();
			@SuppressWarnings({ "deprecation" })
			Document doc = page.getDoc();
			/* extract title and content of news by css selector */
			Elements content = page.select("div[class=zjlist4] ol li a");
			Elements head=page.select("div[class=src] a");
			if(sums<=max)
				sums++;
			
			next.add(urls+sums);
			if(head==null || head.size()<=0){
				return;
			}
			System.out.println(url);
//			String types=head.get(1).text();
//			String auth=head.get(2).text();
//			String names=head.get(3).text();
//			System.out.println(types+">"+auth+">"+names);
			String uuid=Util.getUUID();
			lists.put(uuid, new String []{head.get(1).text(),head.get(2).text(),head.get(3).text(),(sums-1)+""});
			pages.put(uuid, content);
			System.out.println("lists :"+lists.size());
			System.out.println("pages :"+pages.size());
//			
//			Map<String, String> maps = new HashMap<String, String>();
//			for (org.jsoup.nodes.Element element : content) {
//				maps.put("index_name", element.text());
//				if(element.attr("href").indexOf(".html")>0){
//					String href=element.attr("href");
//					href=url.substring(0,url.indexOf("index.html"))+href;
//					maps.put("url", href);
//				}else{
//					maps.put("url", element.attr("href"));
//				}
//				index_lists.put("", "");
////				Catalogue cat = (Catalogue) SunBeanUtils.getObjBeans(maps, new Catalogue());
////				cats.add(cat);
//			}
			//鎻掑叆鐩綍
//			for (int i = 0; i < cats.size(); i++) {
//				try {
//					MySqlJdbc.excuteMySql("insert into catalogue (index_name,href,type) values ('"+cats.get(i).getIndex_name()+"','"+cats.get(i).getUrl()+"','"+uuid+"') ");
//				} catch (Throwable e) {
//					e.printStackTrace();
//				}
//			}
			//cats.clear();
//			index_lists.put(uuid, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 灏忚 鍚嶏紝绔犺妭褰曞叆
	 * 杩斿洖灏忚瀵瑰簲id
	 */
	public String  sectionEntering(String url,Integer number){
		//novalEntering();
		//寰楀埌灏忚鍚� 锛� 鐢熸垚瀵瑰簲id= 灏忚鍚� +number
		ChineseToEnglish.getPingYin("");
		return url;
		
	}
	/**
	 * 灏忚鍐呭褰曞叆
	 */
	public void contentEntering(String url,String id){
		
	}
	
	/**
	 * 
	 */
	public  void   novalEnter(String url){
		Integer max=3;//38000
		for (int i = 1; i <= max; i++) {
			String id =sectionEntering(url+"?aid="+i,i);
			System.out.println(" "+id);
			contentEntering(url,id);
		}
		
	}
	/**
	 * 閮ㄥ垎 id 澧為暱  鐖彇鏂瑰紡
	 * @param args 鐩墠 鍒�20
	 */
	public static void enteringBySoet(){
		Integer max=100;//38000
		String urls="http://www.yznn.com/modules/article/reader.php";
		for (int i = 2; i <= max; i++) {

			novalEntering crawler=new novalEntering(urls+"?aid="+i,false);

			crawler.setThreads(1);

			crawler.setTopN(1);
			try {
				crawler.start(1);
			} catch (Exception e) {
				e.printStackTrace();
			}

			cotentEntering.updateContent();
//			break;
		}
	}
	/**
	 * 绱㈠紩椤甸潰  鐖彇鏂瑰紡
	 * @param args
	 */
	public void enteringByIndex(){
	    String urls="http://www.yznn.com/files/article/html/0/903/index.html";
		//褰曞叆灏忚鍚� 鍜岀珷鑺�
		novalEntering crawler=new novalEntering(urls,false);
		/* 绾跨▼鏁� */
		crawler.setThreads(50);
		/* 璁剧疆姣忔杩唬涓埇鍙栨暟閲忕殑涓婇檺 */
		crawler.setTopN(5000);
		try {
			crawler.start(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//鏇存柊 鏂囨湰涓虹┖鐨� 鏁版嵁
		cotentEntering.updateContent();
	}
	public static void main(String[] args) {
//		enteringBySoet();
		try {
			new Thread(new Runnable() {
				public void run() {
					try {
						novalEntering crawler=new novalEntering(urls+sums,false);
						crawler.setThreads(500);
						crawler.setTopN(5000);
						crawler.start(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
//			new Thread(new Runnable() {
//				public void run() {
//					try {
//						novalEntering crawler=new novalEntering(urls+sums,false);
//						crawler.setThreads(50);
//						crawler.setTopN(5000);
//						crawler.start(5);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 处理小说内容
	 */
	public void entering(){
		
	}

}
