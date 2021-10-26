package com.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dto.Board;
import com.board.service.BoardService;
import com.board.service.BoardServiceImpl;
import com.google.gson.Gson;

//비동기 요청에 대한 답을 만들어 결과만 전송
public class BoardAjaxController {
	BoardService service;
	
	public BoardAjaxController() {
		service = new BoardServiceImpl();
	}
	
	public void ajax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ArrayList<Board> list = service.selectAll();
		
		// Java 객체 (ArrayList) 를 javascript 에서 알아듣도록 JSON 으로 변환시켜주는 과정이 필요
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Board b : list) {
			Gson gs = new Gson();
			sb.append(gs.toJson(b) + ","); // java 객체를 json 형식의 문자열로 변환
		}
		sb.setLength(sb.length() - 1);
		sb.append("]");
		out.print(sb);
	}
	
	public void ajax2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 23번
		Board b = service.selectOne("23");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		Gson gs = new Gson();
		String s = gs.toJson(b); // java 객체를 json 형식의 문자열로 변환
		out.println(s);
	}

}
