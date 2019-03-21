/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.service.impl;

import java.math.BigDecimal;

import com.revolut.account.exception.NotEnoughFundsException;
import com.revolut.account.common.AbstractManager;
import com.revolut.account.model.Account;
import com.revolut.account.service.AccountService;

public class AccountServiceImpl extends AbstractManager implements AccountService {
 
    /**
     *  Create Account Service
     *  
     *  @param 
     *  @return Id Long
     */
    @Override
    public Long createAccount() throws Exception {
        logger.info("Account creation started");
        Long accountId = perform(em -> {
            Account account = new Account();
            account.setBalance(new BigDecimal("0"));
            em.persist(account);
            return account;
        }).getId();
        logger.info("Account created with id={}", accountId);
        return accountId;
    }
    
    /**
     *  get Account balance
     *  
     *  @param Id Long 
     *  @return Long Id
     */
    @Override
    public Account getBalance(Long id) throws Exception {
        Account account = perform(em -> em.find(Account.class, id));
        if (account != null) {
            logger.info("Account found");
            return account;

        } else {
            logger.info("Account not found!");
        }
        return account;
    }

    /**
     *  deposit amount in account
     *  
     *  @param Id Long , amount Big Decimal
     *  @return Account account
     */
    @Override
    public Account deposit(Long id, BigDecimal amount) throws Exception {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        perform(em -> {
            Account account = em.find(Account.class, id);
            BigDecimal balance = account.getBalance();
            balance = balance.add(amount);
            account.setBalance(balance);
            em.merge(account);
            return null;
        });
        logger.info("Deposit to accountId={} completed", id);

        Account accountAfterDeposit = perform(em -> em.find(Account.class, id));
        if (accountAfterDeposit != null) {
            logger.info("Account found");
            return accountAfterDeposit;

        } else {
            logger.info("Account not found!");
        }
        return accountAfterDeposit;

    }

    /**
     *  withdraw amount from account
     *  
     *  @param Id Long , amount Big Decimal
     *  @return Account account
     */
    @Override
    public Account withdraw(Long id, BigDecimal amount) throws Exception {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        perform(em -> {
            Account account = em.find(Account.class, id);
            BigDecimal balance = account.getBalance();
            balance = balance.subtract(amount);
            account.setBalance(balance);
            em.merge(account);
            return null;
        });
        logger.info("withdraw to accountId={} completed", id);

        Account accountAfterWithdraw = perform(em -> em.find(Account.class, id));
        if (accountAfterWithdraw != null) {
            logger.info("Account found");
            return accountAfterWithdraw;

        } else {
            logger.info("Account not found!");
        }
        return accountAfterWithdraw;

    }

    /**
     *  transfer amount from account
     *  
     *  @param fromAccountId Long , toAccountId Long,  amount Big Decimal
     *  @return Account account
     */
    @Override
    public Account transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) throws Exception {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        logger.info("Transfer fromId={} toId={}, amount={} started", fromAccountId, toAccountId, amount);
        Account account = new Account();
        account = perform(em -> {
            Account fromAccount = em.find(Account.class, fromAccountId);
            BigDecimal fromBalance = fromAccount.getBalance();
            if (fromBalance.compareTo(amount) < 0) {
                throw new NotEnoughFundsException("Not enough funds");
            }
            fromBalance = fromBalance.subtract(amount);

            Account toAccount = em.find(Account.class, toAccountId);
            BigDecimal toBalance = toAccount.getBalance();
            toBalance = toBalance.add(amount);
            fromAccount.setBalance(fromBalance);
            toAccount.setBalance(toBalance);

            em.merge(fromAccount);
            em.merge(toAccount);
            return toAccount;
        });
        return account;
    }
}
