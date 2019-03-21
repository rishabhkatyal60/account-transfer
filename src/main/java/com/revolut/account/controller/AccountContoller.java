/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.controller;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.revolut.account.dto.TransferData;
import com.revolut.account.dto.Amount;
import com.revolut.account.model.Account;
import com.revolut.account.service.AccountService;
import com.revolut.account.service.impl.AccountServiceImpl;

@Path("/account")
public class AccountContoller {

    private AccountService accountService;

    public AccountContoller() {
        accountService = new AccountServiceImpl();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/create")
    public String createAccount() throws Exception {

        return accountService.createAccount().toString();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}/balance")
    public Account getBalance(@PathParam("id") Long accountId) throws Exception {

        return accountService.getBalance(accountId);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}/deposit")
    public Account deposit(@PathParam("id") Long accountId, Amount amount) throws Exception {

        BigDecimal depositAmount = amount.getAmount();

        return accountService.deposit(accountId, depositAmount);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}/withdraw")
    public Account withdraw(@PathParam("id") Long accountId, Amount amount) throws Exception {

        BigDecimal depositAmount = amount.getAmount();

        return accountService.withdraw(accountId, depositAmount);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/transfer")
    public Account transfer(TransferData transferData) throws Exception {

        return accountService.transfer(transferData.getFrom(), transferData.getTo(), transferData.getAmount());

    }
}
