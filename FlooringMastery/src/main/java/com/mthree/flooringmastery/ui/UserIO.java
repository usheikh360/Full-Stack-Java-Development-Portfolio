/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.ui;

import java.math.BigDecimal;

/**
 *
 * @author umairsheikh
 */
public interface UserIO {
    void print(String msg);
    void printOnOneLine(String message);//used to print a message to the console on a single line
    BigDecimal readBigDecimal(String prompt);
    BigDecimal readBigDecimal(String prompt, BigDecimal min);
    BigDecimal readBigDecimalForEdits(String prompt);
    BigDecimal readBigDecimalForEdits(String prompt, BigDecimal min);
    float readFloat(String prompt);
    float readFloat(String prompt, float min, float max);
    int readInt(String prompt);
    int readInt(String prompt, int min, int max);
    String readString(String prompt);
}
