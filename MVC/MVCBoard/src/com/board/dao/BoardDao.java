package com.board.dao;

import java.util.*;
import com.board.dto.Board;

// BoardServiceImpl 를 위한 인터페이스
public interface BoardDao {
	ArrayList<Board> selectAll();
	Board selectOne(String num);
	void countUp(String num); // 해당 번호의 글 조회수를 증가시키는 메소드
	void insert(Board b);
	void delete(String num);
	ArrayList<Board> search(String condition, String word);
}
