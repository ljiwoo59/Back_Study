package com.board.front;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.controller.BoardAjaxController;
import com.board.controller.BoardController;

// FrontController : 클라이언트로부터 들어오는 요청 받기
// 받은 요청을 구분해서 Controller 에게 작업을 넘김
@WebServlet("*.bod")
public class BoardFrontController extends HttpServlet {
	BoardController con;// 동기 요청 처리 컨트롤러
	BoardAjaxController acon;// 비동기 요청 처리 컨트롤러
	
	public BoardFrontController() { // by tomcat
		con = new BoardController();
		acon = new BoardAjaxController();
	}
	
	// http://localhost:8080/board/list.bod
	private void process(HttpServletRequest request, HttpServletResponse response) {
		String reqString = request.getServletPath();
		System.out.println(reqString);
		
		// jsp 로 포워드하여 화면이 바뀜
		if (reqString.equals("/list.bod")) { // 초기화면 요청
			con.list(request, response);
		} else if (reqString.equals("/read.bod")) { // 읽기화면 요청
			con.read(request, response);
		} else if (reqString.equals("/insertForm.bod")) { // 입력화면 요청
			con.insertForm(request, response);
		} else if (reqString.equals("/insertProcess.bod")) { // db 에 입력 요청
			con.insertProcess(request, response);
		} else if (reqString.equals("/delete.bod")) { // db 에 삭제 요청
			con.delete(request, response);
		} else if (reqString.equals("/loginForm.bod")) { // 로그인 요청
			con.loginForm(request, response);
		} else if (reqString.equals("/loginProcess.bod")) { // 로그인 처리 요청
			con.loginProcess(request, response);
		} else if (reqString.equals("/logout.bod")) { // 로그아웃 요청
			con.logout(request, response);
		} else if (reqString.equals("/search.bod")) { // 검색 요청
			con.search(request, response);
		} else if (reqString.equals("/modify.bod")) { // 수정 요청
			con.modify(request, response);
		
		// jsp 로 화면이 바뀌는 것이 아니라 데이터만 받아 있는 화면에 뿌림
		} else if (reqString.equals("/ajax.bod")) { // ajax 요청 (비동기 처리: 현재 화면 그대로 데이터를 받아 뿌려줌)
			try {
				acon.ajax(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
}
