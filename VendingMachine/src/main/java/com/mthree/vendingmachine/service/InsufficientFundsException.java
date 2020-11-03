/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

/**
 *
 * @author umairsheikh
 */
public class InsufficientFundsException extends Exception{//exception created in case the user does not enter enough money to purchase an item
    public InsufficientFundsException(String message){
        super(message);
    }
    public InsufficientFundsException(String message, Throwable cause){
        super(message, cause);
    }
}
