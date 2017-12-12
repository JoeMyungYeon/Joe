package com.sist.controller;

import java.io.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.Model;
import com.sist.model.MovieList;

import java.util.*; //Map (��û=> Ŭ����(��) ��Ī)


public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String[] strCls= {
		"com.sist.model.MovieList",
		"com.sist.model.MovieDetail"
	};
	private String[] strCmd = {
		"list", "detail"	
	};
	// <bean id="list" class="com.sist.model.MovieList" />
	// csv�� ��� => list,com.sist.model.MovieList
	/*	
	 * 	key			value
	 *  list   new MovieList()   or  Class.forName()
	 *  detail new MovieDetail()
	 *  
	 *  ���÷���
	 *  Ŭ���� �̸�(����)�� �о �޸� �Ҵ��� �Ѵ�.
	 */
	private Map clsMap=new HashMap();
	// HashMap, Hashtable
	public void init(ServletConfig config) throws ServletException {
		try {
			for(int i=0;i<strCls.length;i++) {
				Class clsName=Class.forName(strCls[i]);
				Object obj=clsName.newInstance();
				clsMap.put(strCmd[i], obj);
				// Singleton
				// init �޼ҵ�� �ѹ��� ȣ�� �Ǳ� ������ ����ȭ�� ����
			}
//			clsMap.put("list", new MovieList());
//			clsMap.put("detail", new MovieList()); // Ŭ���� ������ ���� ������ ������
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			System.out.println("6");
		}
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//list.do, detail.do => movie.do?cmd=list
			String cmd=request.getRequestURI();
			// uri : ����ڰ� �ּ��Է³��� ��û�� ����
			// http://localhost:8080/MVCProject1/list.do
			// uri : /MVCProject1/list.do
			// ����ڰ� ��û�� ����
			cmd=cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf("."));
			// ��û�� ó�� => ��Ŭ����(Ŭ����,�޼ҵ�)
			Model model=(Model)clsMap.get(cmd);
			// model => ������ �� �Ŀ� ����� request�� ��� �޶�
			// Call by reference => �ּҸ� �Ѱ��ְ� �ּҿ� ���� ä���
			String jsp=model.execute(request);
			//Jsp�� request, session���� ����
			RequestDispatcher rd=
							request.getRequestDispatcher(jsp);
			rd.forward(request, response);
			/*
			 *  jsp�� _jspService()�� ȣ���Ѵ�
			 *  service(request, response) {
			 *  	_jspService(request);
			 *  }
			 */
		
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println("7");
		}
	}

}









