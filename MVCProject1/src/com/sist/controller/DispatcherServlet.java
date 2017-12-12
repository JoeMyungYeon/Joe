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

import java.util.*; //Map (요청=> 클래스(모델) 매칭)


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
	// csv일 경우 => list,com.sist.model.MovieList
	/*	
	 * 	key			value
	 *  list   new MovieList()   or  Class.forName()
	 *  detail new MovieDetail()
	 *  
	 *  리플렉션
	 *  클래스 이름(정보)를 읽어서 메모리 할당을 한다.
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
				// init 메소드는 한번만 호출 되기 때문에 최적화에 조음
			}
//			clsMap.put("list", new MovieList());
//			clsMap.put("detail", new MovieList()); // 클래스 갯수가 많기 때문에 지양함
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			System.out.println("6");
		}
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//list.do, detail.do => movie.do?cmd=list
			String cmd=request.getRequestURI();
			// uri : 사용자가 주소입력난에 요청한 파일
			// http://localhost:8080/MVCProject1/list.do
			// uri : /MVCProject1/list.do
			// 사용자가 요청한 내용
			cmd=cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf("."));
			// 요청을 처리 => 모델클래스(클래스,메소드)
			Model model=(Model)clsMap.get(cmd);
			// model => 실행을 한 후에 결과를 request에 담아 달라
			// Call by reference => 주소를 넘겨주고 주소에 값을 채운다
			String jsp=model.execute(request);
			//Jsp에 request, session값을 전송
			RequestDispatcher rd=
							request.getRequestDispatcher(jsp);
			rd.forward(request, response);
			/*
			 *  jsp의 _jspService()를 호출한다
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









