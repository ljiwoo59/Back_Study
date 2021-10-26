package com.board.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.dto.Board;
import com.board.service.BoardService;
import com.board.service.BoardServiceImpl;

// FrontController 에게서 요청을 전달 받아 Service 에게 작업을 넘김
public class BoardController {
	BoardService service; // interface 타입으로 선언
	
	public BoardController() {
		service = new BoardServiceImpl();
	}

	public void list(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Board> list = service.selectAll();
		request.setAttribute("list", list);
		
		// jsp 로 넘어가기 (forward 방식)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/list.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void read(HttpServletRequest request, HttpServletResponse response) {
		String num = request.getParameter("num");
		Board b = service.selectOne(num);
		request.setAttribute("b", b);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/read.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 새글 입력을 위한 화면만 보내주면 된다
	public void insertForm(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/insertForm.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertProcess(HttpServletRequest request, HttpServletResponse response) {
		// 0. 한글 처리
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		// 1. 사용자가 입력한 값 받아오기
		String title = request.getParameter("title");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		String content = request.getParameter("content");
		
		// 2. service 에게 넘겨주기
		service.insert(new Board(pass, name, title, content));
		
		// 3. 초기화면으로 바로 넘어가기
		try {
			response.sendRedirect("list.bod");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		String num = request.getParameter("num");
		service.delete(num);
		try {
			response.sendRedirect("list.bod");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void loginForm(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/loginForm.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loginProcess(HttpServletRequest request, HttpServletResponse response) {
		// 1. 사용자가 입력한 값 받기 -> db
		String id = request.getParameter("id");
		// 2. 세션에 ID 저장
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		// 3. 초기 화면으로 redirect
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.bod");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute("id", null);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.bod");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String condition = request.getParameter("condition");
		String word = request.getParameter("word");
	
		ArrayList<Board> searched = service.search(condition, word);
		request.setAttribute("searched", searched);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/search.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void modify(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
}
