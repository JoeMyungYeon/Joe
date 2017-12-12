package com.sist.model;

import javax.servlet.http.HttpServletRequest;

public interface Model {
	// default 메소드(구현된 메소드) jdk 1.8버전 이상에사 사용가능,
	// 상속된 다른 인터페이스에서 정의 할 필요 없음
	public String execute(HttpServletRequest req) 
		throws Exception;

}
