package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;

import com.cg.mypaymentapp.beans.Customer;

public interface WalletRepo {
public boolean save(Customer customer) throws Exception;
	
	public Customer findOne(String mobileNo);


}
