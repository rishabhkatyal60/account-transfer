/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Amount implements Serializable {
    /**
     *  serial version id
     */
    private static final long serialVersionUID = 1L;

    /**
     *  amount
     */
    private BigDecimal amount;
}