package com.learning;

public class Threads1 implements Runnable {   
    public void run() {   
         synchronized(this) {    // 同步代码块  
              for (int i = 0; i < 5; i++) {   
                   System.out.println(Thread.currentThread().getName() + " synchronized loop " + i);   
              }   
         }   
    }   
    public static void main(String[] args) {   
         Threads1 t1 = new Threads1();   
         Thread ta = new Thread(t1, "A");   
         Thread tb = new Thread(t1, "B");   
         ta.start();   
         tb.start();   
    }  
} 
