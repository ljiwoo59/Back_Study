<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html><body>

<center>
		<img src="image/f1.jpg">
		<h1>MVC-JSTL 게시판</h1>		
		<%@ include file="loginCheck.jsp" %>
		<%--
		<jsp:include page="loginCheck.jsp" />
		 --%>
		 <hr>
	
		<table border=1 cellspacing=1 cellpadding = 1><tr>
		<th width=100 bgcolor=#113366><font color=#ffffee size=2>번호</th>
		<th width=200 bgcolor=#113366><font color=#ffffee size=2>제목</th>
		<th width=100 bgcolor=#113366><font color=#ffffee size=2>글쓴이</th>
		<th width=150 bgcolor=#113366><font color=#ffffee size=2>날짜</th>
		<th width=100 bgcolor=#113366><font color=#ffffee size=2>조회수</th>

	  	<c:forEach items="${searched}" var="b">
		    <tr bgcolor=pink>
			   <td align=center bgcolor=pink>&nbsp;<font size=2>${b.num}</td>
		       
		      <td align=center bgcolor=pink>&nbsp;<font size=2>
		      	<a href="read.bod?num=${b.num}">${b.title}</a></td>
		       
		      <td align=center bgcolor=pink>&nbsp;<font size=2>${b.name}</td>
		       
		      <td align=center bgcolor=pink>&nbsp;<font size=2>${b.wdate}</td>
		       
		      <td align=center bgcolor=pink>&nbsp;<font size=2>${b.count}</td>	       
		    </tr> 
	   </c:forEach>
</table>
<br>
<a href="list.bod">돌아가기</a><br>
</body></html>