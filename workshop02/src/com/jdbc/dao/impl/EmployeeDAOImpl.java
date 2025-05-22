package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.dao.EmployeeDAO;
import com.jdbc.vo.Employee;

import config.ServerInfo;

public class EmployeeDAOImpl implements EmployeeDAO {
	private static EmployeeDAOImpl dao = new EmployeeDAOImpl();

	private EmployeeDAOImpl() {

	}

	public static EmployeeDAOImpl getInstance() {
		return dao;
	}

	private Connection getConnect() throws SQLException {
		return DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
	}

	private void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}

	private void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(ps, conn);
	}

	@Override
	public void insertEmployee(Employee emp) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "INSERT INTO employee(num,name,salary,address) VALUES(?,?,?,?)";
			ps = conn.prepareStatement(query);

			ps.setInt(1, emp.getNum());
			ps.setString(2, emp.getName());
			ps.setDouble(3, emp.getSalary());
			ps.setString(4, emp.getAddress());

			System.out.println(ps.executeUpdate() == 1 ? "추가 성공" : "추가 실패");
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void removeEmployee(int num) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "DELETE FROM employee WHERE num = ?";
			ps = conn.prepareStatement(query);

			ps.setInt(1, num);

			System.out.println(ps.executeUpdate() == 1 ? "삭제 성공" : "삭제 실패");
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void updateEmployee(Employee emp) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "UPDATE employee SET name = ?, salary = ?, address = ? WHERE num = ?";
			ps = conn.prepareStatement(query);

			ps.setString(1, emp.getName());
			ps.setDouble(2, emp.getSalary());
			ps.setString(3, emp.getAddress());
			ps.setInt(4, emp.getNum());

			System.out.println(ps.executeUpdate() == 1 ? "업데이트 성공" : "업데이트 실패");

		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public Employee selectEmployee(int num) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee employee = null;
		try {
			conn = getConnect();
			String query = "SELECT num, name, salary, address FROM employee WHERE num = ?";
			ps = conn.prepareStatement(query);

			ps.setInt(1, num);

			rs = ps.executeQuery();
			if (rs.next())
				employee = new Employee(num, rs.getString("name"), rs.getDouble("salary"), rs.getString("address"));
		} finally {
			closeAll(rs, ps, conn);
		}
		return employee;
	}

	@Override
	public List<Employee> selectEmployee() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Employee> employees = new ArrayList<>();
		try {
			conn = getConnect();
			String query = "SELECT num, name, salary, address FROM employee";
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				employees.add(new Employee(rs.getInt("num"),rs.getString("name"),rs.getDouble("salary"),rs.getString("address")));
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		return employees;
	}

}
