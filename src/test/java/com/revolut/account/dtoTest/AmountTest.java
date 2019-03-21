/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.dtoTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

import com.revolut.account.dto.Amount;

public class AmountTest {

    @Test
    public void testToString() {
        Amount amount = new Amount();

        assertThat(amount.toString(), equalTo("Amount(amount=null)"));

        amount.setAmount(new BigDecimal("123"));

        assertThat(amount.toString(), equalTo("Amount(amount=123)"));
    }
}
