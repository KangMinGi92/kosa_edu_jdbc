package com.jdbc.dao;

import java.sql.SQLException;
import java.util.List;

import com.jdbc.vo.Employee;

public interface EmployeeDAO {
	
	void insertEmployee(Employee emp) throws SQLException;
	void removeEmployee(int num) throws SQLException;
	void updateEmployee(Employee emp) throws SQLException;
	Employee selectEmployee(int num) throws SQLException;
	List<Employee> selectEmployee() throws SQLException;
}
