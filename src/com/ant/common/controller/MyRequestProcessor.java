package com.ant.common.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.RequestProcessor;

public class MyRequestProcessor 
				extends RequestProcessor {

	@Override
	protected boolean processPreprocess(
			HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(
	"MyRequestProcessor�� processPreprocess()호출..");
		try {
			request.setCharacterEncoding("utf_8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	

}
