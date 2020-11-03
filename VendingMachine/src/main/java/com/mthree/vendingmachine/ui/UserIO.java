/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.ui;

import java.math.BigDecimal;

/**
 *
 * @author umairsheikh
 */
public interface UserIO {//used by the view to take the users input
    void print(String msg);
    
    BigDecimal readBigDecimal(String prompt);
    
    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    String readString(String prompt);
}
