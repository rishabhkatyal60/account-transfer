/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.ServiceTest;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.revolut.account.exception.NotEnoughFundsException;
import com.revolut.account.model.Account;
import com.revolut.account.service.AccountService;
import com.revolut.account.service.impl.AccountServiceImpl;

public class AccountServiceTest {

    private AccountService accountService = new AccountServiceImpl();
    private Long accountId;

    @Before
    public void init() throws Exception {
        accountId = accountService.createAccount();
    }

    @Test
    public void testCreateAccount() throws Exception {
        assertThat(accountId, notNullValue());
    }

    @Test
    public void testGetBalance() throws Exception {
        assertThat(getBalance(), is(notNullValue()));
    }

    @Test
    public void testNewAccountShouldHaveZeroBalance() throws Exception {
        assertAccountBalanceEqualsTo(new BigDecimal("0"));
    }

    @Test
    public void testDesposit() throws Exception {
        deposit(new BigDecimal("100"));
        assertAccountBalanceEqualsTo(new BigDecimal("100"));

        deposit(new BigDecimal("23"));
        assertAccountBalanceEqualsTo(new BigDecimal("123"));
    }

    @Test
    public void testWithdraw() throws Exception {
        deposit(new BigDecimal("1000"));
        assertAccountBalanceEqualsTo(new BigDecimal("1000"));

        withdraw(new BigDecimal("10"));
        assertAccountBalanceEqualsTo(new BigDecimal("990"));

        withdraw(new BigDecimal("123"));
        assertAccountBalanceEqualsTo(new BigDecimal("867"));
    }

    @Test
    public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
        assertWithdrawFails(accountId, new BigDecimal("5"));

        deposit(new BigDecimal("30"));
        assertAccountBalanceEqualsTo(new BigDecimal("30"));
        assertWithdrawFails(accountId, new BigDecimal("100"));
        assertAccountBalanceEqualsTo(new BigDecimal("30"));

        withdraw(new BigDecimal("10"));
        assertAccountBalanceEqualsTo(new BigDecimal("20"));
        assertWithdrawFails(accountId, new BigDecimal("30"));
        assertAccountBalanceEqualsTo(new BigDecimal("20"));

        withdraw(new BigDecimal("20"));
        assertAccountBalanceEqualsTo(new BigDecimal("0"));
        assertWithdrawFails(accountId, new BigDecimal("1"));
        assertAccountBalanceEqualsTo(new BigDecimal("0"));
    }

    @Test
    public void testAmountMustBePositiveOnly() throws Exception {
        assertDepositFails(accountId, new BigDecimal("0"));
        assertWithdrawFails(accountId, new BigDecimal("0"));

        assertDepositFails(accountId, new BigDecimal("-100"));
        assertWithdrawFails(accountId, new BigDecimal("-20"));
    }

    private Account getBalance() throws Exception {
        return accountService.getBalance(accountId);
    }

    private void deposit(BigDecimal amount) throws Exception {
        accountService.deposit(accountId, amount);
    }

    private void withdraw(BigDecimal amount) throws Exception {
        accountService.withdraw(accountId, amount);
    }

    private void assertAccountBalanceEqualsTo(BigDecimal balance) throws Exception {
        assertThat(accountService.getBalance(accountId).getBalance().compareTo(balance), equalTo(0));
    }

    private void assertDepositFails(Long id, BigDecimal amount) {
        try {
            accountService.deposit(id, amount);
            fail("Should not deposit");
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    private void assertWithdrawFails(Long id, BigDecimal amount) {
        try {
            accountService.withdraw(id, amount);
            fail("Should not withdraw");
        } catch (Exception e) {
            if (amount.signum() > 0) {
                assertThat(e, instanceOf(NotEnoughFundsException.class));
            } else {
                assertThat(e, instanceOf(IllegalArgumentException.class));
            }
        }
    }

}
