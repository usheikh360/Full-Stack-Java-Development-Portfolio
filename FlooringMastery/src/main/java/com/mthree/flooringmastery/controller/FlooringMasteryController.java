/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.controller;

import com.mthree.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.service.FlooringMasteryServiceLayer;
import com.mthree.flooringmastery.service.OrderDateException;
import com.mthree.flooringmastery.service.OrderNumberException;
import com.mthree.flooringmastery.service.ProductTypeException;
import com.mthree.flooringmastery.service.StateException;
import com.mthree.flooringmastery.ui.FlooringMasteryView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryController {//this is the controller class it uses spring based Dependency injection, and uses the service and view part of the application to communicate 
    //with the dao and the user
    
    private FlooringMasteryServiceLayer service;
    private FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }
    
        public void run() throws OrderDateException, OrderNumberException, StateException {
        boolean keepGoing = true;
        int menuSelection =0;
        try{
            while(keepGoing){
                menuSelection = getMenuSelection();
                switch (menuSelection){
                    case 1: 
                        displayOrders(); //for a specifc date
                        break;
                    case 2:       
                        addAnOrder();
                        break;
                    case 3:
                        editAnOrder();
                        break;
                    case 4:
                        removeAnOrder();
                        break;
                    case 5: 
                        exportAllData();//Please note exportAllData will export all ‘ACTIVE’ orders to a file called 'DataExport' in a ‘backup’ folder. 
                        //PLEASE NOTE THE TERM ‘ACTIVE’ MEANS ANY ORDER THAT HAS BEEN REMOVED WILL NO LONGER BE CONSIDERED AS ACTIVE, AND THEREFORE, ONLY ORDERS THAT HAVE NOT BEEN REMOVED ARE CONSIDERED AS 'ACTIVE' ORDERS AND ARE EXPORTED TO THE BACKUP FILE.
                        //THIS WOULD REPLACE ANY ORDERS FROM THE BACKUP FILE WHICH ARE NO LONGER 'ACTIVE' (I.E. THAT HAVE BEEN REMOVED').
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        }catch (FlooringMasteryPersistenceException e) {
        view.displayErrorMessage(e.getMessage());
    }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void displayOrders() {
        LocalDate orderDate;
        int orderNumber;
        orderDate = view.askForDate();
        try {
            service.validateOrderDate(orderDate);//anywhere you see 'service.' is most of the time the service layer applying some kind of business logic to the user;s input, 
            //additionally, since the controller can't directly access the dao the you will see in this class service. which tell the dao to perform certain tasks
            view.displayOrdersBanner();
            view.displaySpecifcDateOrders(service.ordersForSpecifcDate(orderDate));
            } catch (OrderDateException | FlooringMasteryPersistenceException ex) {
                view.displayErrorMessage(String.valueOf(ex.getMessage()));
            }
    }
    
    private void addAnOrder() {
        Order orderToAdd = view.askForOrderInfoToAddOrder();
        boolean validProductAndState = false;
        String productType;
        String stateAbbreviation;
        do{
            try {
                view.displayProducts(service.getAllProducts()); 
                productType = view.askForProductType();
                service.validateProductType(productType);
                orderToAdd.setProductType(productType);
                view.displayStates(service.getAllStates());
                stateAbbreviation = view.askForState();
                service.validateState(stateAbbreviation);
                orderToAdd.setState(stateAbbreviation);
                validProductAndState = true;
                Order orderWithFullInfo = service.createOrder(orderToAdd);
                if (view.askToConfirm(orderWithFullInfo, "Are you sure you want to add this new order?")){
                    if(service.confirmOrder(orderWithFullInfo)!=null){
                    view.displayOrderAddResult("===ORDER SUCCESSFULLY ADDED===");
                    }else{
                    view.displayOrderAddResult("===UNSUCCESSFULL, ORDER COULD NOT BE ADDED===");
                    }
                }
            } catch (ProductTypeException | StateException | FlooringMasteryPersistenceException ex) {
                view.displayErrorMessage(ex.getMessage()); 
            }
        }while(!validProductAndState);
    }

        private void editAnOrder() throws FlooringMasteryPersistenceException, OrderDateException, OrderNumberException {
        boolean orderExists = false;
        LocalDate orderDate;
        int orderNumber;
        do{
          orderDate = view.askForDate();
          orderNumber = view.askForOrderNumber();
            try {
                service.validateOrderDate(orderDate);
                service.validateOrderNumber(orderNumber);
                orderExists = true;
            } catch (OrderDateException | OrderNumberException ex) {
               view.displayErrorMessage(String.valueOf(ex.getMessage())); 
            }
        }while(!orderExists);
        //***call the service layer to return the products rom the dao
        Map <String, Product> allProducts = service.getAllProducts();
        boolean editInfoAccurate = false;
        Order partialEditedOrder = null;
        do{
            try {
                partialEditedOrder = view.editOrderPartialInfo(orderDate, orderNumber, allProducts);
                partialEditedOrder.setOrderDate(orderDate); //seeting the order date of the partial edited irder to the order date validated the earlier try catch block of this method
                partialEditedOrder.setOrderNumber(orderNumber);//seeting the order number of the partial edited irder to the order date validated the earlier try catch block of this method
                if (!(partialEditedOrder.getState().equals(""))){
                    service.validateState(partialEditedOrder.getState());
                } 
                if (!(partialEditedOrder.getProductType().equals(""))){
                    service.validateProductType(partialEditedOrder.getProductType());
                }
                editInfoAccurate = true;
                Order editedOrderWithFullInfo = service.editTheOrder(partialEditedOrder);
                if (view.askToConfirm(editedOrderWithFullInfo, "Are you sure you want to save these changes to the order")){
                    if(service.confirmOrder(editedOrderWithFullInfo)!=null){
                    view.displayOrderAddResult("===ORDER SUCCESSFULLY EDITED===");
                    }else{
                    view.displayOrderAddResult("===UNSUCCESSFULL, ORDER COULD NOT BE EDITED===");
                    }
                }
            } catch (StateException | ProductTypeException | FlooringMasteryPersistenceException ex) {
                view.displayErrorMessage(String.valueOf(ex.getMessage()));
            }
        }while(!editInfoAccurate); 
    }
            
    private void removeAnOrder() {
        LocalDate orderDate;
        int orderNumber;
        boolean orderExists = false;
        do{
            try {
                orderDate = view.askForDate();
                orderNumber = view.askForOrderNumber();
                service.validateOrderDate(orderDate);
                service.validateOrderNumber(orderNumber);
                Order orderToDelete = service.getUsersOrder(orderDate, orderNumber);
                orderExists = true;
                if (view.askToConfirm(orderToDelete, "Are you sure you want to remove this order?")){
                    if (service.removerTheOrder(orderToDelete) != null){
                        view.displayRemoveResult("==Order Removed Successfully!===");
                    }else{
                         view.displayRemoveResult("==No Such Order===");
                    }
                }
                } catch (OrderDateException | OrderNumberException | FlooringMasteryPersistenceException ex) {
                    view.displayErrorMessage(String.valueOf(ex.getMessage()));
                }
            
        }while(!orderExists);
    }
        

    private void exportAllData() {
        try{
            service.exportOrders();
            view.displayExportDataSuccessBanner("======Data Exported succesfully=======");
        }catch(FlooringMasteryPersistenceException ex){
            view.displayErrorMessage(ex.getMessage());
        }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    } 

    private void exitMessage() {
        view.displayExitBanner();
    }
}
