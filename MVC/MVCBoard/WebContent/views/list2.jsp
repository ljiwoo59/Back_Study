<%@ page contentType="text/html;charset=utf-8" import="java.util.*, com.board.dto.Board" %>

<html><body>
<%
	ArrayList<Board> list = (ArrayList<Board>) request.getAttribute("list");
%>
<center>
		<img src="image/f1.jpg">
		<h1>MVC 게시판</h1>			
		
				
		<table border=1 cellspacing=1 cellpadding = 1><tr>
		<th width=100 bgcolor=#113366><font color=#ffffee size=2>번호</th>
		<th width=200 bgcolor=#113366><font color=#ffffee size=2>제목</th>
		<th width=100 bgcolor=#113366><font color=#ffffee size=2>글쓴이</th>
		<th width=150 bgcolor=#113366><font color=#ffffee size=2>날짜</th>
		<th width=100 bgcolor=#113366><font color=#ffffee size=2>조회수</th>

	  	<% for (Board b : list) { %>
		    <tr bgcolor=pink>
			   <td align=center bgcolor=pink>&nbsp;<font size=2><%= b.getNum() %></td>
		       
		      <td align=center bgcolor=pink>&nbsp;<font size=2><%= b.getTitle() %></td>
		       
		      <td align=center bgcolor=pink>&nbsp;<font size=2><%= b.getName() %></td>
		       
		      <td align=center bgcolor=pink>&nbsp;<font size=2><%= b.getWdate() %></td>
		       
		      <td align=center bgcolor=pink>&nbsp;<font size=2><%= b.getCount() %></td>	       
		    </tr> 
	   <%} %>
</table>
<br></br>
</body></html>