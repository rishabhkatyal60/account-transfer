/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.helper;

import static org.junit.Assert.fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected void assertTestFails(TestOperation testOperation, Class<?> exceptionClass) throws Exception {
        try {
            testOperation.perform();
            fail("should fail with " + exceptionClass.getName());
        } catch (Exception e) {
            if (exceptionClass.isInstance(e)) {
                logger.debug(e.getMessage());
                return;
            }
            throw e;
        }
    }

}
