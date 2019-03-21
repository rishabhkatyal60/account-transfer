/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.common;

import javax.persistence.EntityManager;

public interface Operation<T> {

    T perform(EntityManager accnt) throws Exception;
}
