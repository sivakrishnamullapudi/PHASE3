package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{
private WalletRepo repo;
	
	public WalletServiceImpl(Map<String, Customer> data)
	{
		repo= new WalletRepoImpl(data);
	}
	public WalletServiceImpl(WalletRepo repo) 
	{
		super();
		this.repo = repo;
	}
	public WalletServiceImpl() 
	{
	
		repo= new WalletRepoImpl(new HashMap<>());

	}
	
	

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws Exception 
	{
	
		
		Customer customer1=null;
		
		String pattern="[1-9][0-9]{9}";
		if(!mobileNo.matches(pattern))
		{
			throw new InvalidInputException("Mobile number should contain 10 digits only, no alphabets");
		}
		
		if(repo.findOne(mobileNo)!=null)
		{
			throw new InvalidInputException("Account Exists");
		}
		
		if((amount.compareTo(new BigDecimal(0)))>0)
		{
		customer1=new Customer(name, mobileNo, new Wallet(amount));
		
		if(repo.save(customer1))
		{
			return customer1;
		}
		return customer1;}
		
		else
		{
			throw new InvalidInputException("Amount has to be > 0");
		}
	}
	
	
	
	

	public Customer showBalance(String mobileNo) throws InvalidInputException
	{
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Mobile number doesn't exist");
	}
	
	

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)throws Exception
	{
		
		Customer source=repo.findOne(sourceMobileNo);
		Customer target=repo.findOne(targetMobileNo);
		if((source==null||target==null)||(sourceMobileNo.equals(targetMobileNo)))
			throw new InvalidInputException("source or target number is invalid");
			
		if((amount.compareTo(new BigDecimal(0)))<0)
		{
			throw new InvalidInputException("Amount >0");
		}
		else {
	
		if(source!=null&&target!=null&&(source.getWallet().getBalance().compareTo(amount))>=0)
		{
			BigDecimal amount1=target.getWallet().getBalance().add(amount);
			target.setWallet(new Wallet(amount1));
			repo.save(target);
			
			amount1=source.getWallet().getBalance().subtract(amount);
			source.setWallet(new Wallet(amount1));
			repo.save(source);

		return source;
		}
		else 
			throw new InsufficientBalanceException(" fund amount amount should not be greater than balance");
		}
		
	}
	
	

	public Customer depositAmount(String mobileNo, BigDecimal amount)throws Exception
	{
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
		{
			if((amount.compareTo(new BigDecimal(0)))>0)
			{
				BigDecimal amount1=customer.getWallet().getBalance().add(amount);
			customer.setWallet(new Wallet(amount1));
			if(repo.save(customer))
			{
				return customer;
			}
			}
			else throw new 	InvalidInputException("deposit amount >0");
		}
		else
			throw new InvalidInputException("Mobile number doesn't exist");
	
		return null;
	}
	

	
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws Exception
	{
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
		{
			if((amount.compareTo(new BigDecimal(0)))>0)
			{
				int i=customer.getWallet().getBalance().compareTo(amount);
			if(i>=0)
			{
				BigDecimal amount1=customer.getWallet().getBalance().subtract(amount);
				customer.setWallet(new Wallet(amount1));
				if(repo.save(customer))
				{
					return customer;
				}
			}		
			else
				throw new InsufficientBalanceException("withdrawal amount can't be greater than balance");
			}
			else throw new 	InvalidInputException("withdraw amount >0");
	}
		else
		throw new InvalidInputException("Mobile number doesn't exist ");
		return null;
}
	
	
	
}