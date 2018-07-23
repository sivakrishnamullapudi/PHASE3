package com.cg.mypaymentapp.beans;

import java.math.BigDecimal;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

//import com.cg.mypaymentapp.test.Wallet;
@Entity
public class Customer {
	private String name;
	@Id
	private String mobileNo;
	@Embedded
	private Wallet wallet;
	public Customer() {
		super();
		this.name = name;
		this.mobileNo = mobileNo;
		this.wallet = wallet;
	}
	
	public Customer(String name2, String mobileno2, Wallet wallet2) {
		super();
		this.name = name2;
		this.mobileNo = mobileno2;
		this.wallet = wallet2;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	@Override
	public String toString() {
		return "Customer : name=" + name + "  mobileNo=" + mobileNo
				+ "  wallet balance=" + wallet ;
	}
	
}
