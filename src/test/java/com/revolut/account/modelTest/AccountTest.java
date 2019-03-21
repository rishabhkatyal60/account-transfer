/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.modelTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.Test;

import com.revolut.account.model.Account;

public class AccountTest {

    @Test
    public void testToString() throws Exception {
        Account account = new Account();

        assertThat(account.toString(), equalTo("Account(id=null, balance=null, owner=null)"));

        account.setId(777L);
        account.setBalance(new BigDecimal("10500"));

        assertThat(account.toString(), equalTo("Account(id=777, balance=10500, owner=null)"));

    }
}
