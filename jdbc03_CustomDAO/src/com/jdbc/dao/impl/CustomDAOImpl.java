package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.dao.CustomDAO;
import com.jdbc.vo.Custom;

import config.ServerInfo;

public class CustomDAOImpl implements CustomDAO {
	private static CustomDAOImpl dao = new CustomDAOImpl();

	private CustomDAOImpl() {
		System.out.println("singletone...Creating...");
	}

	public static CustomDAOImpl getInstance() {
		return dao;
	}

	/////////////// 공통로직 ///////////////////
	private Connection getConnect() throws SQLException{
		return DriverManager.getConnection(ServerInfo.URL,ServerInfo.USER,ServerInfo.PASS);
	}

	private void closeAll(PreparedStatement ps, Connection conn) throws SQLException{
		if(ps!=null) ps.close();
		if(conn!=null) conn.close();
		
	}
	private void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException{
		if(rs!=null) rs.close();
		closeAll(ps,conn);
		
	}
	/////////////// 비즈니스로직 ////////////////
	@Override
	public void addCustom(Custom custom) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		/*
		 * 회원가입 1. DB서버 연결 Connection 반환 
		 * 2. 반환받은 Connection을 이용하여 PreparedStatement 이용하여 CREATE 쿼리문 작성 
		 * 3. 값 바인딩 및 쿼리문 실행 
		 * 4. 자원반환
		 */
		try {
			conn=getConnect();
			String query = "INSERT INTO custom(id,name,address) VALUES(?,?,?)";
			ps=conn.prepareStatement(query);
			
			ps.setInt(1, custom.getId());
			ps.setString(2, custom.getName());
			ps.setString(3, custom.getAddress());
			System.out.println(ps.executeUpdate()+" 명 등록성공");
		}finally {
			closeAll(ps,conn);
		}
	}

	@Override
	public void removeCustom(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "DELETE FROM custom WHERE id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			System.out.println(id+"회원번호의 회원정보가"+ps.executeUpdate()+" 개 삭제 되었습니다.");
		}finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void updateCustom(Custom custom) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "UPDATE custom SET name=?,address=? WHERE id=?";
			ps = conn.prepareStatement(query);
			ps.setString(1,custom.getName());
			ps.setString(2, custom.getAddress());
			ps.setInt(3, custom.getId());
			System.out.println(custom.getId()+" 회원번호의 회원정보가"+ps.executeUpdate()+" 개 수정 되었습니다.");
		}finally {
			closeAll(ps, conn);
		}

	}

	@Override
	public Custom getCustom(int id) throws SQLException {
		Custom custom = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT id, name, address FROM custom WHERE id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				custom = new Custom(id,rs.getString("name"),rs.getString("address"));
			}
			
		}finally {
			closeAll(rs, ps, conn);
		}
		return custom;
	}

	@Override
	public List<Custom> getCustom() throws SQLException{
		List<Custom> customers = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT id, name, address FROM custom";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				customers.add(new Custom(rs.getInt("id"),rs.getString("name"),rs.getString("address")));
			}
		}finally {
			closeAll(rs, ps, conn);
		}
		return customers;
	}

}
