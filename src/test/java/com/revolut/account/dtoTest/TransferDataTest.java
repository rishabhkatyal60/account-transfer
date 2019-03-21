/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.dtoTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

import com.revolut.account.dto.TransferData;

public class TransferDataTest {

    @Test
    public void testToString() throws Exception {
        TransferData transferData = new TransferData();

        assertThat(transferData.toString(), equalTo("TransferData(from=null, to=null, amount=null)"));

        transferData.setFrom(132L);
        transferData.setTo(4315L);
        transferData.setAmount(new BigDecimal("6261"));
        assertThat(transferData.toString(), equalTo("TransferData(from=132, to=4315, amount=6261)"));
    }

}
