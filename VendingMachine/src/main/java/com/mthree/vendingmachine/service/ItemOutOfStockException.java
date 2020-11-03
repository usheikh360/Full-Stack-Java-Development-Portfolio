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
public class ItemOutOfStockException extends Exception{//used incase the user tries to purchase an item that is out of stock
    public ItemOutOfStockException(String message){
        super(message);
    }
    
    public ItemOutOfStockException(String message, Throwable cause){
        super(message, cause);
    }
}
