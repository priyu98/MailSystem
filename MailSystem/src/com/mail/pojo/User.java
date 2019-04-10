package com.mail.pojo;

import java.security.NoSuchAlgorithmException;

import com.mail.util.PassUtil;

public class User {
	private String username;
	private String pwdHash;
	private String password;
	private String pwdAlgorithm="SHA";
	private int useForwarding=0;
	private String forwardDestination=null;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwdHash() {
		return pwdHash;
	}
	public void setPwdHash(String password) throws NoSuchAlgorithmException {
		this.pwdHash = PassUtil.digestString(password, "SHA");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIfAdmin() {
		return ifAdmin;
	}
	public void setIfAdmin(int ifAdmin) {
		this.ifAdmin = ifAdmin;
	}
	private int useAlias=0;
	private String alias=null;
	private int ifAdmin;
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		User user=new User();
		user.setPassword("sakura233");
		user.setPwdHash(user.getPassword());
		System.out.println(user.getPwdHash());
	}
}
