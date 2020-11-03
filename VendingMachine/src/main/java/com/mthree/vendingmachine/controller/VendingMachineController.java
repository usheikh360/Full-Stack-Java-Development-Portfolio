/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.controller;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.ItemOutOfStockException;
import com.mthree.vendingmachine.service.VendingMachineServiceLayer;
import com.mthree.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author umairsheikh
 */
public class VendingMachineController {
    private VendingMachineView view;
    private VendingMachineServiceLayer service;
    
    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service){
        this.view = view;
        this.service = service;
    }
    
    public void run() {
        boolean keepGoing = true;
        int menuSelection =0;
        try{
            displayInventory();
            while(keepGoing){
                menuSelection = getMenuSelection();
                switch (menuSelection){
                    case 1: 
                        displayInventory();
                        break;
                    case 2:
                    {
                        try {
                            purchaseItem();
                        } catch (InsufficientFundsException | NoItemInventoryException | ItemOutOfStockException e) {
                            view.displayErrorMessage(e.getMessage());
                        }
                    }
                        break;

                    case 3:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        }catch (VendingMachinePersistenceException e) {
        view.displayErrorMessage(e.getMessage());
    }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void displayInventory() throws VendingMachinePersistenceException{//displays only the items currently in stock but the items not in stock are still kept a track of in the vendingMachinesItemsInventory.txt file
       try{
           List <Item> itemsList = service.getInventory();
           view.displayInventory(itemsList);
       }catch(VendingMachinePersistenceException e){
           view.displayErrorMessage(e.getMessage());
       }
    }

        private void purchaseItem() throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException, ItemOutOfStockException{
        BigDecimal moneyEntered = null;
            try{
            moneyEntered = view.getMoney();
            displayInventory();
            int itemID = view.getItemID();
            BigDecimal change = new BigDecimal("0");
            change = service.sellingItem(itemID,moneyEntered);
            Map<String, Integer> allChangeInCoins = service.getChange(change);
            view.displaySoldItemSuccessMessage(itemID);
            view.displayChangeReturnedBanner();
            view.displayChangeAndMoneyReturn(allChangeInCoins, moneyEntered);
        }catch(VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException | ItemOutOfStockException e){
            Map<String, Integer> returnedMoney = service.getChange(moneyEntered);//using the change method incase there was no such item, item out of stock or not enough money was entered
            //to return the money back to the user
            view.displayErrorMessage(e.getMessage());
            view.displayMoneyReturnedBanner();
            view.displayChangeAndMoneyReturn(returnedMoney, moneyEntered);
        }   
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
