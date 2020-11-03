/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.FlooringMasteryAduitDao;
import com.mthree.flooringmastery.dao.FlooringMasteryDao;
import com.mthree.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer{//this is where the business logic of the application happens as discussed in the controller clas
    private FlooringMasteryDao dao;
    private FlooringMasteryAduitDao auditDao;

    public FlooringMasteryServiceLayerImpl(FlooringMasteryDao dao, FlooringMasteryAduitDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    @Override 
    public Order confirmOrder(Order orderToConfirm) throws FlooringMasteryPersistenceException{
        if (orderToConfirm !=null){
            String orderInfo = String.format("%s: #%s %s %s %s %s %s %s %s £%s %s %s ", orderToConfirm.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")), orderToConfirm.getCustomerName(), 
                      orderToConfirm.getState(), orderToConfirm.getTaxRate(), orderToConfirm.getProductType(), orderToConfirm.getArea(), orderToConfirm.getCostPerSquareFoot(), 
                      orderToConfirm.getLaborCostPerSquareFoot(), orderToConfirm.getMaterialCost(), orderToConfirm.getLaborCost(), orderToConfirm.getTax(), orderToConfirm.getTotal());
            auditDao.writeAuditEntry("The Current details of order number #" + orderToConfirm.getOrderNumber() + " are: " + orderInfo);
            return dao.orderIsConfirmed(orderToConfirm);
        }
        return null;
    } 

    @Override
    public Order createOrder(Order orderToCreate) throws FlooringMasteryPersistenceException, ProductTypeException, StateException {//this method is used by the user to add a new order 
        return dao.addOrder(orderToCreate);
    }
    
    @Override
    public Order editTheOrder(Order orderInfo) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException, ProductTypeException, StateException{
        String orderInfoStr = String.format("%s: #%s %s %s %s %s %s %s %s £%s %s %s ", orderInfo.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")), orderInfo.getCustomerName(),
                orderInfo.getState(), orderInfo.getTaxRate(), orderInfo.getProductType(), orderInfo.getArea(), orderInfo.getCostPerSquareFoot(),
                orderInfo.getLaborCostPerSquareFoot(), orderInfo.getMaterialCost(), orderInfo.getLaborCost(), orderInfo.getTax(), orderInfo.getTotal());
        auditDao.writeAuditEntry("Order #" +orderInfo.getOrderNumber() +  "edited - The Current State of order number #" + orderInfo.getOrderNumber() + " is: " + orderInfo);
        return dao.editOrder(orderInfo);
    }

    @Override
    public void validateOrderDate(LocalDate orderDate) throws OrderDateException, FlooringMasteryPersistenceException {
        if (dao.searchForTheOrderDate(orderDate) == null){
            throw new OrderDateException("Sorry an order with that date does not exist.");
        }
        auditDao.writeAuditEntry("Searched for an order with date " + orderDate);//confirmed means that an order was succesfully added/edited
    }

    @Override
    public void validateState(String stateUserEntered) throws StateException, FlooringMasteryPersistenceException{//checks if the tax with the statename the user entered exists
        if (dao.getTheTax(stateUserEntered) == null){
            throw new StateException("Invalid state entered!");
        }
        auditDao.writeAuditEntry("Searched for state " + stateUserEntered);
    }
    
    @Override
    public Order getUsersOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException{
         if (dao.getTheOrder(orderDate, orderNumber) == null){
             throw new FlooringMasteryPersistenceException("No such order");
         }
         auditDao.writeAuditEntry("Searched for order date " + String.valueOf(orderDate));
         return dao.getTheOrder(orderDate, orderNumber);
    }

    @Override
    public void validateProductType(String productUserEntered) throws ProductTypeException, FlooringMasteryPersistenceException {
        if (dao.productType(productUserEntered) == null){
            throw new ProductTypeException("Invalid product type entered: " + productUserEntered );
        }
    } 
    
    @Override
    public Map<String, Product> getAllProducts() throws FlooringMasteryPersistenceException{
        auditDao.writeAuditEntry("Retrieved all products");
       return dao.getDataProducts();
    }
    
    @Override
    public Map<String, Tax> getAllStates() throws FlooringMasteryPersistenceException{
        auditDao.writeAuditEntry("Retrieved all states");
       return dao.getAllStatesWithTaxInfo();
    }

    @Override
    public void validateOrderNumber(int orderNumber) throws  OrderNumberException, FlooringMasteryPersistenceException{
        
        if  (dao.searchForTheOrderNumber(orderNumber) == -1){
            throw new OrderNumberException("Sorry an oder with order number #" + orderNumber + " does not exist.");
        }
    }  

    @Override 
    public Order removerTheOrder(Order orderToRemove) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException {
        return dao.removeOrder(orderToRemove);
    }

    @Override
    public void exportOrders() throws FlooringMasteryPersistenceException{
        auditDao.writeAuditEntry("Back up of all active orders (orders that have not been removed) made.");
        dao.exportAllOrdersBackup();
    }
    
    @Override
    public List<Order> ordersForSpecifcDate(LocalDate orderDate) throws FlooringMasteryPersistenceException{
        auditDao.writeAuditEntry("Searched for orders of specific date " + orderDate);
        return dao.getOrdersForSpecifcDate(orderDate);
    }
}
