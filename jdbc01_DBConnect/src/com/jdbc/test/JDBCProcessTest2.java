package com.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 1. 드라이버 로딩
 2. DB연결 후 Connection객체 반환 받는다.
 3. PreparedStatement 생성
 4. 쿼리문 실행
 	1) ?에 해당하는 부분에 각각 값을 바인딩
 	2) executeUpdate() | executeQuery()로 실행
 	
 ------------------------------------------------
 
 	해당코드는 서버에 관련된 실제 정보가 코드에 그대로 노출되어져 있다.
 	프로그램에서 사용되고 있는 상수값은 별도로 분리해야 보안측면에서나 재사용성에서나
 	더 좋은 코드를 만들 수 있다.
 	
 	파일로 분리  -----> properties 파일
 	
 				Map
 				 |
 		-----------------	
 		|				|
 	HashMap			HashTable
 						|
 					Properties
 			(String key - String value)
 */
public class JDBCProcessTest2 {
	public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/kosa?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	public static final String USER = "root";
	public static final String PASS = "1234";

	public JDBCProcessTest2() {
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("DB서버 연결 성공...");

			String query = "ISERT INTO custom(id, name, address) VALUES(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			System.out.println("PreparedStatement 생성...");

			ps.setInt(1, 4);
			ps.setString(2, "아이유");
			ps.setString(3, "종로");
			int row = ps.executeUpdate();
			System.out.println(row + " ROW INSERT OK~~!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new JDBCProcessTest2();
	}// main

	static {
		try {
			Class.forName(DRIVER_NAME);
			System.out.println("드라이버 로딩 성공...");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패...");
		}
	}
}
