package com.learning.AbstractFactory;

public class SendSmsFactory implements Provider{  
	  
    @Override  
    public Sender produce() {  
        return new SmsSender();  
    }  
} 