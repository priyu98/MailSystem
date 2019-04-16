<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<table align='center' border='1' cellspacing='0'>
    <tr>
        <td>id</td>
        <td>password</td>
        <td>编辑</td>
	    <td>删除</td>
    </tr>
    <c:forEach items="${userlist}" var="user" varStatus="st">
        <tr>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td><a href="editUser?username=${user.username}">修改密码</a></td>
	        <td><a href="deleteUser?username=${user.username}">删除</a></td>
        </tr>
    </c:forEach>
</table>
<script>
	function test(){
		$.ajax({
			url:'AdminLogin',
			type:'post',
			data:{username: '1', pass: '1'},
			dataType: "text", 
			success:function(res){
				window.alert(res.result)
			}
		});
	}
</script>
<div style="text-align:center;margin-top:40px">
		
		<form method="post">
			用户名： <input name="username" value="" type="text"> <br><br>
			密码：<input name="password" value="" type="text"><br><br>
			<input type="button" value="增加用户" onclick="test()">
		</form>

	</div>	