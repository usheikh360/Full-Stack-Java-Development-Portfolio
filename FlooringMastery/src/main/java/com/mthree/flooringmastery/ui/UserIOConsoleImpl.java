/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 *
 * @author umairsheikh
 */
public class UserIOConsoleImpl implements UserIO {
    private Scanner scanner = new Scanner(System.in);//used for the purpose of retrieving the user input 
    
    @Override
    public void print(String message){//used to print a message to the console
       System.out.println(message);
    }
    
    @Override
    public void printOnOneLine(String message){//used to print a message to the console on a single line
       System.out.print(message);
    }
    
    @Override
    public String readString(String prompt){//used to print a message to the console and retrieve the user's response
       this.print(prompt);
       String valueUserEntered = scanner.nextLine();
       return valueUserEntered;
   }
    
    @Override
    public BigDecimal readBigDecimal(String prompt){
        String valueEntered = this.readString(prompt);
        BigDecimal valueToBigDecimal = new BigDecimal(valueEntered);
        valueToBigDecimal = valueToBigDecimal.setScale(2, RoundingMode.HALF_UP);
        return valueToBigDecimal;
    }

    @Override
    public int readInt(String prompt){//try catch blocks used to let the user know what they entered was not a valid number
        System.out.println(prompt);
        int valueUserEntered = -1;
        try{valueUserEntered = Integer.parseInt(scanner.nextLine());}
        catch(NumberFormatException e){
            System.out.println("Not a number, please try again.");
        }
       return valueUserEntered;
    }

    @Override
    public int readInt(String prompt, int min, int max){//checking if the the the value being read is within the min and max range 
        //and is converted and returned back as a int
        int valueUserEntered = 0;
        do{
            valueUserEntered = this.readInt(prompt);//readInt(String prompt) is defined above 
        }while (valueUserEntered<min || valueUserEntered >max);
        
        return valueUserEntered;
        
    }
 
    @Override
    public float readFloat(String prompt){//try catch blocks used to let the user know what they entered was not a valid number
       System.out.println(prompt);
       float valueUserEntered =-1;
        try{valueUserEntered = Float.parseFloat(scanner.nextLine());}
        catch(NumberFormatException e){
            System.out.println("Not a number, please try again!");
        }
       return valueUserEntered;
    }
    
    @Override
    public float readFloat(String prompt, float min, float max){//checking if the the the value being read is within the min and max range 
        //and is converted and returned back as a float
        float valueUserEntered = -1;
        do{
            valueUserEntered = this.readFloat(prompt);//readFloat(prompt) is defined above
        }while (valueUserEntered<min || valueUserEntered > max);
        return valueUserEntered;  
    }
    
    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        Boolean meetsCriteria = false; 
        BigDecimal bigDecimal = null;
        do {
            try{
              bigDecimal = readBigDecimal(prompt);
              if (bigDecimal.compareTo(min) >=0){
                meetsCriteria = true;
            }
            }catch(NumberFormatException | NullPointerException e){
                System.out.println("Not a valid input");
            }
        }while (meetsCriteria == false);
        
        return bigDecimal;
    }
    
    
    
    @Override
    public BigDecimal readBigDecimalForEdits(String prompt, BigDecimal min) {
        String valueEntered = this.readString(prompt);
        if (!(valueEntered.isEmpty())){
            Boolean meetsCriteria = false; 
            BigDecimal bigDecimal = null;
        do {
            try{
              bigDecimal = readBigDecimalForEdits(valueEntered);
              if (bigDecimal.compareTo(min) >=0){
                meetsCriteria = true;
            }
            }catch(NumberFormatException | NullPointerException e){
                System.out.println("Not a valid input, note minimum valid area is 100");
            }
            if (meetsCriteria == false){
                valueEntered = this.readString(prompt);
            }
        }while (meetsCriteria == false);
         return bigDecimal;
        }
        
        return null;
    }
    
    
    @Override
    public BigDecimal readBigDecimalForEdits(String prompt){
        BigDecimal valueToBigDecimal = new BigDecimal(prompt);
        valueToBigDecimal = valueToBigDecimal.setScale(2, RoundingMode.HALF_UP);
        return valueToBigDecimal;
    }
}
