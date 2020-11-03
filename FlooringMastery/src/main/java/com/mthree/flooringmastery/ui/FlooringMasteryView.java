/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.ui;

import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryView {
    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("====Main Menu====");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        io.print("    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readInt("Please select an option from the Main Menu from 1 to 6." , 1, 6);
    }
    
    public Order askForOrderInfoToAddOrder(){//INCLUDES ONLY PARTIAL ORDER INFO, THE REST IS CALCULATED IN THE DAO
        LocalDate orderDate = askForDateInTheFuture();
        String customerName = askForCustomerName();

        BigDecimal area = askForArea();
        
        Order newOrder = new Order();
        
        newOrder.setOrderDate(orderDate);
        newOrder.setCustomerName(customerName);

        newOrder.setArea(area);
        return newOrder;
    }
    
    public Order editOrderPartialInfo(LocalDate orderDate, int orderNumber, Map<String, Product> products){//****EDITED
        Order editOrder = new Order(orderNumber);
        String customerName = askForCustomerNameForEdit();
        String state = askForState();//this is the state abrivation
        displayProducts(products);
        String productType = askForProductType();
        BigDecimal area = io.readBigDecimalForEdits("Please enter the area in square ft.", new BigDecimal("100"));
        if (area!=null){
            editOrder.setArea(area);
        }
        editOrder.setCustomerName(customerName);
        editOrder.setState(state);
        editOrder.setProductType(productType);        
        return editOrder;
    }
    
   
    public LocalDate askForDate(){
        String orderDateStr = "";
        boolean invalidDateFormat = true;
        LocalDate orderDate = null;
        do{
           try{
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
               orderDateStr = io.readString("Please enter the Order date in the format MM-dd-yyyy.");
               orderDate = LocalDate.parse(orderDateStr, formatter);
               invalidDateFormat = false;
           }catch(DateTimeParseException e){
               io.print("The format of the date should be as MM-dd-yyyy!");
               invalidDateFormat = true;
           }
        } while(invalidDateFormat); 
        return orderDate;
    }
    
    public LocalDate askForDateInTheFuture(){
        boolean dateInTheFuture = false;
        LocalDate dateToday = LocalDate.now();
        LocalDate orderDate;
        do {
            orderDate = askForDate();
            if (orderDate.isAfter(dateToday)){
                dateInTheFuture = true;
            }else{
                io.print("Date not in the future!");
            }
        }while (!dateInTheFuture);
        return orderDate;
    }
    
    public int askForOrderNumber(){
       return io.readInt("Please enter Order number");
    }
   
    
    public void displaySpecifcDateOrders(List <Order> ordersList){//I wanted to try a a different approach in the dao for collecting the Order objects is why I have another method where the dao returns a list instead of a map
        io.print("Order Date | Oder Number | Customer Name | State | Tax Rate | Product type | Area | Cost Per Square Foot | Labor Cost Per Square foot | Cost of material (£) | Labor cost |Tax | Total");
        for (Order order: ordersList){
              String orderInfo = String.format("%s: #%s %s %s £%s %s %s £%s £%s £%s £%s £%s £%s ", order.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")), order.getOrderNumber(), order.getCustomerName(), 
                      order.getState(), order.getTaxRate(), order.getProductType(), order.getArea(), order.getCostPerSquareFoot(), 
                      order.getLaborCostPerSquareFoot(), order.getMaterialCost(), order.getLaborCost(), order.getTax(), order.getTotal());
            io.print(orderInfo);
        }
        io.readString("Press the enter key to continue");
    }
    
    
    public void displayOrdersBanner(String ordersOnDate){
        io.print("===Orders on " + ordersOnDate + " ===");
    }
       
    public String askForCustomerName(){
        boolean validName = false;
        String customerName;
        do {
             customerName = io.readString("Please enter Customer Nmae:");
            if (customerName.matches("[\\w\\s\\.,]+")){ //regular expression used to check if customer name is not blank, allowed to contain [a-z][0-9] as well as periods and comma characters. “Acme, Inc.” is a valid name
                validName = true;
            }else{ 
                io.print("Sorry Customer name must start with a capital letter, may not be blank, allowed to contain [a-z][0-9] as well as periods and comma characters for e.g. “Acme, Inc.” is a valid name");
            }   
        }while (!validName);
       return customerName;
    }
    
    public String askForCustomerNameForEdit(){
        boolean validName = false;
        String customerName;
        do {
             customerName = io.readString("Please enter Customer Nmae:");
            if (customerName.matches("[\\w\\s\\.,]+") || customerName.isEmpty()){ //regular expression used to check if customer name is not blank, allowed to contain [a-z][0-9] as well as periods and comma characters. “Acme, Inc.” is a valid name
                validName = true;
            }else{ 
                io.print("Sorry Customer name must start with a capital letter, may not be blank, allowed to contain [a-z][0-9] as well as periods and comma characters for e.g. “Acme, Inc.” is a valid name");
            }   
        }while (!validName);
       return customerName;
    }
    
    public String askForState(){
        String stateAbriviation = io.readString("Please enter State abbrivation:");
         if (!(stateAbriviation.isEmpty())){
             stateAbriviation = stateAbriviation.toUpperCase();
         }
         return stateAbriviation;
    }
    public String askForProductType(){//making sure the input matches the input as it is in the file
        String productType = io.readString("Please enter Product type:");
        String productTypeFormatted;
        if (!(productType.isEmpty())){
            productTypeFormatted = productType.substring(0, 1).toUpperCase() + productType.substring(1);
            return productTypeFormatted;
        }
        return productType;
    }
    public BigDecimal askForArea(){
        return io.readBigDecimal("Please enter the area in sq ft. Please note the minimum valid area is 100sq ft", new BigDecimal("100"));
    }

      public boolean askToConfirm(Order order, String message){
       displaySummaryOfOder(order);
       io.print(message);
       boolean returnType = false;
       boolean validInput = false;
       do{
           String response = io.readString("Enter Y/N (or yes or no)");
       if (response.equals("Y") || response.equals("y") || response.equals("Yes") || response.equals("yes")){
           returnType = true;
           validInput = true;
       }else if (response.equals("N") || response.equals("n") || response.equals("No") || response.equals("no")){
           returnType = false;
           validInput = true;
       }
       }while(!validInput);
       return returnType;
    }
    
    public void displaySummaryOfOder(Order order){
        io.print("===Summary of Order ===");
        io.print("Order Date: " + String.valueOf(order.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))));//EDITED
        io.print("Oder Number: " + String.valueOf(order.getOrderNumber()));
        io.print("Customer Name: " + order.getCustomerName()); 
        io.print("State: " + order.getState());
        io.print("Tax rate: " + String.valueOf(order.getTaxRate()));
        io.print("Product type: " + order.getProductType());
        io.print("Area: " + String.valueOf(order.getArea()));
        io.print("Cost per Squarefoot: £" + String.valueOf(order.getCostPerSquareFoot()));
        io.print("LaborCostPerSquareFoot £" + String.valueOf(order.getLaborCostPerSquareFoot()));
        io.print("Material cost: £" + String.valueOf(order.getMaterialCost()));
        io.print("Labor cost £" + String.valueOf(order.getLaborCost()));
        io.print("Tax: " + String.valueOf(order.getTax()));
        io.print("Total: " + String.valueOf(order.getTotal()));
    }
   
    public void displayErrorMessage(String message) {
        io.print("===Error===");
        io.print(message);
        io.readString("Press the enter key to continue");
    }
    
    public void displayOrderAddResult(String message) {
        io.print(message);
        io.readString("Press the enter key to continue");
    }
    
    public void displayRemoveResult(String message) {
        io.print(message);
        io.readString("Press the enter key to continue");
    }

    public void displayProducts(Map<String, Product> products) {
        Collection<Product> collectionOfProducts = products.values();
        List<Product> productsList = collectionOfProducts.stream().collect(Collectors.toList());
        io.print("===List of available products===");
        io.print("Product Type | Cost per square foot |  Labor cost per square foot");
        for (Product product: productsList){
              String productInfo = String.format("%s: £%s £%s", product.getProductType(), product.getCostPerSquareFoot(), product.getLaborCostPerSquareFoot());
              io.print(productInfo);
            }
        }
    
    public void displayStates(Map<String, Tax> taxes) {
        Collection<Tax> collectionOfTaxes = taxes.values();
        List<Tax> taxessList = collectionOfTaxes.stream().collect(Collectors.toList());
        io.print("===List of available States with their Tax rates===");
        io.print("State abbreviation | State name |  Tax rate");
        for (Tax tax: taxessList){
              String productInfo = String.format("%s: %s %s", tax.getState(), tax.getStateName(), tax.getTaxRate());
              io.print(productInfo);
            }
        }

    public void displayOrderEditSuccessBanner(Order order){
        if(order != null){
            io.print("==Order edit succesfull!==");
        }else{
            io.print("==Order edit unsuccessfull==");
        }
        io.readString("Press the enter key to continue");
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!");
    }

    public void displayUnknownCommandBanner() {
    io.print("Unknown Command!");
    io.readString("Press the enter key to continue");
    } 

    public void displayExportDataSuccessBanner(String data_Exported_succesfully) {
          io.print(data_Exported_succesfully);
          io.readString("Press the enter key to continue");
    }
    
    public void displayOrdersBanner(){
        io.print("========Displaying orders========");
    }
}
