package broker.twotier.test;

import broker.twotier.dao.Database;
import broker.twotier.vo.CustomerRec;

/*
 	비지니스 로직 하나하나에 대한 단위 테스트 클래스
 */
public class DatabaseUnitTest {

	public static void main(String[] args) {
		Database db = Database.getInstance();

		// CREATE
//		try {
//			db.addCustomer(new CustomerRec("777-777","강민기","서울"));
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//		}

		// UPDATE
//		try {
//			db.updateCustomer(new CustomerRec("777-777", "김개똥", "안산"));
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		// DELETE
//		try {
//			db.deleteCustomer("777-777");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		// SELECT a CUSTOMER
//		try {
//			System.out.println(db.getCustomer("222-222"));
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		
		// SELECT All CUSTOMER
//		try {
//			db.getAllCustomers().stream().forEach(System.out::println);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		//SELECT AllStocks
//		try {
//			db.getAllStocks().stream().forEach(System.out::println);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		//BUY shares
//		try {
//			db.buyShares("777-777", "JDK", 100);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		// sell shares
//		try {
//			db.sellShares("777-777", "JDK", 100);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		

		
		
	} //main
}
