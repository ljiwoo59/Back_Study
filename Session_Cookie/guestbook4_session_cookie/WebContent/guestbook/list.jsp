<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,com.ssafy.guestbook.dto.GuestBookDto"%>
<%@ include file="/include/header.jsp" %>
<%
if (memberDto == null) {
%>
<script>
alert("로그인 후 이용 가능합니다.");
location.href = "<%= root %>/user/login.jsp";
</script>
<%
} else {
List<GuestBookDto> list = (List<GuestBookDto>) request.getAttribute("articles");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SSAFY - 글목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        mark.sky {
            background: linear-gradient(to top, #54fff9 20%, transparent 30%);
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#mvRegisterBtn").click(function () {
                location.href = "<%= root %>/guestbook/write.jsp";
            });
        });
    </script>
</head>
<body>
    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
            <h2 class="p-3 mb-3 shadow bg-light"><mark class="sky">글목록</mark></h2>
            <div class="m-3 text-right">
                <button type="button" id="mvRegisterBtn" class="btn btn-link">글작성</button>
            </div>
<%
if(list != null && list.size() != 0) {
	for(GuestBookDto article : list) {
%>
			<table class="table table-active text-left">
				<tbody>
					<tr class="table-info">
						<td>작성자 : <%= article.getUserId() %></td>
						<td class="text-right">작성일 : <%= article.getRegTime() %></td>
					</tr>
					<tr>
						<td colspan="2" class="table-danger">
							<strong><%= article.getArticleNo() %>. <%= article.getSubject() %></strong>
						</td>
					</tr>
					<tr>
						<td class="p-4" colspan="2">
							<%= article.getContent() %>
						</td>
					</tr>
				</tbody>
			</table>
<%
	}
} else {
%>
			<table class="table table-active text-center">
				<tr class="table-info">
					<td>작성한 글이 없습니다.</td>
				</tr>
			</table>
<%	
}
%>
		</div>
    </div>
</body>
</html>
<%
}
%>