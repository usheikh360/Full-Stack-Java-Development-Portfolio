/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author umairsheikh
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao{

     public Item item;
     public VendingMachineDaoStubImpl(){
         item = new Item(001);
         item.setItemName("kinder bueno");
         item.setItemCost(new BigDecimal("2.00"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
         item.setNumberOfItemsInInventory(15);
     }
    
    @Override
    public List<Item> getInventoryList() throws VendingMachinePersistenceException {

    Collection<Item> allVendingMachineItemsCollection = null;
    allVendingMachineItemsCollection.add(item);
    List<Item> allVendingMachineItemsList = allVendingMachineItemsCollection.stream().filter((item) -> item.getNumberOfItemsInInventory() >=1).collect(Collectors.toList());
    return allVendingMachineItemsList;
    }

    @Override
    public BigDecimal sellItem(int id, BigDecimal moneyEntered) throws VendingMachinePersistenceException {
        BigDecimal change = null;
        if (item.getId() == id){
                int currentAmountInInventory = item.getNumberOfItemsInInventory();
                change = moneyEntered.subtract(item.getItemCost().multiply(new BigDecimal("100")));//minusing the money in pence because the total money user entered is kept track of in terms of pennies     
                //and multiplying  item.getItemCost() by the new BigDecimal 100 because the item cost is stored in pounds with a scale of 2
            }
        return change;
    }

    @Override
    public Item checkItemExists(int itemID) throws VendingMachinePersistenceException, NoItemInventoryException {
        if (item.getId() == itemID){
            return item;
        }
        return null;
    }

    @Override
    public boolean checkEnoughMoneyEntered(int itemID, BigDecimal moneyEntered) throws VendingMachinePersistenceException, InsufficientFundsException {            
            if (itemID == item.getId()){
                int comperisonResult = item.getItemCost().multiply(new BigDecimal("100")).compareTo(moneyEntered);
            //comperisonResult will equal 1 if itemCost is greater than moneyEntered and -1 if itemCost is smaller than moneyEntered and 0 if itemCost and moneyEntered are equal.
            if (comperisonResult == -1 || comperisonResult == 0) { 
                return true;
            }
            }
        return false;
    }

    @Override
    public boolean checkIfItemInStock(int itemID)throws VendingMachinePersistenceException, ItemOutOfStockException {
        if (itemID == item.getId()){
            
            if (item.getNumberOfItemsInInventory() >0){
                return true;
            }
        }
        return false;
        }
    
    @Override
    public Item addItem(int itemId, Item item) throws VendingMachinePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //This method is not used for the Vending Machine and was only created for testing purposes in file VendingMachineDaoFileImplTest.java
        //although I have used it for testing purposes in VendingMachineDaoFileImplTest.java and it has worked 
        //I have not yet created a test implementation for it due to time constraints
    }
}
