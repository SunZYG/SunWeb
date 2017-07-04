package com.learning;
/**
 * 线程学习类
 * @author 85366
 *
 */
public class Threads{
	/**
	 * 1、当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行，另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
	 * 2、当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。
     * 3、当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问也将被阻塞。
	 * 4、当一个线程访问object的一个synchronized(this)同步代码块时，它就获得了这个object的对象锁。结果其它线程对该object对象所有同步代码部分的访问都被暂时阻塞。
	 * 5、以上规则对其它对象锁同样适用。
	 */
	public static int is=1;
	public static int il=1;
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public  synchronized  void run() {
				System.out.println("+开始");
				for (int i = 0; i < 50; i++) {
					is++;
					System.out.println("is:"+is);
				}
				System.out.println("+结束");
			
			};
		}).start();
		new Thread(new Runnable() {
			public void run() {
				System.out.println("-开始");
				for (int i = 0; i < 50; i++) {
					il++;
					System.out.println("il:"+il);
				}
				System.out.println("-结束");
				
			};
		}).start();

	}



}
