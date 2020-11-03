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
public class VendingMachineServiceLayerImplTest {
    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }
    
    @Test
    public void testSellingItemSuccessfully(){
        //Arrange
        Item item;
        item = new Item(001);
        item.setItemName("kinder bueno");
        item.setItemCost(new BigDecimal("2.00"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item.setNumberOfItemsInInventory(15);
        BigDecimal money = new BigDecimal("2.00").multiply(new BigDecimal("100"));//money will be stored in 200 IN PENNIES. The coins class keeps track of coin values in pennies 
        //therefore, the getMoney() view method would got the users money and pass it along in the programe 
        
        //Act 
        try{
            service.sellingItem(item.getId(), money);
        }catch(InsufficientFundsException | NoItemInventoryException | ItemOutOfStockException | VendingMachinePersistenceException e){
            //Assert
            fail("No exception should have been thrown." + e);
        }
    }
    
    @Test
    public void testSellItemWithInsufficientFunds(){//TEST PASSED
        //Arrange
        Item item1 = new Item(001);
        item1.setItemName("kinder bueno");
        item1.setItemCost(new BigDecimal("2.00"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item1.setNumberOfItemsInInventory(6);
        BigDecimal money = new BigDecimal("1.00").multiply(new BigDecimal("100"));//money will be stored 100 IN PENNIES. The coins class keeps track of coin values in pennies 
        //therefore, the getMoney() view method would got the users money and pass it along in the programe        
        //Act
        try{
            service.sellingItem(item1.getId(), money);
            fail("Should not sell this item!");
        }catch(NoItemInventoryException | ItemOutOfStockException | VendingMachinePersistenceException e){
            //Assert
            fail("Incorrect exception thrown.");
        }catch(InsufficientFundsException e){//should catch this exception
            return;
        }
    }
    
    
    @Test
    public void testSellingNoSuchItem(){//TEST PASSED
        //Arrange
        Item item1 = new Item(002);
        item1.setItemName("kinder bueno");
        item1.setItemCost(new BigDecimal("2.00"));////using string parameter becuase using a double or a float may not give the exact value as abig decimal
        item1.setNumberOfItemsInInventory(6);
        BigDecimal money = new BigDecimal("2.00").multiply(new BigDecimal("100"));//money will be stored 200 IN PENNIES. The coins class keeps track of coin values in pennies 
        //therefore, the getMoney() view method would got the users money and pass it along in the programe
        
        //Act
        try{
            service.sellingItem(item1.getId(), money);
            fail("Should not sell this item!");
        }catch(InsufficientFundsException | ItemOutOfStockException | VendingMachinePersistenceException e){
            //Assert
            fail("Incorrect exception thrown.");
        }catch(NoItemInventoryException e){//should catch this exception
            return;
        }
    }
}
