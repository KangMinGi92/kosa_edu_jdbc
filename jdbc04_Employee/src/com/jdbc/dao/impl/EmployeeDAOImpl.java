package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.dao.EmployeeDAO;
import com.jdbc.exception.DuplicateNumException;
import com.jdbc.exception.RecordNotFoundException;
import com.jdbc.vo.Employee;

import config.ServerInfo;

public class EmployeeDAOImpl implements EmployeeDAO {
	private static EmployeeDAOImpl dao = new EmployeeDAOImpl();

	private EmployeeDAOImpl() {
		System.out.println("Singletone Creating...");
	}

	public static EmployeeDAOImpl getInstance() {
		return dao;
	}

	//////////////////// 공통 로직//////////////////////////
	@Override
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("Database Conn......");
		return conn;
	}

	@Override
	public void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(ps, conn);
	}

	// empno에 해당하는 사람이 존재하는지의 여부를 반환하는 기능...
	// selectEmployee(num)
	public boolean isExist(int num, Connection conn) throws SQLException { // pk에 해당하는 사람이 있는지 여부
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT num FROM employee WHERE num=?";
		ps = conn.prepareStatement(query);
		ps.setInt(1, num);
		rs = ps.executeQuery();
		return rs.next();

	}

	//////////////////// 비지니스 로직//////////////////////////
	// 직원 등록 서비스...
	@Override
	public void insertEmployee(Employee emp) throws SQLException, DuplicateNumException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnect();

			if (!isExist(emp.getNum(), conn)) { // 추가하려는 직원이 없다면
				String query = "INSERT INTO employee (num, name, salary, address) VALUES (?,?,?,?)";
				ps = conn.prepareStatement(query);

				ps.setInt(1, emp.getNum());
				ps.setString(2, emp.getName());
				ps.setDouble(3, emp.getSalary());
				ps.setString(4, emp.getAddress());
				System.out.println(ps.executeUpdate() + " 명 등록 성공");
			} else { // 추가하려는 직원이 존재한다면
				throw new DuplicateNumException(emp.getName() + " 님은 이미 등록된 직원입니다.");
			}
		} finally {

			closeAll(ps, conn);
		}
	}

	// 직원이 없다면 RecordNotFoundException 발생...
	@Override
	public void removeEmployee(int num) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnect();
			if (isExist(num, conn)) { // 삭제대상이 있다면
				String query = "DELETE FROM employee WHERE num=?";
				ps = conn.prepareStatement(query);

				ps.setInt(1, num);
				System.out.println(ps.executeUpdate() + " 명 삭제 성공");
			} else {
				throw new RecordNotFoundException("삭제 대상이 존재하지 않습니다.");
			}
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

			String query = "UPDATE employee SET name=?, salary=?, address=? WHERE num=?";
			ps = conn.prepareStatement(query);

			ps.setString(1, emp.getName());
			ps.setDouble(2, emp.getSalary());
			ps.setString(3, emp.getAddress());
			ps.setInt(4, emp.getNum());
			System.out.println(ps.executeUpdate() + " 명 수정 성공");
		} finally {

			closeAll(ps, conn);
		}
	}

	@Override
	public List<Employee> selectEmployee() throws SQLException {
		List<Employee> employees = new ArrayList<Employee>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = getConnect();

			String query = "SELECT num, name, salary, address FROM employee";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();
			while (rs.next())
				employees.add(new Employee(rs.getInt("num"), rs.getString("name"), rs.getDouble("salary"),
						rs.getString("address")));
		} finally {

			closeAll(rs, ps, conn);
		}
		return employees;
	}

	@Override
	public Employee selectEmployee(int num) throws SQLException {
		Employee emp = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT num, name, salary, address FROM employee WHERE num=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			if (rs.next())
				emp = new Employee(rs.getInt("num"), rs.getString("name"), rs.getDouble("salary"),
						rs.getString("address"));
		} finally {
			closeAll(rs, ps, conn);
		}
		return emp;
	}
}