package com.mthree.flooringmastery.dao;

import com.mthree.flooringmastery.dto.Order;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.mthree.flooringmastery.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryDaoFileImplTest {

    FlooringMasteryDao testDao;
    LocalDate orderDate = LocalDate.now().plusDays(1);

    public FlooringMasteryDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception{//this method will replace the directory by making a new directory of the same name
//        String testFile = "TestOrders/";
//        testDao = new FlooringMasteryDaoFileImpl(testFile);
//
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("FlooringMasteryDaoFileImpl", FlooringMasteryDaoFileImpl.class);

        File file = new File("TestOrders");
        file.mkdir();
    }


    @Test
    public void testAddOrderCalculation() throws Exception{//TEST PASSED
        //ORDER NUMBER 1 WITH DATE 2013-06-01:
        //OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total
        //1::Ada Lovelace::CA::25.00::Tile::249.00::3.50::4.15::871.50::1033.35::476.21::2381.06

        //ABOVE ORDER NUMBER 1 WITH DATE 2013-06-01 WAS PROVIDED BY SOFTWARE GUILD, I WILL BE USING
        //THIS ORDER'S INFORMATION TO TEST WHETHER MY FLOORING MASTERY APPLICATION DOES
        //THE CORRECT CALCULATION FOR VARIOUS INFORMATION ABOUT THE ORDER LISTED BELOW:
        // TaxRate, CostPerSquareFoot, LaborCostPerSquareFoot, MaterialCost, LaborCost, Tax and Total

        //below I am setting the same field values as in the provided sample data file 'Orders_06012013.txt' to check if method calculates the correct total
        //i.e. my method should return the same total for this object as the one stored in the record
        Order order1 = new Order(1);
        order1.setOrderDate(LocalDate.now().plusDays(1));
        order1.setCustomerName("Ada Lovelace");
        order1.setState("CA");
        order1.setProductType("Tile");
        order1.setArea(new BigDecimal("249.00"));

        order1 = testDao.addOrder(order1);//this method will caluclate the rest of the fields for this order 1 object
        //The fields are TaxRate, CostPerSquareFoot, LaborCostPerSquareFoot, MaterialCost, LaborCost, Tax and Total

        assertEquals(order1.getTaxRate().compareTo(new BigDecimal("25.00")), 0);
        assertEquals(order1.getCostPerSquareFoot().compareTo(new BigDecimal("3.50")), 0);
        assertEquals(order1.getLaborCostPerSquareFoot().compareTo(new BigDecimal("4.15")), 0);
        assertEquals(order1.getMaterialCost().compareTo(new BigDecimal("871.50")), 0);
        assertEquals(order1.getLaborCost().compareTo(new BigDecimal("1033.35")), 0);
        assertEquals(order1.getTax().compareTo(new BigDecimal("476.21")), 0);
        assertEquals(order1.getTotal().compareTo(new BigDecimal("2381.06")), 0);
    }

    @Test
    public void testAddOrderOrderIsAdded() throws Exception{//TEST PASSED
        Order order1 = new Order(1);//will become orderNumber 2
        order1.setOrderDate(LocalDate.now().plusDays(1));
        order1.setCustomerName("Jhon");
        order1.setState("TX");
        order1.setProductType("Wood");
        order1.setArea(new BigDecimal("142.00"));
        order1 = testDao.addOrder(order1);//this method will caluclate the rest of the fields for this order 1 object
        testDao.orderIsConfirmed(order1);//this method will add the order to an orders map andwrite it to a file, here it is being used to check if the order is added to the map

        List<Order> ordersList = testDao.getOrdersForSpecifcDate(order1.getOrderDate());//getting a list of orders which includes order1
        assertNotNull(ordersList, "The list of Orders must not null");

        assertEquals(1, ordersList.size(),"List of Orders should have 1 Order.");//this is because one order was added

        assertTrue(testDao.getOrdersForSpecifcDate(order1.getOrderDate()).contains(order1),"The list of Orders should include Order1.");
    }

    @Test
    public void testEditOrder() throws StateException, ProductTypeException, FlooringMasteryPersistenceException, OrderNumberException, OrderDateException {//Test passed
        Order order1 = new Order(1);
        order1.setOrderDate(LocalDate.now().plusDays(1));
        order1.setCustomerName("Jhon");
        order1.setState("TX");
        order1.setProductType("Wood");
        order1.setArea(new BigDecimal("142.00"));

        order1 = testDao.addOrder(order1);
        testDao.orderIsConfirmed(order1);

        List<Order> ordersList = testDao.getOrdersForSpecifcDate(order1.getOrderDate());

        assertNotNull(ordersList, "The list of Orders must not null");

        assertTrue(testDao.getOrdersForSpecifcDate(order1.getOrderDate()).contains(order1), "The list of Orders should include Order1.");

        order1.setState("CA");

        Order editedOrder = testDao.editOrder(order1);

        assertEquals(editedOrder.getState(), order1.getState());
    }

    @Test
    public void testRemoveOrderAndGetOrdersForSpecifcDate() throws Exception{//TEST PASSED
        Order order1 = new Order(1);
        order1.setOrderDate(LocalDate.now().plusDays(1));
        order1.setCustomerName("Jhon");
        order1.setState("TX");
        order1.setProductType("Wood");
        order1.setArea(new BigDecimal("149.00"));

        order1 = testDao.addOrder(order1);//this method will caluclate the rest of the fields for this order 1 object
        testDao.orderIsConfirmed(order1);

        Order order2 = new Order(2);
        order2.setOrderDate(LocalDate.now().plusDays(1));
        order2.setCustomerName("Mary");
        order2.setState("CA");
        order2.setProductType("Tile");
        order2.setArea(new BigDecimal("120.00"));

        order2 = testDao.addOrder(order2);//this method will caluclate the rest of the fields for this order 1 object
        testDao.orderIsConfirmed(order2);//this method will add the order to an orders map andwrite it to a file, here it is being used to check if the order is added to the map

        List<Order> ordersList = testDao.getOrdersForSpecifcDate(order1.getOrderDate());//*******getting a list of orders which includes order1
        assertNotNull(ordersList, "The list of Orders should not be null");
        assertEquals(2, ordersList.size(),"List of Orders should have 2 Orders.");//this is because order and order 2 are of the same date

        assertTrue(testDao.getOrdersForSpecifcDate(order1.getOrderDate()).contains(order1),"The list of Orders should include Order1.");
        assertTrue(testDao.getOrdersForSpecifcDate(order2.getOrderDate()).contains(order2),"The list of Orders should include Order2.");


        testDao.removeOrder(order1);//this method will remove order1 from the system
        ordersList = testDao.getOrdersForSpecifcDate(order1.getOrderDate());

        assertNotNull(ordersList, "The list of Orders must not be null");
        assertEquals(1, ordersList.size(),"List of Orders should now have 1 order.");//this is because only one order (order1) for that specifc date now exists

        assertFalse(testDao.getOrdersForSpecifcDate(order1.getOrderDate()).contains(order1),"The list of Orders should not include Order1 as it was removed.");
        assertTrue(testDao.getOrdersForSpecifcDate(order2.getOrderDate()).contains(order2),"The list of Orders should still include Order2.");
    }

    @Test
    public void testExportAllOrdersAsBackup() throws StateException, ProductTypeException, FlooringMasteryPersistenceException {
        File file = new File("TestBackup");
        file.mkdir();

        Order order1 = new Order(1);
        order1.setOrderDate(LocalDate.now().plusDays(1));
        order1.setCustomerName("Jhon");
        order1.setState("TX");
        order1.setProductType("Wood");
        order1.setArea(new BigDecimal("149.00"));
        order1 = testDao.addOrder(order1);
        testDao.orderIsConfirmed(order1);

        Order order2 = new Order(2);
        order2.setOrderDate(LocalDate.now().plusDays(1));
        order2.setCustomerName("Mary");
        order2.setState("CA");
        order2.setProductType("Tile");
        order2.setArea(new BigDecimal("120.00"));
        order2 = testDao.addOrder(order2);
        testDao.orderIsConfirmed(order2);

        testDao.exportAllOrdersBackup();

        assertNotEquals(file.length(), 0);
    }
}