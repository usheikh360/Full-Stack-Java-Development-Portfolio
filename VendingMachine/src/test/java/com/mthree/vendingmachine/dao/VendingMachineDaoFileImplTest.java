/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author umairsheikh
 */
public class VendingMachineDaoFileImplTest {
    
    VendingMachineDao testDao;
        
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        String testFile = "testVendingMchineFile.txt";
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
        
    }
    
    @Test    
    public void testGetInventoryList() throws Exception{//TEST PASSED
        Item item1 = new Item(001);
        item1.setItemName("kinder bueno");
        item1.setItemCost(new BigDecimal("2.00"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item1.setNumberOfItemsInInventory(6);
        testDao.addItem(item1.getId(), item1);
        
        Item item2 = new Item(002);
        item2.setItemName("Fanta");
        item2.setItemCost(new BigDecimal("1.99"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item2.setNumberOfItemsInInventory(3);
        testDao.addItem(item2.getId(), item2);
        
        List<Item> allVendingMachineItemsList = testDao.getInventoryList();
        assertNotNull(allVendingMachineItemsList, "The list of items must not null");
        assertEquals(2, allVendingMachineItemsList.size(),"List of items should have 2 items.");
        
        assertTrue(testDao.getInventoryList().contains(item1),"The list of items should include item1.");
        assertTrue(testDao.getInventoryList().contains(item2),"The list of items should include item2.");
    }
    
    @Test
    public void testSellItemAndItemInStockAndCheckEnoughMoneyEntered() throws Exception{//ALL TESTS PASSED
        Item item1 = new Item(001);
        item1.setItemName("kinder bueno");
        item1.setItemCost(new BigDecimal("2.00"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item1.setNumberOfItemsInInventory(1);
        testDao.addItem(item1.getId(), item1);
        
        Item item2 = new Item(002);
        item2.setItemName("Fanta");
        item2.setItemCost(new BigDecimal("1.99"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item2.setNumberOfItemsInInventory(3);
        testDao.addItem(item2.getId(), item2);
        
        List<Item> allVendingMachineItemsList = testDao.getInventoryList();
        assertEquals(2, allVendingMachineItemsList.size(),"List of items should have 2 items.");
        
        //sellItem() method takes in money in pence
        BigDecimal moneyEnteredForItem1 = new BigDecimal("200");//in pence
        testDao.sellItem(item1.getId(), moneyEnteredForItem1);
        
        allVendingMachineItemsList = testDao.getInventoryList();
        assertEquals(1, allVendingMachineItemsList.size(),"List of items should now be 1 because this method will only retrive items in stock, however the file will still presist the information about the item");
        assertTrue(testDao.getInventoryList().contains(item2),"The list of items should include item1.");
        
        assertFalse(testDao.checkIfItemInStock(item1.getId()));//
        assertTrue(testDao.checkIfItemInStock(item2.getId()));    
        
        Item item3 = new Item(003);
        item3.setItemName("Rubicon Mango");
        item3.setItemCost(new BigDecimal("1.40"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item3.setNumberOfItemsInInventory(3);
        testDao.addItem(item3.getId(), item3);
        
        BigDecimal exactMoney = new BigDecimal("140");//in pence
        assertNotNull(testDao.sellItem(item1.getId(), exactMoney), "The same amount of money as the cost of the item so it should return not null");
        
        BigDecimal moreThenEnoguhMoney = new BigDecimal("5000");//in pence
        assertNotNull(testDao.sellItem(item1.getId(), moreThenEnoguhMoney), "More money was entered then the cost of the item so it should return not null");
       
        BigDecimal notEnoughMoney = new BigDecimal("40");//in pence
        assertNotNull(testDao.sellItem(item1.getId(), notEnoughMoney), "Not enoguh money was entered so it should return null");
        
        BigDecimal noMoney = new BigDecimal("0");
        assertNotNull(testDao.sellItem(item1.getId(), noMoney), "No money was entered so it should return null");
        
    }
    
    @Test
    public void testcheckItemExists() throws Exception{//TEST PASSED
        Item item1 = new Item(001);
        item1.setItemName("kinder bueno");
        item1.setItemCost(new BigDecimal("2.00"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item1.setNumberOfItemsInInventory(1);
        testDao.addItem(item1.getId(), item1);
        
        assertNotNull(testDao.checkItemExists(item1.getId()), "Should return not null because the ite does exist");
        assertNull(testDao.checkItemExists(99), "Should return  null because the ite does not exist");
    }
           
}
