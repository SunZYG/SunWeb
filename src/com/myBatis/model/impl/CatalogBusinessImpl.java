package com.myBatis.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.xmlbeans.impl.tool.SchemaCopy;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.commonality.ChineseToEnglish;
import com.commonality.Util;
import com.myBatis.bean.Catalogue;
import com.myBatis.bean.CatalogueType;
import com.myBatis.bean.CatalogueTypeExample;
import com.myBatis.bean.Test;
import com.myBatis.dao.ICatalogueDao;
import com.myBatis.dao.ICatalogueTypeDao;
import com.myBatis.model.CatalogBusiness;
import com.noval.SunBeanUtils;
import com.noval.cotentEntering;
import com.noval.novalEntering;
import com.util.Constant;


/**
 * 小说 业务层
 * @author 85366
 *
 */
@Service("CatalogService")
//implements CatalogBusiness
public class CatalogBusinessImpl   implements CatalogBusiness {
	@Resource(name = "iCatalogueTypeDao")
	private ICatalogueTypeDao catalogueTypeDao;
	
	@Resource(name = "iCatalogueDao")
	private ICatalogueDao catalogueDao;
	
	@Resource(name = "catalogueType")
	private CatalogueType catalogueType;
	
	public static String urls="http://www.yznn.com/modules/article/reader.php?aid=";
	public static Integer sums=1;
	public static List<Catalogue> list_url=null;
	public static boolean is_stop=false;
	//目录合集
	public static Map<String,String []> lists = new ConcurrentHashMap<String, String[]>();
	public void enteringCatalog() {
		updateSum();
		//启动 爬虫
		novalEntering crawler=new novalEntering(urls+sums,false);
		crawler.setThreads(500);
		crawler.setTopN(5000);
		try {
			crawler.start(38000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 爬取前  更新 小说 最新位置
	 */
	private void updateSum() {
		sums=catalogueTypeDao.selectNewsSum();
	}
	/**
	 * 处理 lists 中小说目录数据 插入库中
	 */
	public  boolean enteringAuth(){
		List<CatalogueType> cattype=new ArrayList<CatalogueType>();
		for (Entry<String, String[]> entry : novalEntering.lists.entrySet()) {
			cattype.add(getcatObject(entry.getKey(),entry.getValue()));
			System.out.println("插入目录："+entry.getValue()[2]);
			novalEntering.lists.remove(entry.getKey());
		}
		if(cattype!=null && cattype.size()>0){
			catalogueTypeDao.insertBatch(cattype);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 处理 pages 中小说章节 插入库中
	 */
	public boolean enteringIndex(){
		boolean is =false;
		for (Entry<String, Elements> entry : novalEntering.pages.entrySet()) {
			is=true;
			//循环插入某本小说所有章节
			List <Catalogue> cat=getcatObject(entry.getKey(),entry.getValue());
			if(cat!=null && cat.size()>0){
				catalogueDao.insertBatch(cat);
			}
			novalEntering.pages.remove(entry.getKey());
		}
		return is;
	}
	
	private List<Catalogue> getcatObject(String key,List<org.jsoup.nodes.Element> content) {
		List<Catalogue> cats=new ArrayList<Catalogue>();
		Map<String, String> maps = new HashMap<String, String>();
		int i=1;
		for (org.jsoup.nodes.Element element : content) {
			maps.put("indexName", element.text());
			if(element.attr("href").indexOf(".html")<0){
				maps.put("href", element.attr("href"));
			}
//			Catalogue cat = (Catalogue) SunBeanUtils.getObjBeans(maps, new Catalogue());
			//String indexName, String href, String type, String index, String name, String intIndex
			String index=element.text();
			String name=element.text();
//			String intIndex="0";
			String href =element.attr("href");
			href=href.replace("/", "//");
			href=href.replace("'", "");
			href=href.replace("*", "");
			String str=index.indexOf(" ")>0?" ":"章";
			index=index.indexOf(str)>0?index.substring(0, index.indexOf(str)):"";
			name=name.indexOf(str)>0?name.substring(name.indexOf(str)):name;
//			intIndex=index!=null && index.length()>0?getIndexNums(index):"0";
			Catalogue cat=new Catalogue(
					element.text(),element.attr("href"),
					key,index,name,i+""
					);
			i++;
			cats.add(cat);
		}
		return cats;
	}
	/**
	 * 将中文章节 转换成数字  如 第十二章  》 12
	 * @param index
	 * @return
	 */
	private String getIndexNums(String index) {
		index=index.replace("第", "");
		index=index.replace("章", "");
		return chineseNumber2Int(index)+"";
	}
	@SuppressWarnings("unused")
	private static int chineseNumber2Int(String chineseNumber){
		int result = 0;
		int temp = 1;//存放一个单位的数字如：十万
		int count = 0;//判断是否有chArr
		char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
		char[] chArr = new char[]{'十','百','千','万','亿'};
		for (int i = 0; i < chineseNumber.length(); i++) {
			boolean b = true;//判断是否是chArr
			char c = chineseNumber.charAt(i);
			for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
				if (c == cnArr[j]) {
					if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
						result += temp;
						temp = 1;
						count = 0;
					}
					// 下标+1，就是对应的值
					temp = j + 1;
					b = false;
					break;
				}
			}
			if(b){//单位{'十','百','千','万','亿'}
				for (int j = 0; j < chArr.length; j++) {
					if (c == chArr[j]) {
						switch (j) {
						case 0:
							temp *= 10;
							break;
						case 1:
							temp *= 100;
							break;
						case 2:
							temp *= 1000;
							break;
						case 3:
							temp *= 10000;
							break;
						case 4:
							temp *= 100000000;
							break;
						default:
							break;
						}
						count++;
					}
				}
			}
			if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
				result += temp;
			}
		}
		return result;
	}
	/**
	 * 处理 pages 中小说内容 插入库中
	 */
	public synchronized  boolean enteringcontext(){
//		boolean is=false;
		List<Catalogue> cat=new ArrayList<Catalogue>();
//		 Map<String,String > contexts =cotentEntering.contexts;
//		 cotentEntering.contexts.clear();
		for (Entry<String, String> entry : cotentEntering.contexts.entrySet()) {
			cat.add(new Catalogue(entry.getKey(),entry.getValue()));
			cotentEntering.contexts.remove(entry.getKey());
		}
		if(cat!=null && cat.size()>0){
			catalogueDao.updateContextList(cat);
			return true;
		}else{
			return false;
		}
	}
	private CatalogueType getcatObject(String key,String[] strings) {
		return new CatalogueType(
				strings[2], 
				ChineseToEnglish.getPingYin(strings[2]), 
				key, 
				strings[1], 
				strings[0],
				Constant.getNowTime(),
				Constant.getNowTime(),
				Integer.valueOf(strings[3]));
	}

	/**
	 * 删除 元素
	 */
	public  void deldte(String key){
		lists.remove(key);
	}
	public void stars() {
		//线程 1 启动  拉取章节数据
		new Thread(new Runnable() {
			public void run() {
				System.out.println("**********************章节拉取开始***********************");
				enteringCatalog();
				System.out.println("lists :"+novalEntering.lists.size());
				System.out.println("pages :"+novalEntering.pages.size());
				System.out.println("**********************章节拉取结束***********************");
			};
		}).start();
		//线程2启动 插入数据库
		new Thread(new Runnable() {
			public void run() {
				int i=0;
				//三次访问 没数据退出
				int sum=0;
				System.out.println("线程2启动");
				while (true) {
					try {
						System.out.println("线程2 执行"+i++);
						if(!enteringAuth()){
							sum++;
						}else{
							sum=0;
						}
						if(sum>=3)
						break;
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("线程2 退出");
			};
		}).start();
	//线程3启动 插入数据库
	new Thread(new Runnable() {
		public void run() {
			int i=0;
			System.out.println("线程3启动");
			int sum=0;
			while (true) {
				try {
					System.out.println("线程3 执行"+i++);
					if(!enteringIndex()){
						sum++;
					}else{
						sum=0;
					}
					if(sum>=3)
					break;

					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("线程3 退出");
		};
	}).start();
	

}
	public CatalogueType  selectCatalogueType(String name){
		catalogueType=(CatalogueType) catalogueTypeDao.selectByName(name);
		return catalogueType;
		
	}


	public List<CatalogueType> selectCatalogueTypeList(String name) {
		List<CatalogueType> list =new ArrayList<CatalogueType>();
		list= catalogueTypeDao.selectByName(name);
		return list;
	}


	public List<Catalogue> selectCatalogueIndex(String id) {
		return catalogueDao.selectByUUID(id);
	}


	public Catalogue selectCatalogueContext(String id) {
		return catalogueDao.selectByID(id);
	}
	@Override
	public void starsContent() {
		//线程4启动   爬取小说内容 
		Thread trs =new Thread(new Runnable() {
			public void run() {
				int i=0;
				System.out.println("线程4启动");
				int sum=0;
				List<Catalogue> listcat=cotentEntering.lists=catalogueDao.selectContextIsNULL();
				if(listcat==null || listcat.size()<=0){
					return;
				}
				try {
					cotentEntering crawler=new cotentEntering(cotentEntering.lists.get(0).getHref(),false);
					crawler.setThreads(2);
					crawler.setTopN(5000);
					try {
						crawler.start(10000*1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					System.out.println("线程4结束");
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
			}
		});
		trs.start();
		
		
		//线程5启动   小说内容 插入数据 库
		new Thread(new Runnable() {
			public void run() {
				int i=0;
				System.out.println("线程5启动,先等待 25秒拉取数据");
				int sum=0;
				try {
					Thread.sleep(25*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
					while(true){
						System.out.println("线程5 执行"+i++ +",sum:"+sum);
						boolean is =enteringcontext();
						if(!is){
							sum++;
							try {
								Thread.sleep(25*1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}else{
							sum=0;
						}
						if(sum>=3 && cotentEntering.contexts.size()==0 ){
						break;
						}
					}
					is_stop=true;
					System.out.println("**********************线程5结束**************************");
				
			}
		}).start();
		
		while(true){
			if(is_stop){
				try {
					trs.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("线程退出！！！！");
			}
			
		}
		
	}
}
