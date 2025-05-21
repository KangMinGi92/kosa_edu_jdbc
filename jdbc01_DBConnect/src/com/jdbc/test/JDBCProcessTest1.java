package com.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 DAO(Database Access Object
 Java 프로그램을 통해서 DMBS에 접속
 메소드를 통해서 SQL이 실행되도록 한다.
 
 1. MySQL Driver를 해당 클래스 메모리에 로딩
 2. DB서버 접속
 	접속 성공하면 Connection 객체를 반환받고
 	이후 작업은 Connection 
 	jdbc:mysql://127.0.0.1:3306/kosa?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8
 3. 쿼리문을 실행
 	executeUpdate() --- DML(INSERT/DELETE/UPDATE)
 	executeQuery() --- SELECT
 	
 	함수를 PrepareedStatement가 제공
 */
public class JDBCProcessTest1 {

	public static void main(String[] args) {
		// 1. 드라이버 로딩
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// FQCN(FullNameClass)
			System.out.println("Driver Loading....Success");

		// 2. 디비서버 연결
			String url = "jdbc:mysql://127.0.0.1:3306/kosa?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
			Connection conn = DriverManager.getConnection(url, "root", "1234");
			System.out.println("DB Connect....");
		//3. PreparedStatement 생성
			PreparedStatement ps=conn.prepareStatement("DELETE FROM custom WHERE id=3");
			System.out.println("PreparedStatement Creating....");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Loading....Fail....");
		} catch (SQLException e) {
			System.out.println("DB Connect....Fail....");
			System.out.println("PreparedStatement Fail....");
		}

	}

}
