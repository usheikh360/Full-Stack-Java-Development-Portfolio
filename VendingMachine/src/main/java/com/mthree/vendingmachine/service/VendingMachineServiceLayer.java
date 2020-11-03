/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author umairsheikh
 */
public interface VendingMachineServiceLayer {

    public List<Item> getInventory() throws VendingMachinePersistenceException;

    public BigDecimal sellingItem(int itemID, BigDecimal moneyEntered) throws 
            InsufficientFundsException, 
            NoItemInventoryException, 
            ItemOutOfStockException, 
            VendingMachinePersistenceException;
    
    public Map<String, Integer> getChange(BigDecimal changeInPennies)throws VendingMachinePersistenceException;    
}
