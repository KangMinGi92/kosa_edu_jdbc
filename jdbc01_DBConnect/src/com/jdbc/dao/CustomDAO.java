package com.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.ServerInfo;

public class CustomDAO {

	public CustomDAO() throws SQLException {
		// DB서버 연결
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("서버 연결...");

		// PreparedStatement 생성
		//CREATE
		/*
		String query = "INSERT INTO custom(id, name, address) VALUES(?,?,?)";
		PreparedStatement ps = conn.prepareStatement(query);

		// 쿼리문 실행
		ps.setInt(1, 5);
		ps.setString(2, "염혜경");
		ps.setString(3, "제주 애월읍");
		System.out.println(ps.executeUpdate() + " 명 등롱 성공!!");
		*/
		
		//DELETE
		/*
		String query = "DELETE FROM custom WHERE id=?";
		PreparedStatement ps=conn.prepareStatement(query);
		ps.setInt(1, 4);
		System.out.println(ps.executeUpdate()+" 명 삭제 성공!!");
		*/
		
		//UPDATE
		/*
		String query = "UPDATE custom SET name=?, address=? WHERE id=?";
		PreparedStatement ps=conn.prepareStatement(query);
		ps.setString(1, "김혜경");
		ps.setString(2, "서울 종로");
		ps.setInt(3, 5);
		System.out.println(ps.executeUpdate()+" 명 수정 성공!!");
		*/
		
		//A Custom
		String query = "SELECT id,name,address FROM custom WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, 1);
		ResultSet rs=ps.executeQuery();
		System.out.println("--------------특정 고객 정보------------------");
		if(rs.next()) // 1번에 해당하는 고객이 있다면
			System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));
		
		//All Custom
		String allQuery = "SELECT id,name,address FROM custom";
		PreparedStatement ps2 = conn.prepareStatement(allQuery);
		ResultSet rs2 = ps2.executeQuery();
		System.out.println("--------------전체 고객 정보------------------");
		while(rs2.next()) // 1번에 해당하는 고객이 있다면
			System.out.println(rs2.getInt("id")+"\t"+rs2.getString("name")+"\t"+rs2.getString("address"));
	}//생성자
}
