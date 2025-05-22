package com.jdbc.test;

import java.io.FileInputStream;
import java.util.Properties;

import com.jdbc.dao.CustomDAO;

public class CustomDAOTest {
	public static String DRIVER_NAME;
	public static String URL;
	public static String USER;
	public static String PASS;

	public static void main(String[] args) {
		try {
			new CustomDAO(DRIVER_NAME, URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// properties 모든 갓을 로드하고 자바코드가 본격적으로 실행되도록 한다.
	static {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("src/config/db.properties"));
			DRIVER_NAME = p.getProperty("jdbc.mysql.driver");
			URL = p.getProperty("jdbc.mysql.url");
			USER = p.getProperty("jdbc.mysql.user");
			PASS = p.getProperty("jdbc.mysql.pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
