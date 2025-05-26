package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.dao.BookDAO;
import com.jdbc.exception.BookNotFoundException;
import com.jdbc.exception.DMLException;
import com.jdbc.exception.DuplicateISBNException;
import com.jdbc.exception.InvalidInputException;
import com.jdbc.vo.Book;

import config.ServerInfo;

public class BookDAOImpl implements BookDAO {
	private static BookDAOImpl dao = new BookDAOImpl();

	private BookDAOImpl() {
		System.out.println("singletone....");
	}

	public static BookDAOImpl getInstance() {
		return dao;
	}

	/////////////////////// 공통로직/////////////////////////////////////
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
	}

	/////////////////////// 비지니스 로직/////////////////////////////////
	@Override
	public void registerBook(Book vo) throws DMLException, DuplicateISBNException {
		/*
		 * 등록할 사람이 있다면 DuplicateISBNException 터지도록 우회한다. try resource with 구문 사용
		 */
		String sql = "INSERT INTO book(isbn,title,writer,publisher,price) VALUES(?,?,?,?,?)";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, vo.getIsbn());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getWriter());
			ps.setString(4, vo.getPublisher());
			ps.setInt(5, vo.getPrice());
			System.out.println(ps.executeUpdate() + "권 등록되었습니다.");
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DuplicateISBNException(vo.getTitle() + "는 이미 등록된 도서입니다.");
		} catch (SQLException e) {
			throw new DMLException("등록 절차중 문제가 생겼습니다. 다시 등록해주세요");
		}
	}

	@Override
	public void deleteBook(String isbn) throws DMLException, BookNotFoundException {
		String sql = "DELETE FROM book WHERE isbn=?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, isbn);
			int result = ps.executeUpdate();
			if (result == 0) {
				throw new BookNotFoundException(isbn + "에 해당하는 책을 찾지 못했습니다.");
			} else {
				System.out.println(result + "권 이 삭제 되었습니다.");
			}
		} catch (SQLException e) {
			throw new DMLException("삭제 절차중 문제가 생겼습니다.");
		}
	}

	@Override
	public Book findByBook(String isbn, String title) throws DMLException {
		Book book = null;
		String sql = "SELECT isbn,title,writer,publisher,price FROM book WHERE isbn=? AND title=?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, isbn);
			ps.setString(2, title);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				book = new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("writer"),
						rs.getString("publisher"), rs.getInt("price"));
				rs.close();
			}
		} catch (SQLException e) {
			throw new DMLException("조회 중 문제가 생겼습니다. 다시 조회해주세요");
		}
		return book;
	}

	@Override
	public ArrayList<Book> findByWriter(String writer) throws DMLException {
		ArrayList<Book> books = new ArrayList<>();
		String sql = "SELECT isbn,title,writer,publisher,price FROM book WHERE writer=?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, writer);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				books.add(new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("writer"),
						rs.getString("publisher"), rs.getInt("price")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DMLException("조회 중 문제가 생겼습니다. 다시 조회해주세요");
		}
		return books;
	}

	@Override
	public ArrayList<Book> findByPublisher(String publisher) throws DMLException {
		ArrayList<Book> books = new ArrayList<>();
		String sql = "SELECT isbn,title,writer,publisher,price FROM book WHERE publisher=?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, publisher);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				books.add(new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("writer"),
						rs.getString("publisher"), rs.getInt("price")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DMLException("조회 중 문제가 생겼습니다. 다시 조회해주세요");
		}
		return books;
	}

	// InvalidInputException를 에러 잡을때는 java에서 애초에 잘못된값이 들어가는걸 막아서 처리하는 방법
	// 또는 쿼리문 던져서 no record를 가져왔을때 유효하지 않은값을 가져왔다고 예외러 던지는 경우 방법이 있다.
	@Override
	public ArrayList<Book> findByPrice(int min, int max) throws DMLException, InvalidInputException {
		if (min < 0 || max < 0 || min > max) {
			throw new InvalidInputException("가격 범위가 잘못되었습니다.");
		}
		ArrayList<Book> books = new ArrayList<>();
		String sql = "SELECT isbn,title,writer,publisher,price FROM book WHERE price BETWEEN ? AND ?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, min);
			ps.setInt(2, max);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				books.add(new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("writer"),
						rs.getString("publisher"), rs.getInt("price")));
			}
			if (books.isEmpty()) {
				throw new InvalidInputException("해당하는 데이터가 없습니다.");
			}
			rs.close();
		} catch (SQLException e) {
			throw new DMLException("조회 중 문제가 생겼습니다. 다시 조회해주세요");
		}
		return books;
	}

	@Override
	// 10% 면 10이 들어와서 0.1로 처리
	public void discountBook(int per, String publisher) throws DMLException {
		String sql = "UPDATE book SET price=price*(100-?)/100 WHERE publisher = ?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, per);
			ps.setString(2,publisher);
			int result = ps.executeUpdate();
			System.out.println(publisher+" 출판사에 할인율"+per+"%가 총"+result+"권 적용 되었습니다.");
		} catch (SQLException e) {
			throw new DMLException("할인 적용하는데 문제가 생겼습니다. 다시 적용해주세요");
		}
	}

} //BookDAO
