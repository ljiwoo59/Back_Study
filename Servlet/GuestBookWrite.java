package com.ssafy.guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.util.DBUtil;


@WebServlet("/articlewrite") // 접속할 수 있는 URL 이름
public class GuestBookWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DBUtil util = DBUtil.getInstance();
	
	// service method: 클라이언트로부터 요청이 들어오면 응답을 만들어 내는 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. data get
		request.setCharacterEncoding("utf-8"); // post 에서만!!!
		String id = request.getParameter("userid");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		System.out.println(subject);
		
		// 2. 1 의 데이터를 이용하여 logic : DB
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		try {
			conn = util.getConnection();
			String sql = "insert into guestbook (userid, subject, content) \n";
			sql += "values (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, subject);
			pstmt.setString(3, content);
			cnt = pstmt.executeUpdate(); // insert 된 개수 리턴
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt, conn);
		}
		
		// 3. 2 의 결과에 따른 response page >> 성공 또는 실패
		response.setContentType("text/html;charset=utf-8"); // 서버에서 응답으로 나가는 컨텐트 타입 지정
		PrintWriter out = response.getWriter(); // 서버에서 응답으로 나가는 컨텐트 출력을 위한 출력 스트림
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("    <meta charset=\"UTF-8\">");
		out.println("    <title>SSAFY - 글목록</title>");
		out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");
		out.println("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
		out.println("    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>");
		out.println("    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>");
		out.println("    <script type=\"text/javascript\">");
		out.println("        $(document).ready(function () {");
		out.println("            $(\"#mvListBtn\").click(function () {");
		out.println("                location.href = \"/guestbook1_servlet/listarticle\";");
		out.println("            });");
		out.println("            $(\"#mvRegisterBtn\").click(function () {");
		out.println("                location.href = \"/guestbook1_servlet/guestbook/write.html\";");
		out.println("            });");
		out.println("        });");
		out.println("    </script>");
		out.println("</head>");
		out.println("<body>");
		out.println("    <div class=\"container text-center mt-3\">");
		out.println("        <div class=\"col-lg-8 mx-auto\">");
		// 성공적으로 DB 에 insert 가 되었을 시
		if (cnt != 0) {
			out.println("            <div class=\"jumbotron\">");
			out.println("                <h1 class=\"text-primary\">글작성 성공 ^^</h1>");
			out.println("                <p class=\"mt-4\"><button type=\"button\" id=\"mvListBtn\" class=\"btn btn-outline-dark\">글목록 페이지로 이동</button>");
			out.println("                </p>");
			out.println("            </div>");
		// 실패했을 시
		} else {
			out.println("            <div class=\"jumbotron\">");
			out.println("                <h1 class=\"text-danger\">글작성 실패 T.T</h1>");
			out.println("                <p class=\"mt-4\"><button type=\"button\" id=\"mvRegisterBtn\" class=\"btn btn-outline-dark\">글쓰기 페이지로 이동</button>");
			out.println("                </p>");
			out.println("            </div>");
		}
		out.println("        </div>");
		out.println("    </div>");
		out.println("</body>");
		out.println("</html>");
	}

}
