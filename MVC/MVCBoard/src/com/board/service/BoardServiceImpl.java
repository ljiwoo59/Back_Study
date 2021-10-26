package com.board.service;

import java.util.*;

import com.board.dao.BoardDao;
import com.board.dao.BoardDaoImpl;
import com.board.dto.Board;

// Controller 에게서 작업을 전달 받아 Dao 에게 작업을 넘김
// transaction 관리
public class BoardServiceImpl implements BoardService {

	BoardDao dao; // 인터페이스 타입
	public BoardServiceImpl() {
		dao = new BoardDaoImpl();
	}
	
	@Override
	public ArrayList<Board> selectAll() {
		return dao.selectAll();
	}

	@Override
	public Board selectOne(String num) {
		dao.countUp(num);
		return dao.selectOne(num);
	}

	@Override
	public void insert(Board b) {
		dao.insert(b);
	}

	@Override
	public void delete(String num) {
		dao.delete(num);
	}

	@Override
	public ArrayList<Board> search(String condition, String word) {
		return dao.search(condition, word);
	}

}
