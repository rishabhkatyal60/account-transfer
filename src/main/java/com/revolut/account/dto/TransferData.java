/**
 * Copyright (c) 2019 Revolut Coding Task.
 */

package com.revolut.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class TransferData implements Serializable {
    /**
     *  serial version id
     */
    private static final long serialVersionUID = 1L;

    /**
     *  Account Transfer From
     */
    private Long from;
    
    /**
     *  Account Transfer to
     */
    private Long to;
    
    /**
     *  amount transferred
     */
    private BigDecimal amount;
}
