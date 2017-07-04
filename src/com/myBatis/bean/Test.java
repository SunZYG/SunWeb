package com.myBatis.bean;

import org.springframework.stereotype.Component;

@Component("test")  
public class Test {
    private Integer id;

    private String test;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}