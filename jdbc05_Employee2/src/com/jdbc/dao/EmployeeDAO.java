package com.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jdbc.exception.DMLException;
import com.jdbc.exception.DuplicateNumException;
import com.jdbc.exception.RecordNotFoundException;
import com.jdbc.vo.Employee;

public interface EmployeeDAO {
	Connection getConnect() throws SQLException;

	void closeAll(PreparedStatement ps, Connection conn) throws SQLException;

	void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException;

	void insertEmployee(Employee emp) throws DuplicateNumException,DMLException;

	void removeEmployee(int num) throws RecordNotFoundException,DMLException;

	void updateEmployee(Employee emp) throws SQLException;

	List<Employee> selectEmployee() throws SQLException;

	Employee selectEmployee(int num) throws SQLException;
}