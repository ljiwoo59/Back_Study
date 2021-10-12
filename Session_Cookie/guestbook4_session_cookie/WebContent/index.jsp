<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SSAFY</title>
</head>
<body>
	<div align="center">
		<h3>SSAFY 방명록!!!(session_cookie)</h3>
		<%
			if (memberDto == null) {
		%>
		<a href="<%= root %>/user/join.jsp">회원가입</a><br> 
		<a href="<%= root %>/user/login.jsp">로그인</a><br> 
		<%
			}else {
		%>
		<strong><%= memberDto.getUserName() %>(<%= memberDto.getEmail() %>)</strong> 님 안녕하세요.<br>
		<a href="<%= root %>/user?act=logout">로그아웃</a><br> 
		<a href="<%= root %>/guestbook/write.jsp">글쓰기</a><br> 
		<a href="<%= root %>/guestbook?act=list">글목록</a>
		<%
			}
		%>
	</div>
</body>
</html>