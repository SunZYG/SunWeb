package com.noval;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.myBatis.bean.Catalogue;

/**
 * 
 * @author 85366
 *
 */
public class WebCollect extends BreadthCrawler {
	/**
	 * @param crawlPath
	 *            crawlPath is the path of the directory which maintains
	 *            information of this crawler
	 * @param autoParse
	 *            if autoParse is true,BreadthCrawler will auto extract links
	 *            which match regex rules from pag
	 */
	private static String url="http://www.yznn.com/modules/article/reader.php?aid=";
	static Integer sums=1;
	public WebCollect(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
//		/* 种子页面 */
//		this.addSeed("http://news.hfut.edu.cn/list-1-1.html");
//
//		/* 正则规则设置 */
//		/* 爬取符合 http://news.hfut.edu.cn/show-xxxxxxhtml的URL */
//		this.addRegex("http://news.hfut.edu.cn/show-.*html");
//		/* 不要爬取 jpg|png|gif */
//		this.addRegex("-.*\\.(jpg|png|gif).*");
//		/* 不要爬取包含 # 的URL */
//		this.addRegex("-.*#.*");
		this.addSeed(url);
		this.addRegex(url);
		this.addRegex("-.*\\.(jpg|png|gif).*");
		this.addRegex("-.*#.*");
	}
	static List<Catalogue> cats=new ArrayList<Catalogue>();
	@Override
	public void visit(Page page, CrawlDatums next) {
		try {
		@SuppressWarnings("unused")
		String url = page.getUrl();
		/* 判断是否为新闻页，通过正则可以轻松判断 */
//		if (page.matchUrl("http://www.yznn.com/modules/article/reader.php?aid=39")) {
			/* we use jsoup to parse page */
			@SuppressWarnings({ "deprecation", "unused" })
			Document doc = page.getDoc();
			/* extract title and content of news by css selector */
			Elements content = page.select("div[class=zjlist4] ol li a");
			if(cats.size()==0){
				@SuppressWarnings("unused")
				String name = page.select("span[id=htmldhshuming]").first().text();
			}
			Map<String, String> maps=new HashMap<String,String>();
			for (org.jsoup.nodes.Element element : content) {
				maps.put("index_name", element.text());
				maps.put("url", element.attr("href"));
				Catalogue cat=(Catalogue) SunBeanUtils.getObjBeans(maps, new Catalogue());
				cats.add(cat);
			}
			/*
			 * 如果你想添加新的爬取任务，可以向next中添加爬取任务， 这就是上文中提到的手动解析
			 */
			next.add(url+(sums+1));
			/*
			 * WebCollector会自动去掉重复的任务(通过任务的key，默认是URL)，
			 * 因此在编写爬虫时不需要考虑去重问题，加入重复的URL不会导致重复爬取
			 */
			/*
			 * 如果autoParse是true(构造函数的第二个参数)，爬虫会自动抽取网页中符合正则规则的URL，
			 * 作为后续任务，当然，爬虫会去掉重复的URL，不会爬取历史中爬取过的URL。 autoParse为true即开启自动解析机制
			 */
			// next.add("http://xxxxxx.com");
//		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		WebCollect crawler = new WebCollect("crawl", true);
		/* 线程数 */
		crawler.setThreads(50);
		/* 设置每次迭代中爬取数量的上限 */
		crawler.setTopN(5000);
		/*
		 * 设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
		 * 如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
		 * 续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到 死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
		 * 的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务 进行爬取。断点爬取默认为false
		 */
		// crawler.setResumable(true);
		/*
		 * 开始深度为4的爬取，这里深度和网站的拓扑结构没有任何关系 可以将深度理解为迭代次数，往往迭代次数越多，爬取的数据越多
		 */
		crawler.start(4);
		for (int i = 0; i < cats.size(); i++) {
			Catalogue cat=cats.get(i);
//			System.out.println(cat.getIndex_name()+cat.getUrl());
		}
	}
}
