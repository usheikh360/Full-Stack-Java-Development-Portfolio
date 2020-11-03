/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author umairsheikh
 */
public interface FlooringMasteryServiceLayer {
   Order createOrder(Order orderToCreate) throws FlooringMasteryPersistenceException, ProductTypeException, StateException;
   void validateOrderDate(LocalDate orderDate) throws OrderDateException, FlooringMasteryPersistenceException;
   void validateState(String stateUserEntered) throws StateException, FlooringMasteryPersistenceException;
   void validateProductType(String productUserEntered) throws ProductTypeException, FlooringMasteryPersistenceException;
   void validateOrderNumber(int orderNumber) throws  OrderNumberException, FlooringMasteryPersistenceException;   
   Order editTheOrder(Order orderInfo) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException, ProductTypeException, StateException;
   void exportOrders() throws FlooringMasteryPersistenceException;
   Map<String, Product> getAllProducts() throws FlooringMasteryPersistenceException;
   Order getUsersOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException;
   Order removerTheOrder(Order orderToRemove) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException;
   Order confirmOrder(Order orderToConfirm) throws FlooringMasteryPersistenceException;
   Map<String, Tax> getAllStates() throws FlooringMasteryPersistenceException;
   List<Order> ordersForSpecifcDate(LocalDate orderDate) throws FlooringMasteryPersistenceException;
} 
