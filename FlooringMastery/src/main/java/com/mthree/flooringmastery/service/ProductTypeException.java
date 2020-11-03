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
public class ProductTypeException extends Exception {

    public ProductTypeException(String message) {
        super(message);
    }

    public ProductTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
