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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao{
    //the first 4 fields of this class corresponde to the file names that various information will be stored in
    private final String BACKUP_DATA_EXPORT_FILE;
    private final String DATA_PRODUCTS_FILE;
    private final String DATA_TAXES_FILE;
    private String ORDERS_DATES_FILE;//here Orders is the folder that the Orders_date.txt file is in,
    //where 'DATES' stands for the delivery date that the user entered also known as the order date as discussed in class
    public static final String DELIMITER = "::"; //I have chosen the :: symbol as a delimeter to be used to seprate each field within the txt files because a name/company name could include commas as discussed in class
    
    private Map<Integer, Order> orders = new HashMap<>();
    private Map<String, Tax> taxRates = new HashMap<>();
    private Map<String, Product> products = new HashMap<>();
    private String ordersDirectoryName;
    public FlooringMasteryDaoFileImpl() {
        this.BACKUP_DATA_EXPORT_FILE = "Backup/DataExport.txt";
        this.DATA_PRODUCTS_FILE = "Data/Products.txt";
        this.DATA_TAXES_FILE = "Data/Taxes.txt";
        this.ORDERS_DATES_FILE = "Orders/";
        this.ordersDirectoryName = "Orders";
    }
    
    public FlooringMasteryDaoFileImpl(String fileName){//This constuctor is used for testing purposes, it allows me to define a different directory to store the orders in
        this.BACKUP_DATA_EXPORT_FILE = "TestBackup/DataExport.txt";
        this.DATA_PRODUCTS_FILE = "Data/Products.txt";
        this.DATA_TAXES_FILE = "Data/Taxes.txt";
        this.ORDERS_DATES_FILE = fileName;
        this.ordersDirectoryName = "";
    }
    
    
    @Override
    public Order orderIsConfirmed(Order order) throws FlooringMasteryPersistenceException{//this method will store the new order or an edited order and write them to a file
        loadOrdersFromFile(this.ordersDirectoryName);
        Order newOrderOrEditedOrder = order;
                
        orders.put(newOrderOrEditedOrder.getOrderNumber(), newOrderOrEditedOrder);
        
        String orderDate = newOrderOrEditedOrder.getOrderDate().format(DateTimeFormatter.ofPattern("MMddyyyy"));//using the DateTimeFormatter of the DateTime  will allow me to name the files that don't exist when adding an order for a new date with the correct date format
        String filePath  = ORDERS_DATES_FILE + "Orders_" + orderDate + ".txt";
        writeToFile(filePath, newOrderOrEditedOrder.getOrderDate(), false);
        return newOrderOrEditedOrder;
    }  
    
    @Override
    public Order addOrder(Order order) throws FlooringMasteryPersistenceException, ProductTypeException, StateException {
        int highestOrderNumber = 0;
        loadOrdersFromFile(this.ordersDirectoryName);
        loadTaxRatesFromFile();
        loadProductTypesFromFile();
        
        List <Integer> orderNumbers = orders.values().stream().map((o) -> o.getOrderNumber()).collect(Collectors.toList());
        for (int number : orderNumbers){
            if (highestOrderNumber < number){
                highestOrderNumber = number;
            }
        }
        int orderNumber = highestOrderNumber +1;//getting the highest order number from the file and setting the order number to that oder number +1 
        order.setOrderNumber(orderNumber);
        //Finding the costPerSquareFoot based on the product type the user chose
        Product productType = productType(order.getProductType());
        order.setCostPerSquareFoot(productType.getCostPerSquareFoot());
        order.setMaterialCost(order.getArea().multiply(order.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
        //Finding the LaborCostPerSquareFoot based on the product type the user chose
        order.setLaborCostPerSquareFoot(productType.getLaborCostPerSquareFoot()); 
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
        //Finding the tax rate in file Taxes, laborcost is in the Products file
        Tax tax = getTheTax(order.getState());//here 'state' is the abbrivation of the state and not the whole state name, state name is stored in 'stateName', I have kept to this naming convention because this is how this ifnormation was written in the data provided
        order.setTaxRate(tax.getTaxRate());
        //setting the tax for the order
        order.setTax((order.getMaterialCost().add(order.getLaborCost())).multiply((order.getTaxRate().divide(new BigDecimal("100")))).setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax()));
        //above I have set scale to 2 and rounding to RoundingMode.HALF_UP as suggested in one of the lessons
        return order;
    }
    
    @Override
    public Order editOrder(Order orderUserEntered) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException, ProductTypeException, StateException {
        Order editedOrder;
        loadOrdersFromFile(this.ordersDirectoryName);
        loadTaxRatesFromFile();
        loadProductTypesFromFile();
        
        LocalDate dateOfOrder = searchForTheOrderDate(orderUserEntered.getOrderDate());
        String date = ""; //empty string because this method will write to Order_date.txt file and not Backup/DataExport.txt file
        int orderNumber = searchForTheOrderNumber(orderUserEntered.getOrderNumber());
        Set<Integer> keys = orders.keySet();
        for (int key : keys){
            if ((orders.get(key).getOrderNumber() == orderNumber) && (orders.get(key).getOrderDate().compareTo(dateOfOrder) == 0)){
                
                if (!orderUserEntered.getCustomerName().equals("")){
                    orders.get(key).setCustomerName(orderUserEntered.getCustomerName());
                }
                boolean changed = checkIfStateProductAreaChangedAndChange(orders.get(key), orderUserEntered);
                if (changed == true){
                    Product productType = productType(orders.get(key).getProductType());
                    orders.get(key).setCostPerSquareFoot(productType.getCostPerSquareFoot());
                    orders.get(key).setMaterialCost(orders.get(key).getArea().multiply(orders.get(key).getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
 
                    orders.get(key).setLaborCostPerSquareFoot(productType.getLaborCostPerSquareFoot()); 
                    orders.get(key).setLaborCost(orders.get(key).getArea().multiply(orders.get(key).getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
                    Tax tax = getTheTax(orders.get(key).getState());//here state is the abbrivation of the state and not the whole state name, which is stored in stateName
                    orders.get(key).setTaxRate(tax.getTaxRate());
                    //setting the tax for the order
                    orders.get(key).setTax((orders.get(key).getMaterialCost().add(orders.get(key).getLaborCost())).multiply((orders.get(key).getTaxRate().divide(new BigDecimal("100")))).setScale(2, RoundingMode.HALF_UP));
                    orders.get(key).setTotal(orders.get(key).getMaterialCost().add(orders.get(key).getLaborCost()).add(orders.get(key).getTax()));
                    return orders.get(key);
                }else {
                    return orders.get(key);
                }
            }
        }
        return null; //returning null incase there was nothing to edit or an error occured
    }
    
 
    private boolean checkIfStateProductAreaChangedAndChange(Order storedOrder, Order orderUserEntered) throws FlooringMasteryPersistenceException {
        loadTaxRatesFromFile();
        boolean changed = false;
        if (!orderUserEntered.getState().equals("") && (!(storedOrder.getState().equals(orderUserEntered.getState())))){
            changed = true;
            storedOrder.setState(orderUserEntered.getState());//state is the abbriviation of state, stateName is the state's name
        }
        if (!orderUserEntered.getProductType().equals("") && (!(storedOrder.getProductType().equals(orderUserEntered.getProductType())))){
            changed = true;
            storedOrder.setProductType(orderUserEntered.getProductType());//state is the abbriviation of state, stateName is the state's name
        }
        if ((orderUserEntered.getArea()!=null) && orderUserEntered.getArea().compareTo(new BigDecimal("100"))>=1 && orderUserEntered.getArea().compareTo(new BigDecimal("100"))!=0){//using String.valueOf to convert the BigDecimal type of the area to a stirng to see if the user 
            //and checking if the minimum square fot the user entered is greater then or equal to 100, where 1 represents exactly that greater then or equal to 100
            //and checking if the area is dffferent from the area currently stored
            changed = true;
            storedOrder.setArea(orderUserEntered.getArea());//state is the abbriviation of state, stateName is the state's name
        }
        return changed;
    }
 
    @Override
    public Order removeOrder(Order orderUserEntered) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException{
        loadOrdersFromFile(this.ordersDirectoryName);
        Order removedOrder = null;
        String orderDate = String.valueOf(orderUserEntered.getOrderDate().format(DateTimeFormatter.ofPattern("MMddyyyy")));
        String filePath = ORDERS_DATES_FILE +"Orders_"+orderDate+".txt";
        removedOrder = orders.remove(orderUserEntered.getOrderNumber());
        writeToFileRemoveRecord(filePath, removedOrder);
        return removedOrder;
    }
    
    public Order getTheOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException{
        loadOrdersFromFile(this.ordersDirectoryName);
        Set<Integer> keys = orders.keySet();
        for (int key : keys){
            if (orders.get(key).getOrderDate().compareTo(orderDate) ==0 && orders.get(key).getOrderNumber() == orderNumber){
                return orders.get(key);
            }
        }
        return null;
    }
    
    @Override
    public List<Order>  getOrdersForSpecifcDate(LocalDate orderDate) throws FlooringMasteryPersistenceException{
        loadOrdersFromFile(this.ordersDirectoryName);
        List<Order> ordersByDate = new ArrayList<>();
        Collection<Order> specifcOrdersByDate = orders.values();
        for (Order order:specifcOrdersByDate ){
            if (order.getOrderDate().compareTo(orderDate)==0){
                ordersByDate.add(order);
            }
        }
        return ordersByDate;
    } 
   

    @Override
    public void exportAllOrdersBackup() throws FlooringMasteryPersistenceException {    
    loadOrdersFromFile(this.ordersDirectoryName);
    writeToFile(BACKUP_DATA_EXPORT_FILE,null, true);//True tells the writeToFile method that this call is for exporting all the data to backup
    }

    @Override
    public Map<String, Product> getDataProducts() throws FlooringMasteryPersistenceException {//returns all the different products that were loaded in from the Products.txt file inside the 'Data' filder, hence the naming convention of getDataProducts
        loadProductTypesFromFile();//used to lead Products from the file onto the products map so that the products can be used with the application
        return products;
    }
    
    @Override
    public Product productType (String productUserEntered) throws ProductTypeException, FlooringMasteryPersistenceException{
        loadProductTypesFromFile();
        Collection <Product> currentProducts = products.values();
        for (Product product : currentProducts){
            if (product.getProductType().equals(productUserEntered)){
                return product;
            }
        }
        return null;
    }
    
    @Override
    public Tax getTheTax(String stateUserEntered) throws StateException, FlooringMasteryPersistenceException{//checks if the tax with the state name exists 
        loadTaxRatesFromFile();
        Collection <Tax> currentTaxes = taxRates.values();
        
        int loopNumber = 0;
        for (Tax tax : currentTaxes){
            if (tax.getState().equals(stateUserEntered)){
                return tax;
            }
        } 
        return null;
    }
    
    @Override
    public LocalDate searchForTheOrderDate(LocalDate dateUserEntered) throws OrderDateException, FlooringMasteryPersistenceException{
        loadOrdersFromFile(this.ordersDirectoryName);
        Set <Integer> keys =  orders.keySet();
        for (int key : keys){
        if (orders.get(key).getOrderDate().compareTo(dateUserEntered) == 0){
            return orders.get(key).getOrderDate();
            }
        }
        return null;
    }
    
    @Override
    public int searchForTheOrderNumber(int userEnteredOrderNumber) throws OrderNumberException, FlooringMasteryPersistenceException{
        loadOrdersFromFile(this.ordersDirectoryName);
        Set <Integer> keys =  orders.keySet();
        for (int key : keys){
        if (orders.get(key).getOrderNumber() == userEnteredOrderNumber){
            return orders.get(key).getOrderNumber();
            }
        }
        return -1;//returning -1 if the order number does not exist
    }
    
    //Unmarshelling - in this case the process of taking each record from the Orders file
    //and creating an object of it and then storing it into the  file 
    private Order unmarshellFlooringMasteryOrders(String orderAsText){//this method will be used to convert each orderAsText String (Orders record) into an object
        
        String[] orderTokens = orderAsText.split(DELIMITER);
        String orderNumber = orderTokens[0];//the first token is the order number
        String customerName = orderTokens[1];
        String state = orderTokens[2];
        String taxRate = orderTokens[3];
        String productType = orderTokens[4];
        String area = orderTokens[5];
        String costPerSquareFoot = orderTokens[6];
        String laborCostPerSquareFoot = orderTokens[7];
        String materialCost = orderTokens[8];
        String laborCost = orderTokens[9];
        String tax = orderTokens[10];
        String total = orderTokens[11]; 
                
        Order orderFromFile = new Order(Integer.parseInt(orderNumber));        
       
        orderFromFile.setCustomerName(customerName);
        orderFromFile.setState(state);
        orderFromFile.setTaxRate(new BigDecimal(taxRate));
        orderFromFile.setProductType(productType);
        orderFromFile.setArea(new BigDecimal(area));
        orderFromFile.setCostPerSquareFoot(new BigDecimal(costPerSquareFoot));
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(laborCostPerSquareFoot));
        orderFromFile.setMaterialCost(new BigDecimal(materialCost));
        orderFromFile.setLaborCost(new BigDecimal(laborCost));
        orderFromFile.setTax(new BigDecimal(tax));
        orderFromFile.setTotal(new BigDecimal(total));
        
        return orderFromFile;
    }
    
    private void loadOrdersFromFile(String ordersDirectoryName) throws FlooringMasteryPersistenceException{//will read the orders and store them in the 'orders' map
        Scanner scanner = null;
        File dir;
        if (ordersDirectoryName.length() != 0){
            try{
                dir = new File(ordersDirectoryName);
                File[] directoryListing = dir.listFiles();

                if (directoryListing != null) {
                    for (File file : directoryListing) {
                        String fileName = file.getName();
                        if (fileName.contains("Orders")){
                            String filePath = "Orders/" + fileName;
                            String orderDate = fileName.replace("Orders_", "");
                            orderDate = orderDate.replace(".txt", "");
                            //order date is the delivery date
                            LocalDate date = LocalDate.parse(orderDate, DateTimeFormatter.ofPattern("MMddyyyy"));
                            try{
                                scanner = new Scanner(new BufferedReader(new FileReader(filePath)));
                            }catch(FileNotFoundException e){
                                throw new FlooringMasteryPersistenceException(e.getMessage());
                            }
                            String currentLine;
                            Order currentOrder;
                            if(file.length()!=0){
                                scanner.nextLine();
                            }
                            while (scanner.hasNext()){
                                currentLine = scanner.nextLine();
                                currentOrder = unmarshellFlooringMasteryOrders(currentLine);
                                currentOrder.setOrderDate(date);
                                orders.put(currentOrder.getOrderNumber(), currentOrder);
                            }
                        }
                    }
                }
                scanner.close();
            }catch(NullPointerException e){
                throw new FlooringMasteryPersistenceException("No directories or files for orders");
            }
        }
    }
        
    private String marshellFlooringMasteryOrders(Order order, boolean writeDate){
        String itemRecordAsText;        
        String orderNumber = String.valueOf(order.getOrderNumber());
        String customerName = order.getCustomerName();
        String state = order.getState();
        String taxRate = String.valueOf(order.getTaxRate());
        String productType = order.getProductType();
        String area = String.valueOf(order.getArea());
        String costPerSquareFoot = String.valueOf(order.getCostPerSquareFoot());
        String laborCostPerSquareFoot = String.valueOf(order.getLaborCostPerSquareFoot());
        String materialCost = String.valueOf(order.getMaterialCost());
        String laborCost = String.valueOf(order.getLaborCost());
        String tax = String.valueOf(order.getTax());
        String total = String.valueOf(order.getTotal());
        
        String dateWithDilemeter ="";
        if (writeDate){
            dateWithDilemeter =  DELIMITER + order.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        }
        itemRecordAsText = orderNumber + DELIMITER + customerName + DELIMITER + state + DELIMITER + taxRate + DELIMITER
                + productType + DELIMITER + area + DELIMITER + costPerSquareFoot + DELIMITER + laborCostPerSquareFoot + DELIMITER 
                + materialCost + DELIMITER + laborCost + DELIMITER + tax + DELIMITER + total + dateWithDilemeter;
        return itemRecordAsText;
    }
    
    private void writeToFile(String fileName, LocalDate date, boolean forExport) throws FlooringMasteryPersistenceException{ //this method makes use of lambdas
   //this method will writes file to the 'Orders' directory or the 'Backup' directory based on the 'forExport' bolean parameter of the writeToFile() method
    File file = new File(fileName);
    
    PrintWriter writer;
    Set keys = orders.keySet();
    try{
        writer = new PrintWriter(new FileWriter(fileName));
    }catch(IOException e){
        throw new FlooringMasteryPersistenceException("Could not save the Item data.", e);
    }
    if (forExport == false){
        writer.println("OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total");//adding the title on the file which correspond to individual fields of the order's object
        keys.stream().forEach((k) -> {
            if (orders.get(k).getOrderDate().compareTo(date)==0){//used to only add orders for of the same date to the the same file
            String orderAsTextRecord = marshellFlooringMasteryOrders(orders.get(k), false);//false argument means that the file should not have order date stored inside of it, only the name of the file will include the order date as required
            writer.println(orderAsTextRecord);
        }
                }); 
    } else if (forExport == true){
         writer.println("OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total::OrderDate");      
         keys.stream().forEach((k) -> {
             String orderAsTextRecord = marshellFlooringMasteryOrders(orders.get(k), true);//true argument means that the file should have the order date stored inside of it as required
             writer.println(orderAsTextRecord);
             }); 
    }
        writer.flush();
        writer.close();
    }
    
    private void writeToFileRemoveRecord(String fileName, Order order) throws FlooringMasteryPersistenceException{ //this method will update the relevent file by reoving the relevent order's record
    PrintWriter writer;
    try{
        writer = new PrintWriter(new FileWriter(fileName));
    }catch(IOException e){
        throw new FlooringMasteryPersistenceException("Could not save the Order data.", e);
    }
    writer.println("OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total");

    LocalDate orderDate = order.getOrderDate();
    Set<Integer> keys = orders.keySet();    
    keys.stream()
                .forEach((key) -> {
                    if (orders.get(key).getOrderDate().compareTo(orderDate)==0){
                    String orderAsTextRecord = marshellFlooringMasteryOrders(orders.get(key), false);
                    writer.println(orderAsTextRecord);
                    };
                }); 
     writer.flush();
    }
    
    private Product unmarshellFlooringMasteryProducts(String productsAsText){//this method will be used to convert each productAsText String (record in the vending machine) into an object
        
        String[] productsTokens = productsAsText.split(DELIMITER);
        String productType = productsTokens[0];
        String costPerSquareFootStr = productsTokens[1];
        String laborCostPerSquareFootStr = productsTokens[2];
        
        BigDecimal costPerSquareFoot = new BigDecimal(costPerSquareFootStr);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(laborCostPerSquareFootStr);
                
        Product productFromFile = new Product();
        productFromFile.setProductType(productType);
        productFromFile.setCostPerSquareFoot(costPerSquareFoot);
        productFromFile.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        return productFromFile;
    }
    private void loadProductTypesFromFile() throws FlooringMasteryPersistenceException {
    
    File file = new File (DATA_PRODUCTS_FILE);
    Scanner scanner = null;
    try {
        scanner = new Scanner(new BufferedReader(new FileReader(DATA_PRODUCTS_FILE)));
    }catch(IOException e){
        throw new FlooringMasteryPersistenceException("-_- Could not read from Products file! The file may not exist");
    }
    
    Product currentProduct;
    String currentLine;
    if (file.length() != 0){
        scanner.nextLine();//used to skip the headings line
    }
    while (scanner.hasNext()){
        currentLine = scanner.nextLine();
        currentProduct = unmarshellFlooringMasteryProducts(currentLine);
        
        products.put(currentProduct.getProductType(), currentProduct);
    }
    scanner.close();
   }
    
    private Tax unmarshellFlooringMasteryTaxes(String taxesAsText){//this method will be used to convert each taxesAsText String  into an object
        
        String[] taxesTokens = taxesAsText.split(DELIMITER);
        String state = taxesTokens[0];
        String stateName = taxesTokens[1];
        String taxRateStr = taxesTokens[2];
        
        BigDecimal taxRate = new BigDecimal(taxRateStr);
                
        Tax taxFromFile = new Tax();
        taxFromFile.setState(state);
        taxFromFile.setStateName(stateName);
        taxFromFile.setTaxRate(taxRate);
        return taxFromFile;
    }
        
    private void loadTaxRatesFromFile() throws FlooringMasteryPersistenceException {
    
    File file = new File(DATA_TAXES_FILE);
        
    Scanner scanner;
    
    
    try {
        scanner = new Scanner(new BufferedReader(new FileReader(DATA_TAXES_FILE)));
    }catch(IOException e){
        throw new FlooringMasteryPersistenceException("-_- Could not read from Taxes file! The file may not exist");
    }
    
    Tax currentTax;
    String currentLine;

    if (file.length() != 0){
        scanner.nextLine();//skipping the headings line
    }
    while (scanner.hasNext()){
        currentLine = scanner.nextLine();
        currentTax = unmarshellFlooringMasteryTaxes(currentLine);
        
        taxRates.put(currentTax.getState(), currentTax);
    }
    scanner.close();
   }
    
    @Override
    public Map<String, Tax> getAllStatesWithTaxInfo() throws FlooringMasteryPersistenceException {
        loadTaxRatesFromFile();//used to load taxes from the file onto the 'taxRates' map so that the information about tax rates related to various states can be used with the application
        return taxRates;
    }
    
}