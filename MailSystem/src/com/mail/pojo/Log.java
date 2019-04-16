package com.mail.pojo;

import com.mail.util.DateTimeUtil;

public class Log {
	private String user;
	private String operation;
	private String oper_time;
	
	public Log(String user,String operation) {
		this.user=user;
		this.operation=operation;
		this.oper_time=new DateTimeUtil().getDatetime();
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOper_time() {
		return oper_time;
	}
	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}
}
