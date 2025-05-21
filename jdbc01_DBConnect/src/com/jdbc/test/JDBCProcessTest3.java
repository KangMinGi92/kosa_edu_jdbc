package com.jdbc.test;

import java.sql.SQLException;

import com.jdbc.dao.CustomDAO;

import config.ServerInfo;

public class JDBCProcessTest3 {

	public static void main(String[] args) {
		try {
			new CustomDAO();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}// main

	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩...");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
