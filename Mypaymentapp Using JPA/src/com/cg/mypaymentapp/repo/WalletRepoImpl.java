package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InvalidInputException;


public class WalletRepoImpl implements WalletRepo{

	
	
	public WalletRepoImpl(Map<String, Customer> data) 
	{
		
	}

	public boolean save(Customer customer) throws Exception 
	{
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("Mypaymentapp Using JPA");
		EntityManager em=emf.createEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		
		em.persist(customer);
		tx.commit();
			return true;
	}
	
	
	

	public Customer findOne(String mobileNo) 
	{
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("Mypaymentapp Using JPA");
		EntityManager em=emf.createEntityManager();
		
		Customer cust=em.find(Customer.class, mobileNo);
	
		return cust;
		
	}
	
}
