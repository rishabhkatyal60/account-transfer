/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.controllerTest;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.account.App;
import com.revolut.account.dto.Amount;
import com.revolut.account.dto.TransferData;
import com.revolut.account.model.Account;

class BaseRestServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseRestServiceTest.class);

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = App.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(App.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    WebTarget getTarget() {
        return target;
    }

    void logResponseData(Account account) {
        logger.info(account.toString());
    }

    void logAccountId(String accountId) {
        logger.info(accountId);
    }

    void assertDepositSuccess(Long accountId, BigDecimal depositAmount) {
        Account account = getTarget().path("/account/" + accountId + "/deposit")
                .request()
                .put(Entity.entity(new Amount(depositAmount), MediaType.APPLICATION_JSON), Account.class);
        logResponseData(account);

        assertThat(account.getBalance(), notNullValue());
        assertThat(account.getId(), notNullValue());
    }

    void assertWithdrawSuccess(Long accountId, BigDecimal withdrawAmount) {
        Account account = getTarget().path("/account/" + accountId + "/withdraw")
                .request()
                .put(Entity.entity(new Amount(withdrawAmount), MediaType.APPLICATION_JSON), Account.class);
        logResponseData(account);

        assertThat(account.getBalance(), notNullValue());
        assertThat(account.getId(), notNullValue());
    }

    String assertCreateAccount() {
        String accountId = getTarget().path("/account/create").request().post(null, String.class);
        logAccountId(accountId);

        assertThat(accountId, notNullValue());
        return accountId;
    }

    void assertAccountBalanceEqualsTo(Long accountId, BigDecimal balanceAmount) {
        BigDecimal accountBalance = getBalance(accountId);

        assertThat(accountBalance.compareTo(balanceAmount), equalTo(0));
    }

    BigDecimal getBalance(Long accountId) {
        Account account = getTarget().path("/account/" + accountId + "/balance").request().get(Account.class);
        logResponseData(account);

        assertThat(account.getBalance(), notNullValue());

        BigDecimal accountBalance = account.getBalance();
        logger.info("Balance={}", accountBalance);

        return accountBalance;
    }

    protected void assertTransferSuccess(Long fromId, Long toId, BigDecimal amount) {

        Account account = performTransfer(fromId, toId, amount);

        assertThat(account.getBalance(), notNullValue());
    }

    protected void assertTransferFails(Long fromId, Long toId, BigDecimal amount) {
        Account account = performTransfer(fromId, toId, amount);

        assertThat(account.getId(), notNullValue());
    }

    private Account performTransfer(Long fromId, Long toId, BigDecimal amount) {
        TransferData transferData = new TransferData(fromId, toId, amount);
        Account accnt = getTarget().path("/account/transfer")
                .request()
                .post(Entity.entity(transferData, MediaType.APPLICATION_JSON), Account.class);
        return accnt;
    }

}
