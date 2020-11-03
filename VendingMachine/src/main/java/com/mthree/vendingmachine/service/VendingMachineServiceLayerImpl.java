/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineAuditDao;
import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author umairsheikh
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{//this layer is used for business logic
    //it mainly includes the following logics: check an item is in stock if not don't sell the item, if the item does not exist don't sell it, if not enoguh money was enterd do not sell the item
private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    private LocalDateTime dateTime;
    
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    @Override
    public List<Item> getInventory() throws VendingMachinePersistenceException{
        return dao.getInventoryList();
    }
    
    @Override
    public BigDecimal sellingItem(int itemID, BigDecimal moneyEntered) throws 
            InsufficientFundsException, //exception thrown when not enough money entered by the user
            NoItemInventoryException, //exception thrown  when the item doesn't exist
            ItemOutOfStockException, //exception thrown  when the item is out of stock
            VendingMachinePersistenceException {
        
        if (dao.checkItemExists(itemID) == null){
            throw new NoItemInventoryException("No such item with ID " + itemID);
        }

        if (dao.checkEnoughMoneyEntered(itemID, moneyEntered)==false){
            throw new InsufficientFundsException("Insufficient funds provided for item #" + itemID + ". Money entered: £" + moneyEntered.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
        } 
        if (dao.checkIfItemInStock(itemID) == false){
            throw new ItemOutOfStockException("Sorry item #" + itemID + " is currently out of stock.");
        }
        
        BigDecimal change = dao.sellItem(itemID, moneyEntered);
        if (change!=null){//value of £0.00 is not the same as null, and ideally null should not be returned but if it is "Could not sell item #" +itemID will be written to the audit.txt log file 
            auditDao.writeAuditEntry("Item #" + itemID + " sold");
        }else {
               auditDao.writeAuditEntry("Could not sell item #" +itemID);
        }
        
        return change;
    }
    
    @Override
    public Map<String, Integer> getChange(BigDecimal changeInPennies) throws VendingMachinePersistenceException{
       Change change = new Change(changeInPennies);
       change.calculateChange();//calling the change method to get the amount of different coins to give back to the user
       Map<String, Integer> allChangeInCoins = new HashMap<>();//storing those coins in a Hasmap because it seems most suitable
       allChangeInCoins.put("£2", change.getNumberOfTwoPoundCoins());
       allChangeInCoins.put("£1", change.getNumberOfOnePoundCoins());
       allChangeInCoins.put("50 pence", change.getNumberOfFiftyPenceCoins());
       allChangeInCoins.put("20 pence", change.getNumberOfTwentyPenceCoins());
       allChangeInCoins.put("10 pence", change.getNumberOfTenPenceCoins());
       allChangeInCoins.put("5 pence", change.getNumberOfFivePenceCoins());
       allChangeInCoins.put("2 pence", change.getNumberOfTwoPenceCoins());
       allChangeInCoins.put("1 pence", change.getNumberOfOnePenceCoins());
       
       BigDecimal inPounds = new BigDecimal (String.valueOf(changeInPennies));
       inPounds = inPounds.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);//dividing  changeInPennies by 100 so that it can be written to the audit log in pounds
       auditDao.writeAuditEntry("Change was provided of £" + inPounds);      
       return allChangeInCoins;
    }
    
} 
