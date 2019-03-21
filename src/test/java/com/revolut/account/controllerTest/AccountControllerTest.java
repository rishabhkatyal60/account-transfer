/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.controllerTest;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Before;
import org.junit.Test;

public class AccountControllerTest extends BaseRestServiceTest {

    private Long accountId;
    private Long fromId;
    private Long toId;

    @Before
    public void init() throws Exception {
        String accId = assertCreateAccount();
        accountId = Long.parseLong(accId);
        assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));

        String fromAccId = assertCreateAccount();
        fromId = Long.parseLong(fromAccId);
        assertAccountBalanceEqualsTo(fromId, new BigDecimal("0"));

        String toAccId = assertCreateAccount();
        toId = Long.parseLong(toAccId);
        assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));
    }

    @Test
    public void testCreateAccount() throws Exception {
        String accId = assertCreateAccount();
        Long nextAccountId = Long.parseLong(accId);
        assertThat(nextAccountId, equalTo(++toId));
    }

    @Test
    public void testGetBalance() throws Exception {
        BigDecimal accountBalance = getBalance(accountId);
        assertThat(accountBalance, is(notNullValue()));
    }

    @Test
    public void testNewAccountShouldHaveZeroBalance() throws Exception {
        assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));
    }

    @Test
    public void testDeposit() throws Exception {
        BigDecimal depositAmount = new BigDecimal("123");
        assertDepositSuccess(accountId, depositAmount);
        assertAccountBalanceEqualsTo(accountId, depositAmount);

        BigDecimal newDepositAmount = new BigDecimal("345");
        assertDepositSuccess(accountId, newDepositAmount);
        assertAccountBalanceEqualsTo(accountId, depositAmount.add(newDepositAmount));
    }

    @Test
    public void testWithdraw() throws Exception {
        BigDecimal deposit = new BigDecimal("100");
        assertDepositSuccess(accountId, deposit);
        assertAccountBalanceEqualsTo(accountId, deposit);

        BigDecimal withdrawAmount = new BigDecimal("13");
        assertWithdrawSuccess(accountId, withdrawAmount);
        assertAccountBalanceEqualsTo(accountId, deposit.subtract(withdrawAmount));

        BigDecimal newWithdrawAmount = new BigDecimal("25");
        assertWithdrawSuccess(accountId, newWithdrawAmount);

        assertWithdrawSuccess(accountId, deposit.subtract(withdrawAmount.add(newWithdrawAmount)));
    }

    @Test
    public void testAmountMustBePositiveOnly() throws Exception {
        BigDecimal zeroAmount = new BigDecimal("0");

        assertAccountBalanceEqualsTo(accountId, zeroAmount);

        assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));
        assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));

        BigDecimal successDepositAmount = new BigDecimal("100");
        assertDepositSuccess(accountId, successDepositAmount);
        assertAccountBalanceEqualsTo(accountId, successDepositAmount);

        assertAccountBalanceEqualsTo(accountId, successDepositAmount);

        assertAccountBalanceEqualsTo(accountId, successDepositAmount);
    }

    @Test
    public void testWithdrawMustNotBeGreaterThanDeposit() throws Exception {

        assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));

        BigDecimal depositAmount = new BigDecimal("10");
        assertDepositSuccess(accountId, depositAmount);
        assertAccountBalanceEqualsTo(accountId, depositAmount);

        BigDecimal withdrawAmount = new BigDecimal("20");
        assertThat(withdrawAmount, greaterThan(depositAmount));

        assertAccountBalanceEqualsTo(accountId, depositAmount);
    }

    @Test
    public void testTransfer() throws Exception {
        assertDepositSuccess(fromId, new BigDecimal("200"));

        assertAccountBalanceEqualsTo(fromId, new BigDecimal("200"));
        assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));

        assertTransferSuccess(fromId, toId, new BigDecimal("10"));
        assertAccountBalanceEqualsTo(fromId, new BigDecimal("190"));
        assertAccountBalanceEqualsTo(toId, new BigDecimal("10"));

        assertTransferSuccess(fromId, toId, new BigDecimal("20"));
        assertAccountBalanceEqualsTo(fromId, new BigDecimal("170"));
        assertAccountBalanceEqualsTo(toId, new BigDecimal("30"));

        assertTransferSuccess(fromId, toId, new BigDecimal("70"));
        assertAccountBalanceEqualsTo(fromId, new BigDecimal("100"));
        assertAccountBalanceEqualsTo(toId, new BigDecimal("100"));
    }

}
