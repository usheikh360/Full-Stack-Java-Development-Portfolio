/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import com.mthree.flooringmastery.service.OrderDateException;
import com.mthree.flooringmastery.service.OrderNumberException;
import com.mthree.flooringmastery.service.ProductTypeException;
import com.mthree.flooringmastery.service.StateException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author umairsheikh
 */
public interface FlooringMasteryDao {
    Order editOrder(Order orderUserEntered) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException, ProductTypeException, StateException;
    Order removeOrder(Order orderUserEntered) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException;
    void exportAllOrdersBackup() throws FlooringMasteryPersistenceException;
    Map <String, Product> getDataProducts() throws FlooringMasteryPersistenceException;
    Order addOrder(Order order) throws FlooringMasteryPersistenceException, ProductTypeException, StateException;
    Tax getTheTax(String stateUserEntered) throws StateException, FlooringMasteryPersistenceException;
    Product productType (String productUserEntered) throws ProductTypeException, FlooringMasteryPersistenceException;
    LocalDate searchForTheOrderDate(LocalDate dateUserEntered) throws OrderDateException, FlooringMasteryPersistenceException;
    int searchForTheOrderNumber(int userEnteredOrderNumber) throws OrderNumberException, FlooringMasteryPersistenceException;
    Order getTheOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException;
    Order orderIsConfirmed(Order order) throws FlooringMasteryPersistenceException;
    Map<String, Tax> getAllStatesWithTaxInfo() throws FlooringMasteryPersistenceException;  
    List<Order>  getOrdersForSpecifcDate(LocalDate orderDate) throws FlooringMasteryPersistenceException; 
}


