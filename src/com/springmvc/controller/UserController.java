package com.springmvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.commonality.Util;
import com.myBatis.bean.Test;
import com.myBatis.dao.ICatalogueDao;
import com.myBatis.dao.TestMapper;
import com.springmvc.entity.User;
import com.test.testMap;
/**
 *   消费者模式 三个线程 拉取 数据 一个线程 插入数据
 * 
 * @author zyg
 */
@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private   TestMapper testMapper;
    @RequestMapping("/test")
    public void test() {

    	System.out.println("进入方法");
    	stars();
    }
    
	static Map<String,String []> lists = new ConcurrentHashMap<String, String[]>();
	/**
	 * 添加元素
	 */
	public static void add(){
		lists.put(Util.getUUID(), new String [] {"test"});
	}
	/**
	 * 查询元素 数量 
	 */
	public  void select(){
		System.out.println("当前集合大小："+lists.size());
		List<Test> tests=new ArrayList<Test>();
		for (Entry<String, String[]> entry : lists.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			Test t =new Test();
			t.setTest(entry.getKey());
			tests.add(t);
			deldte(entry.getKey());
		}
		if(tests!=null && tests.size()>0){
			testMapper.insertCodeBatch(tests);
		}
	}
	/**
	 * 删除 元素
	 */
	public  void deldte(String key){
		lists.remove(key);
	}
	static int i1 = 0;
	static int i2 = 0;
	static int i3 = 0;
	static int i4 = 0;
	public  void stars() {
		//线程 1 启动
		new Thread(new Runnable() {
			public void run() {
				System.out.println("线程1启动");
				while (true) {
					add();
					try {
						i1++;
						System.out.println("线程1:"+i1);
						Thread.sleep(10 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				System.out.println("线程2启动");
				while (true) {
					add();
					try {
						i2++;
						System.out.println("线程2:"+i2);
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}).start();
		//线程 1 启动
		new Thread(new Runnable() {
			public void run() {
				System.out.println("线程3启动");
				while (true) {
					add();
					try {
						i3++;
						System.out.println("线程3:"+i3);
						Thread.sleep(3 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}).start();
		//线程 1 启动
		new Thread(new Runnable() {
			public void run() {
				System.out.println("线程4启动");
				while (true) {
					select();
					try {
						i4++;
						System.out.println("线程4:"+i4);
						Thread.sleep(2 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}).start();
	}
}
