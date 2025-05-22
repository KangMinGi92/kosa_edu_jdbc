package com.jdbc.test;

import java.sql.SQLException;

import com.jdbc.dao.impl.CustomDAOImpl;
import com.jdbc.vo.Custom;

import config.ServerInfo;

public class CustomDAOTest {

	public static void main(String[] args) {
		CustomDAOImpl dao = CustomDAOImpl.getInstance();
		try {
//			dao.addCustom(new Custom(6, "김선호", "대전"));
//			dao.removeCustom(6);
//			dao.updateCustom(new Custom(6, "강민기","강남구"));
			System.out.println(dao.getCustom(6)+"\n");
			
			dao.getCustom().stream().forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
