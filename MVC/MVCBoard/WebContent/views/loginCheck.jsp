<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${empty sessionScope.id}">
		<a href="loginForm.bod">로그인</a>
	</c:if>
	<c:if test="${!empty sessionScope.id}">
		Welcome, ${id}!!!
		<a href="logout.bod">로그아웃</a>
	</c:if>
</body>
</html>