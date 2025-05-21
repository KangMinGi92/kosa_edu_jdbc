package com.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.ServerInfo;

public class ProductDAO {
	
	public ProductDAO() throws SQLException {
		String query="INSERT INTO product(no, pname, maker, price) VALUES(?,?,?,?)";
		PreparedStatement ps=add(query);
		ps.setInt(1, 1);
		ps.setString(2, "아메리카노");
		ps.setString(3, "메가커피");
		ps.setDouble(4, 2000.0);
		System.out.println(ps.executeUpdate()+" 개의 상품추가 성공!!");
		
		PreparedStatement ps1=add(query);
		ps1.setInt(1, 2);
		ps1.setString(2, "카페라떼");
		ps1.setString(3, "메가커피");
		ps1.setDouble(4, 3400.0);
		System.out.println(ps1.executeUpdate()+" 개의 상품추가 성공!!");
		
		PreparedStatement ps2=add(query);
		ps2.setInt(1, 3);
		ps2.setString(2, "카라멜마끼아또");
		ps2.setString(3, "이디야");
		ps2.setDouble(4, 4000.0);
		System.out.println(ps2.executeUpdate()+" 개의 상품추가 성공!!");
		
		PreparedStatement ps3=add(query);
		ps3.setInt(1, 4);
		ps3.setString(2, "자바칩프라푸치노");
		ps3.setString(3, "스타벅스");
		ps3.setDouble(4, 5000.0);
		System.out.println(ps3.executeUpdate()+" 개의 상품추가 성공!!");
	}
	private PreparedStatement add(String str) throws SQLException{
		Connection conn=DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		return conn.prepareStatement(str);
	}
}
