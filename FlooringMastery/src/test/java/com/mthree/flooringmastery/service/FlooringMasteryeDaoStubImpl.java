/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooringmastery.service;

import com.mthree.flooringmastery.dao.FlooringMasteryDao;
import com.mthree.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mthree.flooringmastery.dto.Order;
import com.mthree.flooringmastery.dto.Product;
import com.mthree.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;                
/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryeDaoStubImpl implements FlooringMasteryDao {

    public Order order;

    public FlooringMasteryeDaoStubImpl() {
        order = new Order(1);
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.50"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.21"));
        order.setTotal(new BigDecimal("2381.06"));
    }

    @Override//PASS-THOUGH METHOD-TESTED SEPARATELY IN 'FlooringMasteryDaoFileImplTest'
    public Order editOrder(Order orderUserEntered) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException, ProductTypeException, StateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override//PASS-THOUGH METHOD-TESTED SEPARATELY IN 'FlooringMasteryDaoFileImplTest'
    public Order removeOrder(Order orderUserEntered) throws OrderDateException, OrderNumberException, FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportAllOrdersBackup() throws FlooringMasteryPersistenceException {//PASS-THOUGH METHOD-TESTED SEPARATELY IN 'FlooringMasteryDaoFileImplTest'
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order addOrder(Order order) throws FlooringMasteryPersistenceException, ProductTypeException, StateException {
        Product productType = productType(order.getProductType());
        order.setCostPerSquareFoot(productType.getCostPerSquareFoot());
        order.setMaterialCost(order.getArea().multiply(order.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCostPerSquareFoot(productType.getLaborCostPerSquareFoot());
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
        Tax tax = getTheTax(order.getState());//here 'state' is the abbrivation of the state and not the whole state name, state name is stored in 'stateName', I have kept to this naming convention because this is how this information was written in the data provided
        order.setTaxRate(tax.getTaxRate());
        order.setTax((order.getMaterialCost().add(order.getLaborCost())).multiply((order.getTaxRate().divide(new BigDecimal("100")))).setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax()));
        return order;
    }

    @Override
    public Map<String, Product> getDataProducts() throws FlooringMasteryPersistenceException {
        return null;
    }


    @Override
    public Tax getTheTax(String stateUserEntered) throws StateException, FlooringMasteryPersistenceException {
        Tax tax = new Tax();
        tax.setState("TA");
        tax.setStateName("Texas");
        tax.setTaxRate(new BigDecimal("4.45"));
        return tax;
    }

    @Override
    public Product productType(String productUserEntered) throws ProductTypeException, FlooringMasteryPersistenceException {
        Product product = new Product();
        product.setProductType("Carpet");
        product.setCostPerSquareFoot(new BigDecimal("2.25"));
        product.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        return product;
    }

    @Override
    public LocalDate searchForTheOrderDate(LocalDate dateUserEntered) throws OrderDateException, FlooringMasteryPersistenceException {
        if (dateUserEntered.compareTo(this.order.getOrderDate())==0){
            return this.order.getOrderDate();
        }
        return null;
    }

    @Override
    public int searchForTheOrderNumber(int userEnteredOrderNumber) throws OrderNumberException, FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override//PASS-THOUGH METHOD-TESTED SEPARATELY IN 'FlooringMasteryDaoFileImplTest'
    public Order getTheOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order orderIsConfirmed(Order order) throws FlooringMasteryPersistenceException {
        if (order!= null){
            return order;
        }
        return null;
    }

    @Override
    public Map<String, Tax> getAllStatesWithTaxInfo() throws FlooringMasteryPersistenceException {
        return null;
    }

    @Override//PASS-THOUGH METHOD-TESTED SEPARATELY IN 'FlooringMasteryDaoFileImplTest'
    public List<Order> getOrdersForSpecifcDate(LocalDate orderDate) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
