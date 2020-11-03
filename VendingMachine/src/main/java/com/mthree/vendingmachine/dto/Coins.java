/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author umairsheikh
 */
public enum Coins {
    // This will call enum constructor
    TWO_POUNDS(new BigDecimal("200")), ONE_POUND(new BigDecimal("100")), FIFTY_PENCE(new BigDecimal("50")), 
    TWENTY_PENCE(new BigDecimal("20")), TEN_PENCE(new BigDecimal("10")), FIVE_PENCE(new BigDecimal("5")), 
    TWO_PENCE(new BigDecimal("2")), ONE_PENCE(new BigDecimal("1"));
  
    // declaring private variable for getting values
    private BigDecimal coinValue;
  
    // getter method
    public BigDecimal getCoinValue(){
        return this.coinValue;
    }
    
    // enum constructor
    private  Coins(BigDecimal coinValue){
        this.coinValue = coinValue;
    }
    
    
}
