package com.jdbc.dao;

/*
 	1. 드라이버 로딩
 	2. DB서버 연결(getConnection() ... Connection 반환
 	3. PreparedStatement 생성
 	4. 값 바인딩 및 SQL문 시행
 			+
 		  자원반납 (자원을 연 순서 역순으로 닫아준다.)
 		finally
 		rs.close();
		ps.close();
		conn.close(); 위의 2가지는 안닫아도 문제가 되지 않지만, conn는 항상 닫아줘야한다.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public CustomDAO(String driver, String url, String user, String pass) throws Exception {
		try {
			// 1. 드라이버 로딩
			Class.forName(driver);
			System.out.println("드라이버 로딩 성공");

			// 2. DB서버 연결(getConnection() ... Connection 반환
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("DB서버 연결 성공");

			// 3. PreparedStatement 생성
			String query = "SELECT id, name, address FROM custom";
			ps = conn.prepareStatement(query);

			// 4. SQL문 시행
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getString("address"));
			}
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}

	}
}
