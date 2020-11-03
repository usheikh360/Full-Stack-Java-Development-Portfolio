/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dto.Coins;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author umairsheikh
 */
public class Change {
    private BigDecimal changeInPence;
    private int numberOfTwoPoundCoins;
    private int numberOfOnePoundCoins;
    private int numberOfFiftyPenceCoins;
    private int numberOfTwentyPenceCoins;
    private int numberOfTenPenceCoins;
    private int numberOfFivePenceCoins;
    private int numberOfTwoPenceCoins;
    private int numberOfOnePenceCoins;
        
    public Change(BigDecimal changeInPence){//change is sent to this class in pence
        this.changeInPence = changeInPence;
        this.numberOfTwoPoundCoins = 0;
        this.numberOfOnePoundCoins = 0;
        this.numberOfFiftyPenceCoins = 0;
        this.numberOfTwentyPenceCoins = 0;
        this.numberOfTenPenceCoins = 0;
        this.numberOfFivePenceCoins = 0;
        this.numberOfTwoPenceCoins = 0;
        this.numberOfOnePenceCoins = 0;
    }
   
    public void calculateChange(){
        //this method will use the Coins Enums to get there values in pence 
        boolean changeLeft = true;
        BigDecimal change = new BigDecimal(String.valueOf(changeInPence));
        BigDecimal devisionResult;

        do{ 
            if (change.compareTo(BigDecimal.ZERO)!=0){//it will then compare the change to the Enum Coins if the change value here is greater then 200 then I know to give change of £2 coins
                if(change.compareTo(Coins.TWO_POUNDS.getCoinValue())>=0){
                    devisionResult = change.divide(Coins.TWO_POUNDS.getCoinValue(), 0, RoundingMode.DOWN);//using division to see how many 2 pound coins to return
                    String devisionResultStr = String.valueOf(devisionResult);//keeping a track of how many £2 coins to return
                    this.numberOfTwoPoundCoins = this.numberOfTwoPoundCoins + Integer.parseInt(devisionResultStr);//keeping a track of how many £2 coins to return
                    change = change.remainder(Coins.TWO_POUNDS.getCoinValue());//the reaminder tells me how much money is left after dividing by 200 in this instance and this remainder is used to 
                    //calculate the change still left to give against the Enum coin values below
                    //each if statement below follows a simmilar pattern to the this if statement above
                }if (change.compareTo(Coins.ONE_POUND.getCoinValue())>=0 && change.compareTo(Coins.TWO_POUNDS.getCoinValue())<0){//used to check that the change left to give is not more then £2
                    devisionResult = change.divide(Coins.ONE_POUND.getCoinValue(), 0, RoundingMode.DOWN);
                    String devisionResultStr = String.valueOf(devisionResult);
                    this.numberOfOnePoundCoins = this.numberOfOnePoundCoins + Integer.parseInt(devisionResultStr);
                    change = change.remainder(Coins.ONE_POUND.getCoinValue());
                
                }if (change.compareTo(Coins.FIFTY_PENCE.getCoinValue())>=0 && change.compareTo(Coins.ONE_POUND.getCoinValue())<=99.00){//used to check that the change left to give is less then £1 but is greater then or equal to 50 pence
                    devisionResult = change.divide(Coins.FIFTY_PENCE.getCoinValue(), 0, RoundingMode.DOWN);
                    String devisionResultStr = String.valueOf(devisionResult);
                    this.numberOfFiftyPenceCoins = this.numberOfFiftyPenceCoins + Integer.parseInt(devisionResultStr);
                    change = change.remainder(Coins.FIFTY_PENCE.getCoinValue());
                
                }if (change.compareTo(Coins.TWENTY_PENCE.getCoinValue())>=0 && change.compareTo(Coins.FIFTY_PENCE.getCoinValue())<0){//used to check that the change left to give is less then 50 pence but is greater then or equal to 20 pence
                    devisionResult = change.divide(Coins.TWENTY_PENCE.getCoinValue(), 0, RoundingMode.DOWN);
                    String devisionResultStr = String.valueOf(devisionResult);
                    this.numberOfTwentyPenceCoins = this.numberOfTwentyPenceCoins + Integer.parseInt(devisionResultStr);
                    change = change.remainder(Coins.TWENTY_PENCE.getCoinValue());
                
                }if (change.compareTo(Coins.TEN_PENCE.getCoinValue())>=0 && change.compareTo(Coins.TWENTY_PENCE.getCoinValue())<0){//used to check that the change left to give is less then 20 pence but is greater then or equal to or equal to 10 pence 
                    devisionResult = change.divide(Coins.TEN_PENCE.getCoinValue(), 0, RoundingMode.DOWN);
                    String devisionResultStr = String.valueOf(devisionResult);
                    this.numberOfTenPenceCoins = this.numberOfTenPenceCoins + Integer.parseInt(devisionResultStr);
                    change = change.remainder(Coins.TEN_PENCE.getCoinValue());
                
                }if (change.compareTo(Coins.FIVE_PENCE.getCoinValue())>=0 && change.compareTo(Coins.TEN_PENCE.getCoinValue())<0){//used to check that the change left to give is less then 10 pence but is greater then or equal to or equal to 5 pence
                    devisionResult = change.divide(Coins.FIVE_PENCE.getCoinValue(), 0, RoundingMode.DOWN);
                    String devisionResultStr = String.valueOf(devisionResult);
                    this.numberOfFivePenceCoins = this.numberOfFivePenceCoins + Integer.parseInt(devisionResultStr);
                    change = change.remainder(Coins.FIVE_PENCE.getCoinValue());
                
                }if (change.compareTo(Coins.TWO_PENCE.getCoinValue())>=0 && change.compareTo(Coins.FIVE_PENCE.getCoinValue())<0){//used to check that the change left to give is less then 5 pence but is greater then or equal to or equal to 2 pence
                    devisionResult = change.divide(Coins.TWO_PENCE.getCoinValue(), 0, RoundingMode.DOWN);
                    String devisionResultStr = String.valueOf(devisionResult);
                    this.numberOfTwoPenceCoins = this.numberOfTwoPenceCoins + Integer.parseInt(devisionResultStr);
                    change = change.remainder(Coins.TWO_PENCE.getCoinValue());
                
                }if (change.compareTo(Coins.ONE_PENCE.getCoinValue()) == 0){
                    devisionResult = change.divide(Coins.ONE_PENCE.getCoinValue(), 0, RoundingMode.DOWN);
                    String devisionResultStr = String.valueOf(devisionResult);
                    this.numberOfOnePenceCoins = this.numberOfOnePenceCoins + Integer.parseInt(devisionResultStr);//used to check that the change left to give is less then 2 pence but is greater then or equal to or equal to 1 pence
                    change = change.remainder(Coins.ONE_PENCE.getCoinValue());
                }
            }else {
                changeLeft = false;
            }
        }while(changeLeft);
    }
    
    public BigDecimal getChangeInPence() {
        return changeInPence;
    }

    public int getNumberOfTwoPoundCoins() {
        return numberOfTwoPoundCoins;
    }

    public int getNumberOfOnePoundCoins() {
        return numberOfOnePoundCoins;
    }

    public int getNumberOfFiftyPenceCoins() {
        return numberOfFiftyPenceCoins;
    }

    public int getNumberOfTwentyPenceCoins() {
        return numberOfTwentyPenceCoins;
    }

    public int getNumberOfTenPenceCoins() {
        return numberOfTenPenceCoins;
    }

    public int getNumberOfFivePenceCoins() {
        return numberOfFivePenceCoins;
    }

    public int getNumberOfTwoPenceCoins() {
        return numberOfTwoPenceCoins;
    }

    public int getNumberOfOnePenceCoins() {
        return numberOfOnePenceCoins;
    }
   
}
