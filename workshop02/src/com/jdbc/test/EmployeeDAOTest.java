package com.jdbc.test;

import java.sql.SQLException;

import com.jdbc.dao.impl.EmployeeDAOImpl;
import com.jdbc.vo.Employee;

import config.ServerInfo;

public class EmployeeDAOTest {
	

	public static void main(String[] args) {
		EmployeeDAOImpl dao = EmployeeDAOImpl.getInstance();
		try {
//			dao.insertEmployee(new Employee(1,"강민기",3000.034,"강남"));
//			dao.insertEmployee(new Employee(2,"우승환",5030.015,"강북"));
//			dao.insertEmployee(new Employee(3,"박성우",6000.064,"송파"));
//			dao.insertEmployee(new Employee(4,"이윤열",4000.051,"잠실"));
//			dao.insertEmployee(new Employee(5,"장영민",5000.042,"이태원"));
			
//			dao.removeEmployee(1);
//			dao.removeEmployee(2);
//			dao.removeEmployee(3);
//			dao.removeEmployee(4);
//			dao.removeEmployee(5);
//			dao.updateEmployee(new Employee(1,"홍길동",4000.0,"강북구"));
			System.out.println(dao.selectEmployee(1)+"\n");
			
			dao.selectEmployee().stream().forEach(e->System.out.println(e));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	} //main
	
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
