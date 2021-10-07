<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.ssafy.util.DBUtil,java.sql.*" %>
<%! private final DBUtil dbUtil = DBUtil.getInstance(); %> 
    
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
                location.href = "/guestbook2_jsp/guestbook/write.jsp";
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
	            Connection conn = null;
	    		PreparedStatement pstmt = null;
	    		ResultSet rs = null;
	
	    		try {
	    			conn = dbUtil.getConnection();
	    			StringBuilder listArticle = new StringBuilder();
	    			listArticle.append("select articleno, userid, subject, content, regtime \n");
	    			listArticle.append("from guestbook \n");
	    			listArticle.append("order by articleno desc \n");
	    			pstmt = conn.prepareStatement(listArticle.toString());
	    			rs = pstmt.executeQuery();
	    			while (rs.next()) {
            %>
            <table class="table table-active text-left">
                <tbody>
                    <tr class="table-info">
                        <td>작성자 : <%= rs.getString("userid") %></td>
                        <td class="text-right">작성일 : <%= rs.getString("regtime") %></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="table-danger">
                            <strong><%= rs.getInt("articleno") %>. <%= rs.getString("subject") %></strong>
                        </td>
                    </tr>
                    <tr>
                        <td class="p-4" colspan="2"><%= rs.getString("content") %></td>
                    </tr>
                </tbody>
            </table>
            <%
		    			}
		    		} catch (SQLException e) {
		    			e.printStackTrace();
		    		} finally {
		    			dbUtil.close(pstmt, conn);
		    		}
            %>
        </div>
    </div>
</body>
</html>