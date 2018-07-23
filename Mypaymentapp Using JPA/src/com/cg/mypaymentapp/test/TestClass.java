package com.cg.mypaymentapp.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class TestClass {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
WalletService service;
	
	@Before
	public void initData(){
		 Map<String,Customer> data= new HashMap<String, Customer>();
		 Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
		 Customer cust2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
		 Customer cust3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
				
		 data.put("9900112212", cust1);
		 data.put("9963242422", cust2);	
		 data.put("9922950519", cust3);	
			service= new WalletServiceImpl(data);
			
	}
	@Test
	public void empty() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","corp123");
		String sql="delete from customer";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.executeUpdate();
	} 
	
	
	
	@Test(expected=InvalidInputException.class)
	public void insufficientamountTest2() throws Exception 
	{
	 WalletService   service=new WalletServiceImpl();
	 service.createAccount("shiva","9985288530",new BigDecimal(200));
	 service.withdrawAmount("9123456m", new BigDecimal(200)); 
	 
	 }
	@Test(expected=InvalidInputException.class)
	public void negativeFundTransferTest() throws Exception 
	{
	 WalletService   service=new WalletServiceImpl();
	 service.createAccount("shiva","9493913969",new BigDecimal(25000));
	 service.createAccount("ravi","9985288530",new BigDecimal(10000)); 
	 service.fundTransfer("9493913969", "9985288530", new BigDecimal(-10000));
	 }
	
	@Test(expected=InvalidInputException.class)
	public void fundTransferTest() throws Exception 
	{
	 WalletService   service=new WalletServiceImpl();
	 service.createAccount("shiva","1234567890",new BigDecimal(25000));
	 service.createAccount("ravi","9849934120",new BigDecimal(10000)); 
	 service.fundTransfer("1234567890", "9934120", new BigDecimal(-10000));
	 }
	
	@Test(expected=InvalidInputException.class)
	public void fundTransferTest1() throws Exception 
	{
	 WalletService   service=new WalletServiceImpl();
	 service.createAccount("shiva","4567891230",new BigDecimal(25000));
	 service.createAccount("krishna","7894561230",new BigDecimal(10000)); 
	 service.fundTransfer("", "7894561230", new BigDecimal(-10000));
	 }
	
	
	
	
	
	@Test
	public void depositTest() throws Exception 
	{
	 WalletService   service=new WalletServiceImpl();
	 Customer c=service.createAccount("nikhil","1234567892",new BigDecimal(25000));
	 c=service.depositAmount("1234567892",new BigDecimal(5000));
	 assertEquals(c.getWallet().getBalance(),new BigDecimal(30000));
	}
	
	
	
	@Test(expected=InvalidInputException.class)
	public void depositTest1() throws Exception 
	{
	 WalletService   service=new WalletServiceImpl();
	 service.createAccount("nikhil","9032858312",new BigDecimal(-25000)); 
	}
	
}
