package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TestThread {

	public static void main(String[] args) {
        String readline=null;
		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        // 由Socket对象得到输入流，并构造相应的BufferedReader对象
          try {
			readline = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 从系统标准输入读入一字符串
          System.out.println("打印:"+readline);

	}

}
