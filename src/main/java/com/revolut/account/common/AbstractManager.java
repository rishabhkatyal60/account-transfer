/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revolut.account.db.HibernateContextHolder;
import com.revolut.account.service.impl.AccountServiceImpl;

public abstract class AbstractManager {
    protected static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    protected <T> T perform(Operation<T> operation) throws Exception {
        EntityManager em = HibernateContextHolder.getInstance().createEntityManager();
        EntityTransaction tx = null;
        T result;
        try {
            tx = em.getTransaction();
            tx.begin();
            result = operation.perform(em);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.trace(e.getMessage(), e);
            throw e;
        } finally {
            em.close();
        }
        return result;
    }
}
