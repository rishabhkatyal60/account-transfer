/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.service;

import java.math.BigDecimal;

import com.revolut.account.model.Account;

public interface AccountService {
    
    /**
     *  Create Account Service
     *  
     *  @param 
     *  @return Id Long
     */
    public Long createAccount() throws Exception;

    /**
     *  get Account balance
     *  
     *  @param Id Long 
     *  @return Long Id
     */
    Account getBalance(Long id) throws Exception;

    /**
     *  deposit amount in account
     *  
     *  @param Id Long , amount Big Decimal
     *  @return Account account
     */
    Account deposit(Long id, BigDecimal amount) throws Exception;

    /**
     *  withdraw amount from account
     *  
     *  @param Id Long , amount Big Decimal
     *  @return Account account
     */
    Account withdraw(Long id, BigDecimal amount) throws Exception;

    /**
     *  transfer amount from account
     *  
     *  @param fromAccountId Long , toAccountId Long,  amount Big Decimal
     *  @return Account account
     */
    Account transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) throws Exception;

}
