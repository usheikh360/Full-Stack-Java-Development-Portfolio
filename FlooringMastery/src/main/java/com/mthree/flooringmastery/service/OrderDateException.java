/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

/**
 *
 * @author umairsheikh
 */
public class OrderDateException extends Exception{

    public OrderDateException(String message) {
        super(message);
    }

    public OrderDateException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
