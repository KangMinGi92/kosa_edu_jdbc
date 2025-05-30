package broker.twotier.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import broker.twotier.exception.DuplicateSSNException;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.SharesRec;
import broker.twotier.vo.StockRec;
import config.ServerInfo;

public class Database implements DatabaseTemplate {
	private static Database database = new Database("127.0.0.1");

	private Database(String serverIp) {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩 성공...");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Database getInstance() {
		return database;
	}

	private Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("DB Connecting....");
		return conn;
	}

	// DML 쿼리문일때 사용할 자원 반납 메소드
	private void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();

	}

	// SELECT 쿼리문일때 사용할 자원 반납 메소드
	private void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(ps, conn);

	}

	//////////////////////////// 존재 유무 확인 //////////////////////////
	public boolean isExist(String ssn, Connection conn) throws SQLException {
		String query = "SELECT ssn FROM customer WHERE ssn=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, ssn);
		ResultSet rs = ps.executeQuery();
		return rs.next(); // ssn이 있으면 true | 없으면 false
	}

	///////////////////////////// 비지니스 로직 ///////////////////////////
	@Override
	public void addCustomer(CustomerRec cust) throws SQLException, DuplicateSSNException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if (!isExist(cust.getSsn(), conn)) { // 추가하려는 ssn이 없다면
				String query = "INSERT INTO customer(ssn, cust_name, address) VALUES(?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, cust.getSsn());
				ps.setString(2, cust.getName());
				ps.setString(3, cust.getAddress());

				System.out.println(ps.executeUpdate() + " 명 INSERT 성공... addCustomer()");
			} else {
				throw new DuplicateSSNException("추가하려는 고객은 이미 등록된 상태입니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void deleteCustomer(String ssn) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if (isExist(ssn, conn)) { // 삭제하려는 ssn이 있다면
				String query = "DELETE FROM customer WHERE ssn=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, ssn);
				System.out.println(ps.executeUpdate() + " 명 DELETE 성공... deleteCustomer()");
			} else {
				throw new RecordNotFoundException("삭제하려는 대상이 없습니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void updateCustomer(CustomerRec cust) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			if (isExist(cust.getSsn(), conn)) { // 수정하려는 ssn이 있다면
				String query = "UPDATE customer SET cust_name=?,address=? WHERE ssn=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, cust.getName());
				ps.setString(2, cust.getAddress());
				ps.setString(3, cust.getSsn());
				System.out.println(ps.executeUpdate() + " 명 Update 성공... updateCustomer()");
			} else {
				throw new RecordNotFoundException("수정하려는 대상이 없습니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public ArrayList<SharesRec> getPortfolio(String ssn) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SharesRec> portfolio = new ArrayList<>();
		try {
			conn = getConnect();
			if (isExist(ssn, conn)) { // 조회하려는 ssn이 있다면
				String query = "SELECT ssn,symbol,quantity FROM shares WHERE ssn=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, ssn);
				rs = ps.executeQuery();
				while (rs.next()) {
					portfolio.add(new SharesRec(rs.getString("ssn"), rs.getString("symbol"), rs.getInt("quantity")));
				}
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		return portfolio;
	}

	@Override
	public CustomerRec getCustomer(String ssn) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CustomerRec customer = null;
		try {
			conn = getConnect();
			if (isExist(ssn, conn)) { // 조회하려는 ssn이 있다면
				String query = "SELECT ssn,cust_name,address FROM customer WHERE ssn=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, ssn);
				rs = ps.executeQuery();

				if (rs.next()) {
					customer = new CustomerRec(rs.getString("ssn"), rs.getString("cust_name"), rs.getString("address"),
							getPortfolio(ssn));
				}
			} else {
				throw new SQLException("조회하는 대상이 없습니다.");
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		return customer;
	}

	@Override
	public ArrayList<CustomerRec> getAllCustomers() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CustomerRec> customers = new ArrayList<>();
		try {
			conn = getConnect();
			String query = "SELECT ssn,cust_name,address FROM customer";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				customers.add(new CustomerRec(rs.getString("ssn"), rs.getString("cust_name"), rs.getString("address"),
						getPortfolio(rs.getString("ssn"))));
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		return customers;
	}

	@Override
	public ArrayList<StockRec> getAllStocks() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<StockRec> stocks = new ArrayList<>();
		try {
			conn = getConnect();
			String query = "SELECT symbol,price FROM stock";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				stocks.add(new StockRec(rs.getString("symbol"), rs.getFloat("price")));
			}
		} finally {
			closeAll(rs, ps, conn);
		}
		return stocks;
	}

	@Override
	public void buyShares(String ssn, String symbol, int quantity) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		int checkQuantity = checkShares(ssn, symbol);
		try {
			conn = getConnect();
			if (checkQuantity != 0) {
				String query = "UPDATE shares SET quantity=? WHERE ssn=? AND symbol=?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, quantity);
				ps.setString(2, ssn);
				ps.setString(3, symbol);
				System.out.println(ps.executeUpdate() == 1 ? symbol + "주식" + quantity + "매수하셨습니다." : "매수 실패하셨습니다.");
			} else {
				String query = "INSERT INTO shares(ssn, symbol, quantity) VALUES(?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, ssn);
				ps.setString(2, symbol);
				ps.setInt(3, quantity);
				System.out.println(ps.executeUpdate() == 1 ? symbol + "주식" + quantity + "매수하셨습니다." : "매수 실패하셨습니다.");
			}
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void sellShares(String ssn, String symbol, int quantity)
			throws SQLException, InvalidTransactionException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		int checkQuantity = checkShares(ssn, symbol);
		if (checkQuantity != 0) {
			try {
				conn = getConnect();
				if (checkQuantity > quantity) {
					String query = "UPDATE shares SET quantity=? WHERE ssn=? AND symbol=?";
					ps = conn.prepareStatement(query);
					ps.setInt(1,checkQuantity-quantity);
					ps.setString(2, ssn);
					ps.setString(3, symbol);
					System.out.println(ps.executeUpdate() == 1 ? symbol + "주식" + quantity + "매도하셨습니다." : "매도 실패하셨습니다.");
				} else if (checkQuantity == quantity) {
					String query = "DELETE FROM shares WHERE ssn=? AND symbol=?";
					ps = conn.prepareStatement(query);
					ps.setString(1, ssn);
					ps.setString(2, symbol);
					System.out.println(ps.executeUpdate() == 1 ? symbol + "주식을 전량 매도하셨습니다." : "매도 실패하셨습니다.");
				} else {
					throw new InvalidTransactionException("보유주식보다 많은양을 매도할 수 없습니다.");
				}
			} finally {
				closeAll(ps, conn);
			}
		} else {
			throw new RecordNotFoundException("해당주식을 보유하고 있지 않습니다.");
		}
	}

	// 특정한 사람이 보유하고 있는 주식수를 확인하는 메소드
	private int checkShares(String ssn, String symbol) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int quantity = 0;
		try {
			conn = getConnect();
			String query = "SELECT quantity FROM shares WHERE ssn=? AND symbol=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ssn);
			ps.setString(2, symbol);
			rs = ps.executeQuery();
			if (rs.next()) {
				quantity = rs.getInt("quantity");
			}
		} finally {
			closeAll(rs, ps, conn);
		}

		return quantity;
	}
	
	
}//Database
