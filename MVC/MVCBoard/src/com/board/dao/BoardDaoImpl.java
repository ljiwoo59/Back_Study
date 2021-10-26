package com.board.dao;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.board.dto.Board;

// dao 객체 : CRUD
// Service 에서 작업을 전달 받아 DB 작업 수행
public class BoardDaoImpl implements BoardDao {
	// Connection Pool 을 찾아서 빌리고 반납하고
	DataSource ds;
	
	public BoardDaoImpl() {
		// tomcat 이 만들어 놓은 pool 을 찾아다 놓기
		// JNDI (Java Naming & Directory Interface)
		// 공유 자원에 대해 이름을 붙여 놓고 필요할 때 그 이름으로 자원을 찾아 이용하는 기술
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Board> selectAll() {
		ArrayList<Board> list = new ArrayList<>();
		
		try {
			String query = "select num, name, wdate, title, count from board order by num desc";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        Statement stat = con.createStatement();
	        ResultSet rs = stat.executeQuery(query);
	        
	        while (rs.next()) {
	        	String num = rs.getString(1);
	        	String name = rs.getString(2);
	        	String wdate = rs.getString(3);
	        	String title = rs.getString(4);
	        	String count = rs.getString(5);
	        	
	        	Board b = new Board(num, null, name, wdate, title, null, count);
	        	list.add(b);
	        }
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Board selectOne(String num) {
		Board b = null;
		try {
			String query = "select name, wdate, title, count, content from board where num = ?";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        PreparedStatement stat = con.prepareStatement(query);
	        stat.setString(1, num);
	        
	        ResultSet rs = stat.executeQuery();
	        rs.next();
        	String name = rs.getString(1);
        	String wdate = rs.getString(2);
        	String title = rs.getString(3);
        	String count = rs.getString(4);
        	String content = rs.getString(5);
        	
        	b = new Board(num, null, name, wdate, title, content, count);

        	con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public void countUp(String num) {
		try {
			String query = "update board set count = count + 1 where num = ?";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        PreparedStatement stat = con.prepareStatement(query);
	        stat.setString(1, num);
	        stat.executeUpdate();
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Board b) {
		try {
			String query = "insert into board(pass, name, wdate, title, content, count)";
			query += "values(?, ?, sysdate(), ?, ?, 0)";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        PreparedStatement stat = con.prepareStatement(query);
	        stat.setString(1, b.getPass());
	        stat.setString(2, b.getName());
	        stat.setString(3, b.getTitle());
	        stat.setString(4, b.getContent());
	        stat.executeUpdate();
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String num) {
		try {
			String query = "delete from board where num = ?";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        PreparedStatement stat = con.prepareStatement(query);
	        stat.setString(1, num);
	        stat.executeUpdate();
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Board> search(String condition, String word) {
		ArrayList<Board> searched = new ArrayList<>();
		try {
			String query = "";
			if (condition.equals("title"))
				query = "select num, name, wdate, title, count from board where title like ?";
			
			else
				query = "select num, name, wdate, title, count from board where name like ?";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
			PreparedStatement stat = con.prepareStatement(query);
	        word = "%" + word + "%";
	        stat.setString(1, word);
	        ResultSet rs = stat.executeQuery();
	        
	        while (rs.next()) {
	        	String num = rs.getString(1);
	        	String name = rs.getString(2);
	        	String wdate = rs.getString(3);
	        	String title = rs.getString(4);
	        	String count = rs.getString(5);
	        	
	        	Board b = new Board(num, null, name, wdate, title, null, count);
	        	searched.add(b);
	        }
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
		return searched;
	}

}
