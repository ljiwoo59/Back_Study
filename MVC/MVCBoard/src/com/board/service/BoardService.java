package com.board.service;

import java.util.*;
import com.board.dto.Board;

// Controller 를 위한 인터페이스
public interface BoardService {
	ArrayList<Board> selectAll(); // board 테이블의 모든 레코드를 리턴해주는 메소드
	Board selectOne(String num); // 해당 번호의 레코드를 리턴해주는 메소드
	void insert(Board b);
	void delete(String num);
	ArrayList<Board> search(String condition, String word);
}
