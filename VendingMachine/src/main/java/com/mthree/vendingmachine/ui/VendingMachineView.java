/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.ui;

import com.mthree.vendingmachine.dto.Coins;
import com.mthree.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author umairsheikh
 */
public class VendingMachineView {
    private UserIO io;
    
    public VendingMachineView(UserIO io){//dependency injection used
        this.io = io;
    }
    
    
    public int printMenuAndGetSelection() {
        io.print("====Main Menu====");
        io.print("1. List Inventory");
        io.print("2. Purchase Item");
        io.print("3. Exit");
        return io.readInt("Please select an option from the Main Menu from 1 to 3." , 1, 3);
    }

    public void displayInventory(List<Item> itemsList) {
        io.print("====Items in the Vending Machine====");
        String titles = String.format("%s| %s| %s| %s", "ITEM ID", "ITEM NAME", "ITEM PRICE", "QUANTITY CURRENTLY IN THE VENDING MACHINE");
        //stirng formatting used above and below to make it clear what data represnts which information
        io.print(titles);
        for (Item item : itemsList){
            String itemInfo = String.format("#%s: %s £%s (%s in stock)", item.getId(), item.getItemName(), item.getItemCost(), item.getNumberOfItemsInInventory());
            io.print(itemInfo);
        }
    }

    public int getItemID() {
        String idStr = io.readString("Please enter the ID number of the item you would like to purchase, e.g. #7 or 7.");
        idStr = idStr.replaceAll("\\D", "");//regular expression used to remove any letters or # the user might enter
        int id = Integer.parseInt(idStr);
        return id;
    }

    public BigDecimal getMoney() {
        io.print("=============================================");
        io.print("The coins the Vending Machine takes");
        io.print("A) £2, B)£1, C)50 PENCE,  D)20 PENCE");
        io.print("E)10 PENCE, F)5 PENCE, G)2 PENCE, H)1 PENCE");
        io.print("=============================================");
        io.print("To insert money into the vending machine:");
        io.print("Please enter the corresponding letters to the coins e.g. A for £2.");
        io.print("Enter 0 when you have finished inserting your coins.");
        
        BigDecimal totalMoneyEntered =  new BigDecimal("0");//The coins class keeps track of coin values in pennies therefore, totalMoneyEntered will keep track of the money in pennies
        
        boolean finished = false;
        
        do { //using a do while loop to ask the user to keep entering money till the user enters 0 to let the vending machine know that they have finished inserting money 
               String valueEntered = io.readString("==Insert money (or enter 0 to finish inserting money)==");
               //In the if statements below the Enum Coins are used to get the value in pennies of the coin the user enters into the vending machine
               //this is kept track of in 'totalMoneyEntered;
               if (valueEntered.equals("0")){
                   finished = true;
               }else if (valueEntered.equals("a") || valueEntered.equals("A")){                 
                   totalMoneyEntered = totalMoneyEntered.add(Coins.TWO_POUNDS.getCoinValue()); 
               }else if (valueEntered.equals("b") || valueEntered.equals("B")){
                   totalMoneyEntered = totalMoneyEntered.add(Coins.ONE_POUND.getCoinValue());
               }else if (valueEntered.equals("c") || valueEntered.equals("C")){
                   totalMoneyEntered = totalMoneyEntered.add(Coins.FIFTY_PENCE.getCoinValue());
               }else if (valueEntered.equals("d") || valueEntered.equals("D")){
                   totalMoneyEntered = totalMoneyEntered.add(Coins.TWENTY_PENCE.getCoinValue());
               }else if (valueEntered.equals("e") || valueEntered.equals("E")){
                   totalMoneyEntered = totalMoneyEntered.add(Coins.TEN_PENCE.getCoinValue());
               }else if (valueEntered.equals("f") || valueEntered.equals("F")){
                   totalMoneyEntered = totalMoneyEntered.add(Coins.FIVE_PENCE.getCoinValue());
               }else if (valueEntered.equals("g") || valueEntered.equals("G")){
                   totalMoneyEntered = totalMoneyEntered.add(Coins.TWO_PENCE.getCoinValue());
               }else if (valueEntered.equals("h") || valueEntered.equals("H")){
                   totalMoneyEntered = totalMoneyEntered.add(Coins.ONE_PENCE.getCoinValue());
               }
               
        }while (!finished);
        
        return totalMoneyEntered;
    }  

    public void displayExitBanner() {
        io.print("Good Bye!");
    }
    
    public void displayChangeReturnedBanner() {
        io.print("Your change is returned below:");
    }
    
    public void displayMoneyReturnedBanner(){
        io.print("Your money is returned below");
    }

    public void displayUnknownCommandBanner() {
        io.print("Sorry that command was unknown, please try again!");
    }

    public void displayErrorMessage(String message) {//used for the exceptions I have created, 'message' is the String message passed by a partiucular exception which is displayed to the user
        io.print("====ERROR===");
        io.print(message); 
    }

    public void displayChangeAndMoneyReturn(Map<String, Integer> allChangeInCoins, BigDecimal money) {//added a lambda here because it seemed appropriate to use
        money = money.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);//dividng by 100 because here the money is stored in pennies
        io.print("====Money Entered: £" + money + "====");
        Set keys = allChangeInCoins.keySet();//getting all the values of coins in the map
        keys.stream().forEach((k) -> System.out.println("Total number of " +k + " coins returned: " + allChangeInCoins.get(k)));//displaying all of the coins
    }
    
    public void displaySoldItemSuccessMessage(int itemId){//when an item is sold to the user successfully this banner is displayed
        io.print("====Success-Item #" + itemId + " sold sucessfullly, enjoy!====");
    }
}
