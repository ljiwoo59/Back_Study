package com.ssafy.guestbook.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.guestbook.dto.GuestBookDto;
import com.ssafy.guestbook.dto.MemberDto;
import com.ssafy.util.DBUtil;

@WebServlet("/guestbook")
public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DBUtil dbUtil = DBUtil.getInstance();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String act = request.getParameter("act");
		String path = "/index.jsp";
		if ("register".equals(act)) {
			path = registerArticle(request, response);
		} else if ("list".equals(act)) {
			path = listArticle(request, response);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	private String registerArticle(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if (memberDto != null) {
			String userid = request.getParameter("userid");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
	
			Connection conn = null;
			PreparedStatement pstmt = null;
			int cnt = 0;
			try {
				conn = dbUtil.getConnection();
				StringBuilder registerArticle = new StringBuilder();
				registerArticle.append("insert into guestbook (userid, subject, content, regtime) \n");
				registerArticle.append("values (?, ?, ?, now())");
				pstmt = conn.prepareStatement(registerArticle.toString());
				pstmt.setString(1, memberDto.getUserid());
				pstmt.setString(2, subject);
				pstmt.setString(3, content);
				cnt = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbUtil.close(pstmt, conn);
			}
			return cnt != 0 ? "/guestbook/writesuccess.jsp" : "/guestbook/writefail.jsp";
		} else {
			return "/user/login.jsp";
		}
	}

	private String listArticle(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		if (memberDto != null) {
			List<GuestBookDto> list = new ArrayList<GuestBookDto>();
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
					GuestBookDto guestBookDto = new GuestBookDto();
					guestBookDto.setArticleNo(rs.getInt("articleno"));
					guestBookDto.setUserId(rs.getString("userid"));
					guestBookDto.setSubject(rs.getString("subject"));
					guestBookDto.setContent(rs.getString("content"));
					guestBookDto.setRegTime(rs.getString("regtime"));
					
					list.add(guestBookDto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbUtil.close(rs, pstmt, conn);
			}
			
			request.setAttribute("articles", list);
			
			return "/guestbook/list.jsp";
		} else {
			return "/user/login.jsp";
		}
	}

}
