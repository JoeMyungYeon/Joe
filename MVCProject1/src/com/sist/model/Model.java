package com.sist.model;

import javax.servlet.http.HttpServletRequest;

public interface Model {
	// default �޼ҵ�(������ �޼ҵ�) jdk 1.8���� �̻󿡻� ��밡��,
	// ��ӵ� �ٸ� �������̽����� ���� �� �ʿ� ����
	public String execute(HttpServletRequest req) 
		throws Exception;

}
