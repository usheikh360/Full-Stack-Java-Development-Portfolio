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
public class OrderNumberException extends Exception{

    public OrderNumberException(String message) {
        super(message);
    }

    public OrderNumberException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
