package com.ssafy.guestbook.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.guestbook.dto.MemberDto;
import com.ssafy.util.DBUtil;


@WebServlet("/user")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DBUtil dbUtil = DBUtil.getInstance();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String root = request.getContextPath();
		String act = request.getParameter("act");
		
		String path = "/index.jsp";
		
		if ("register".equals(act)) {
			path = registerMamber(request, response);
			response.sendRedirect(root + path);
		} else if ("login".equals(act)) {
			path = loginMember(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} else if ("logout".equals(act)) {
			path = logoutMember(request, response);
			response.sendRedirect(root + path);
		} else {
			response.sendRedirect(root + path);
		}
	}


	private String logoutMember(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		//session.removeAttribute("userinfo");
		session.invalidate();
		return "/index.jsp";
	}


	private String loginMember(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("userid");
		String pass = request.getParameter("userpwd");
		
		MemberDto memberDto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			String sql = "select userid, username, email \n";
			sql += "from ssafy_member \n";
			sql += "where userid = ? and userpwd = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberDto = new MemberDto();
				memberDto.setUserid(id);
				memberDto.setUserName(rs.getString("username"));
				memberDto.setEmail(rs.getString("email"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		
		//request.setAttribute("userinfo", memberDto);
		if (memberDto != null) { // 로그인 성공
			HttpSession session = request.getSession();
			session.setAttribute("userinfo", memberDto);
		}
		
		return "/index.jsp";
	}


	private String registerMamber(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("userid");
		String name = request.getParameter("username");
		String pass = request.getParameter("userpwd");
		String email = request.getParameter("emailid") + "@" + request.getParameter("emaildomain");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			String sql = "insert into ssafy_member (userid, username, userpwd, email, joindate) \n";
			sql += "values(?, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, pass);
			pstmt.setString(4, email);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
		return "/user/login.jsp";
	}
}
