/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.ItemOutOfStockException;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author umairsheikh
 */
public interface VendingMachineDao {

    public List<Item> getInventoryList() throws VendingMachinePersistenceException;

    public BigDecimal sellItem(int id, BigDecimal moneyEntered) throws VendingMachinePersistenceException;

    public Item checkItemExists(int itemID) throws VendingMachinePersistenceException, NoItemInventoryException;

    public boolean checkEnoughMoneyEntered(int itemID, BigDecimal moneyEntered) throws VendingMachinePersistenceException, InsufficientFundsException;

    public boolean checkIfItemInStock(int itemID) throws VendingMachinePersistenceException, ItemOutOfStockException;
    
    public Item addItem(int itemId, Item item) throws VendingMachinePersistenceException;//this method is used later for testing purposes
}
