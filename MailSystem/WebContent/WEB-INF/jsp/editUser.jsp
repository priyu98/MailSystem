<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
 <div style="width:500px;margin:0px auto;text-align:center">
	
	
	<div style="text-align:center;margin-top:40px">
		
		<form method="post" action="editPassword">
			<p align="left">
			用户名： ${user.username} 
			</p>
			<p align="left">
			<input type="hidden" value="${user.username}" name="username">
			密码： <input type="text" value="${user.password}" name="password">
			</p>
			<input type="submit" value="保存">
			
		</form>

	</div>	
 </div>