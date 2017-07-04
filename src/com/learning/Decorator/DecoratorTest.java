package com.learning.Decorator;
/**
 * 装饰模式（Decorator）
 * 顾名思义，装饰模式就是给一个对象增加一些新的功能，而且是动态的，
 * 要求装饰对象和被装饰对象实现同一个接口，装饰对象持有被装饰对象的实例
 * @author 85366
 *
 */
public class DecoratorTest {  
	  
    public static void main(String[] args) {  
        Sourceable source = new Source();  
        Sourceable obj = new Decorator(source);  
        obj.method();  
    }  
}  
