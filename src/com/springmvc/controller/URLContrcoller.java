package com.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springmvc.entity.User;

/**
 * 用户管理
 * 
 * @author springmvc
 */
@Controller
@RequestMapping("/urls")
public class URLContrcoller {
	
    @RequestMapping("/resp")
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect("Manage/sql-excult.html");
    }
    @RequestMapping("/thtml")
    public void handleRequest1(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect("../index.html");
    }
    
    @RequestMapping("/test")
    public void TestRequest1(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	 req.getRequestDispatcher("index.html").forward(req,resp);
    }
}
