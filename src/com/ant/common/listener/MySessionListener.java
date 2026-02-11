package com.ant.common.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener {

	public MySessionListener(){}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session=event.getSession();	
		System.out.println("==============>技记 积己凳:");		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("==============> 技记 力芭凳");
		HttpSession session=event.getSession();
	}
}
