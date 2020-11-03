package com.mthree.flooringmastery.service;
import com.mthree.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.mthree.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author umairsheikh
 */
public class FlooringMasteryServiceLayerImplTest {
    
      private FlooringMasteryServiceLayer service;

      public FlooringMasteryServiceLayerImplTest() {

//        FlooringMasteryDao dao = new FlooringMasteryeDaoStubImpl();
//        FlooringMasteryAduitDao aduitDao = new FlooringMasteryAuditDaoStubImpl();
//        service = new FlooringMasteryServiceLayerImpl(dao, aduitDao);

          ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
          service = ctx.getBean("serviceLayer", FlooringMasteryServiceLayer.class);
      }
    
    
    @Test
    public void testCreateOrder(){//Test passed
        Order order = new Order(1);
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setCustomerName("Ada Lovelace"); 
        order.setState("TX"); //edited from CA
        order.setProductType("Wood");//edited from tile
        order.setArea(new BigDecimal("109.00"));//edited from 249.00
      
        Order createdOrder;
        try {
           createdOrder = service.createOrder(order);
        } catch(ProductTypeException | FlooringMasteryPersistenceException | StateException ex){
                    fail("Order was valid. No exception should have been thrown.");
        }
        }
    
    @Test
    public void testConfirmAndAddOrderWithCorrectStateAndProductType(){//Test passed
        Order order = new Order();
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setCustomerName("Ada Lovelace"); 
        order.setState("TX"); //edited from CA
        order.setProductType("Carpet");//edited from tile
        order.setArea(new BigDecimal("109.00"));//edited from 249.00
        Order addedOrder;
        try { 
            service.validateState(order.getState());//this business logic will make sure that the a valid state abbrivation is enterd by the user
            service.validateProductType(order.getProductType());//this business logic will make sure that a valid product type is enterd
            service.createOrder(order);//this method will then calculate, create and add the values of the rest of the order's fields
           addedOrder = service.confirmOrder(order);//this method is called in the controller once the user confirms they want to add the order and here I am simply calling this method with the
           //the order object that has been created
        } catch(ProductTypeException | FlooringMasteryPersistenceException | StateException ex){
                    fail("Order was valid. No exception should have been thrown." + ex.getMessage());
        }
        } 

    @Test
    public void testCreateInvalidStateOrder(){//Test passed
        Order order = new Order();
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setCustomerName("Will Smith"); 
        order.setState("XYZ"); //edited from CA
        order.setProductType("Wood");//edited from tile
        order.setArea(new BigDecimal("109.00"));//edited from 249.00

        Order createdOrder;
        try { 
           service.validateState(order.getState());//Before creating an order I use validateState business logic from my service layer to validate the users input
           //Once it is validated then only the  service.createOrder(order) method is executed. I have chosen to do this, this way because if I added the validateState functionality 
           //within the createOrder method then the user would have to wait till they have entered everything before they can find out that they enterd something wrong 
           createdOrder = service.createOrder(order);
        } catch(ProductTypeException | FlooringMasteryPersistenceException ex){
            fail("Wrong exception thrown");
        }catch( StateException e){
            return;
        }
        }

        @Test
        public void testCreateInvalidProductTypeOrder(){//Test passed
        Order order = new Order();
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setCustomerName("Will Smith"); 
        order.setState("TX"); 
        order.setProductType("Metal");
        order.setArea(new BigDecimal("109.00"));

        Order createdOrder;
        try { 
           service.validateProductType(order.getProductType());
           createdOrder = service.createOrder(order);
        } catch(StateException | FlooringMasteryPersistenceException ex){
            fail("Wrong exception thrown");
        }catch( ProductTypeException e){
            return;
        }
        }

       @Test
        public void testValidateOrderDateForSearchingAnOrder(){//Test passed was able to search for order data successfully
        Order order = new Order();
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setCustomerName("Ada Lovelace"); 
        order.setState("TX"); //edited from CA
        order.setProductType("Wood");//edited from tile
        order.setArea(new BigDecimal("109.00"));//edited from 249.00
        try {
             service.validateOrderDate(order.getOrderDate());//in the applicaition this would validate the order against the orders from the persisted order data in the files which is loaded to an orders map
             //for testing purposes this will confirm it based on the order object in the stub dao
        } catch(FlooringMasteryPersistenceException |  OrderDateException ex){
                    fail("Order date was valid. No exception should have been thrown.");
        }
        }
        
        @Test
        public void testValidateInvalidOrderDateForSearchingAnOrder(){//Test passed
        Order order = new Order();
        order.setOrderDate(LocalDate.now().plusDays(2));
        order.setCustomerName("Ada Lovelace"); 
        order.setState("TX"); 
        order.setProductType("Wood");
        order.setArea(new BigDecimal("109.00"));
        try {
             service.validateOrderDate(order.getOrderDate());
        } catch(FlooringMasteryPersistenceException  ex){
                    fail("Invalid Exception thrown.");
        }catch(OrderDateException ex){
            return;
        }
        }

}

