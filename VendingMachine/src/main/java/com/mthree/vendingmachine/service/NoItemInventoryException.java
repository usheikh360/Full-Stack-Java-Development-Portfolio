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
public class NoItemInventoryException extends Exception{//used in case no such item exists in the vending machine
    public NoItemInventoryException(String message){
        super(message);
    }
    
    public NoItemInventoryException(String message, Throwable cause){
        super(cause);
    }
}
