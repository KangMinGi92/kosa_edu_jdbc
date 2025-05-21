package com.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.ServerInfo;

public class CustomDAO {

	public CustomDAO() throws SQLException {
		// DB서버 연결
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("서버 연결...");

		// PreparedStatement 생성
		String query = "INSERT INTO custom(id, name, address) VALUES(?,?,?)";
		PreparedStatement ps = conn.prepareStatement(query);

		// 쿼리문 실행
		ps.setInt(1, 5);
		ps.setString(2, "염혜경");
		ps.setString(3, "제주 애월읍");
		System.out.println(ps.executeUpdate() + " 명 등롱 성공!!");

	}
}
