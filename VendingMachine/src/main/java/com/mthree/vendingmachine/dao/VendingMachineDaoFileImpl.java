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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author umairsheikh
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    private final String VENDING_MACHINES_INVENTORY_FILE;
    public static final String DELIMITER = "::";

    private Map<Integer, Item> itemsInventory = new HashMap<>();//all of the items are stored in this
    
    public VendingMachineDaoFileImpl(){
        VENDING_MACHINES_INVENTORY_FILE = "vendingMachinesItemsInventory.txt";
    }
    
    public VendingMachineDaoFileImpl(String VendingMachineInventoryTextFile){
        VENDING_MACHINES_INVENTORY_FILE = VendingMachineInventoryTextFile;
    }

    
    @Override
    public List<Item> getInventoryList() throws VendingMachinePersistenceException{//using lambdas to only display items that are in stock
        loadItems();
        Collection<Item> allVendingMachineItemsCollection = itemsInventory.values();
        List<Item> allVendingMachineItemsList = allVendingMachineItemsCollection.stream().filter((item) -> item.getNumberOfItemsInInventory() >=1).collect(Collectors.toList());
        return allVendingMachineItemsList;
    }
    

    @Override
    public BigDecimal sellItem(int id, BigDecimal moneyEntered) throws VendingMachinePersistenceException, VendingMachinePersistenceException{//moneyEntered is in pennies
        loadItems();
        BigDecimal change;
        Collection<Item> allVendingMachineItemsCollection = itemsInventory.values();
        for (Item item: allVendingMachineItemsCollection){
            if (item.getId() == id){
                int currentAmountInInventory = item.getNumberOfItemsInInventory();
                itemsInventory.get(id).setNumberOfItemsInInventory(currentAmountInInventory-1);//the  NumberOfItemsInInventory for that item is reduced by 1
                change = moneyEntered.subtract(item.getItemCost().multiply(new BigDecimal("100")));//minusing the money in pence because the total money user entered is kept track of in terms of pennies 
                writeItems();
                
                return change;
            }
        }
        return null;
    }

    @Override
        public Item checkItemExists(int itemID) throws VendingMachinePersistenceException, NoItemInventoryException{
        loadItems();
        return itemsInventory.get(itemID);
    }


    @Override
    public boolean checkEnoughMoneyEntered(int itemID, BigDecimal moneyEntered) throws VendingMachinePersistenceException, InsufficientFundsException{
        loadItems();
        Set<Integer> keys = itemsInventory.keySet();
            for (int key: keys){
            int comperisonResult = itemsInventory.get(itemID).getItemCost().multiply(new BigDecimal("100")).compareTo(moneyEntered);//It will return 0 if itemCost and moneyEntered are equal, 
            //1 if itemCost is greater than moneyEntered and -1 if itemCost is smaller than moneyEntered
            if (comperisonResult == -1 || comperisonResult == 0) 
            { 
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIfItemInStock(int itemID) throws VendingMachinePersistenceException, ItemOutOfStockException{
        loadItems();
        Set<Integer> keys = itemsInventory.keySet();
        for (int key: keys){
            if (itemsInventory.get(itemID).getNumberOfItemsInInventory() >= 1){
                return true;
            }
        }
        return false;
    }
    
    //Unmarshelling - in this case the process of taking each record from the VENDING_MACHINES_INVENTORY_FILE
    //and creating an object of it and then storing it into the 
    private Item unmarshellVendingMachine(String itemAsText){//this method will be used to convert each itemAsText String (record in the vending machine) into an object
        
        String[] itemTokens = itemAsText.split(DELIMITER);
        
        String id = itemTokens[0];
        String itemName = itemTokens[1];
        String itemCost = itemTokens[2];
        String numberOfItemsInInventory = itemTokens[3];
        
        Item itemFromFile = new Item(Integer.parseInt(id));
        
        itemFromFile.setItemName(itemName);
        itemFromFile.setItemCost(new BigDecimal(itemCost));
        itemFromFile.setNumberOfItemsInInventory(Integer.parseInt(numberOfItemsInInventory));
        
        return itemFromFile;
    }
    
    private void loadItems() throws VendingMachinePersistenceException{
        Scanner scanner;
        
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(VENDING_MACHINES_INVENTORY_FILE)));
        }catch(FileNotFoundException e){
            throw new VendingMachinePersistenceException("-_- Unable to load file.");
        }
        
        String currentLine;
        Item currentItem;
        
        while (scanner.hasNext()){
            currentLine = scanner.nextLine();
            currentItem = unmarshellVendingMachine(currentLine);
            itemsInventory.put(currentItem.getId(), currentItem);
        }
        
        scanner.close();
    }
    
    private String nmarshellVendingMachine(Item item){
        String itemRecordAsText;
        
        String id = String.valueOf(item.getId());
        String itemName = String.valueOf(item.getItemName());
        String itemCost = String.valueOf(item.getItemCost());
        String numberOfItemsInInventory = String.valueOf(item.getNumberOfItemsInInventory());
        
        itemRecordAsText = id + DELIMITER + itemName + DELIMITER + itemCost + DELIMITER + numberOfItemsInInventory;
        
        return itemRecordAsText;
    }
    
    private void writeItems() throws VendingMachinePersistenceException{//this method makes use of lambdas
    
    PrintWriter writer;
    
    try{
        writer = new PrintWriter(new FileWriter(VENDING_MACHINES_INVENTORY_FILE));
    }catch(IOException e){
        throw new VendingMachinePersistenceException("Could not save the Item data.", e);
    }        
        Set keys = itemsInventory.keySet();
        keys.stream().forEach((k) -> {
                String itemAsTextRecord = nmarshellVendingMachine(itemsInventory.get(k));
                writer.println(itemAsTextRecord);
                writer.flush();
                });
    }
 
    @Override//used for testing purposes
    public Item addItem(int itemId, Item item) throws VendingMachinePersistenceException {//will be used for testing purposes
        loadItems();
        Item newItem = itemsInventory.put(itemId, item);
        writeItems();
        return newItem;
    }
}
