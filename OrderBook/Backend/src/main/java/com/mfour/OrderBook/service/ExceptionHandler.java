package com.mfour.OrderBook.service;

public class ExceptionHandler extends Exception {

    public ExceptionHandler(String string) {
        super(string);
    }

    public ExceptionHandler(String string, Error err) {
        super(string, err);
    }
}
