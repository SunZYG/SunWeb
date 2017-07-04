package com.test;

 
public class TestMain {
    public static void main(String[] args) {
 
        ClassLoader loader = map.class.getClassLoader();
        while (loader!=null){
            System.out.println(loader);
            loader = loader.getParent();
        }
    }
}