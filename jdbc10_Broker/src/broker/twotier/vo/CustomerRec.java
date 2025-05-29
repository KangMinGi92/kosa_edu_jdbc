package broker.twotier.vo;

import java.util.List;

public class CustomerRec {
	private String ssn;
	private String name; // 컬럼명 cust_name
	private String address;
	
	//주식을 보유하는 고객을 설명할 수 있는 코드 부분!!!
	private List<SharesRec> portfolio;
	
	public CustomerRec() {
	}

	public CustomerRec(String ssn, String name, String address) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.address = address;
	}
	
	
	
	public CustomerRec(String ssn, String name, String address, List<SharesRec> portfolio) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.address = address;
		this.portfolio = portfolio;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public List<SharesRec> getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(List<SharesRec> portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "CustomerRec [ssn=" + ssn + ", name=" + name + ", address=" + address + ", SharesRecs=" + portfolio + "]";
	}


	
}
