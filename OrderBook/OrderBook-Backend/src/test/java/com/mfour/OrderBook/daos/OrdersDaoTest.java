package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrdersDaoTest {

    @Autowired
    MpidInfoDao mpidInfoDao;

    @Autowired
    OrdersDao ordersDao;

    @Autowired
    StockDao stockDao;

    @Autowired
    TradesDao tradesDao;

    @Autowired
    UserInfoDao userInfoDao;

    @BeforeEach
    void setUp() {
        mpidInfoDao.deleteAll();
        ordersDao.deleteAll();
        stockDao.deleteAll();
        tradesDao.deleteAll();
        userInfoDao.deleteAll();
    }

    @Test
    public void testAddGetOrder() {
        List<Order> retrievedList = ordersDao.findAll();
        assertEquals(0, retrievedList.size());

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testOrder = new Order();
        testOrder.setBuyOrSell(BuyOrSell.BUY);
        testOrder.setMpidInfo(testMpidInfo);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP));
        testOrder.setSize(500);
        testOrder.setTime(LocalDateTime.now().withNano(0));
        testOrder.setComplete(false);

        //ACT
        ordersDao.save(testOrder);
        int savedId = testOrder.getId();

        Order retrievedOrder = ordersDao.findById(savedId).get();

        //ASSERT
        assertEquals(testOrder, retrievedOrder);
    }

    @Test
    public void testGetAllOrders() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testOrder = new Order();
        testOrder.setBuyOrSell(BuyOrSell.BUY);
        testOrder.setMpidInfo(testMpidInfo);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP));
        testOrder.setSize(500);
        testOrder.setTime(LocalDateTime.now().withNano(0));
        testOrder.setComplete(false);
        ordersDao.save(testOrder);

        Order testOrderTwo = new Order();
        testOrderTwo.setBuyOrSell(BuyOrSell.BUY);
        testOrderTwo.setMpidInfo(testMpidInfo);
        testOrderTwo.setStock(testStock);
        testOrderTwo.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testOrderTwo.setSize(300);
        testOrderTwo.setTime(LocalDateTime.now().withNano(0));
        testOrderTwo.setComplete(false);
        ordersDao.save(testOrderTwo);


        //ACT
        List<Order> orders = ordersDao.findAll();

        //ASSERT
        assertEquals(2, orders.size());
        assertTrue(orders.contains(testOrder));
        assertTrue(orders.contains(testOrderTwo));
    }

    @Test
    public void testUpdateOrder() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testOrder = new Order();
        testOrder.setBuyOrSell(BuyOrSell.BUY);
        testOrder.setMpidInfo(testMpidInfo);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP));
        testOrder.setSize(500);
        testOrder.setTime(LocalDateTime.now().withNano(0));
        testOrder.setComplete(false);
        ordersDao.save(testOrder);
        int savedId = testOrder.getId();

        //ACT ASSERT BEFORE UPDATE
        Order retrievedOrderFromDao = ordersDao.findById(savedId).get();
        assertEquals(testOrder, retrievedOrderFromDao);

        //ACT ASSERT DURING UPDATE
        testOrder.setSize(700);
        testOrder.setPrice(new BigDecimal(129.80).setScale(2, RoundingMode.HALF_UP));
        ordersDao.save(testOrder);
        assertNotEquals(testOrder, retrievedOrderFromDao);

        //ACT AND ASSERT AFTER UPDATE
        retrievedOrderFromDao = ordersDao.findById(savedId).get();
        assertEquals(testOrder, retrievedOrderFromDao);
    }

    @Test
    public void testDeleteOrder() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testOrder = new Order();
        testOrder.setBuyOrSell(BuyOrSell.BUY);
        testOrder.setMpidInfo(testMpidInfo);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP));
        testOrder.setSize(500);
        testOrder.setTime(LocalDateTime.now().withNano(0));
        testOrder.setComplete(false);
        ordersDao.save(testOrder);
        int savedId = testOrder.getId();

        //ACT ASSERT BEFORE DELETE
        Order retrievedOrderFromDao = ordersDao.findById(savedId).get();
        assertEquals(testOrder, retrievedOrderFromDao);

        //ACT AND ASSERT AFTER DELETE
        ordersDao.delete(testOrder);
        List<Order> retrievedOrdersListFromDao = ordersDao.findAll();
        assertEquals(0, retrievedOrdersListFromDao.size());

        assertTrue(ordersDao.findById(savedId).isEmpty());
    }

    @Test
    public void testGetAllBuyAllSellOrders() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testOrder = new Order();
        testOrder.setBuyOrSell(BuyOrSell.BUY);
        testOrder.setMpidInfo(testMpidInfo);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP));
        testOrder.setSize(500);
        testOrder.setTime(LocalDateTime.now().withNano(0));
        testOrder.setComplete(false);
        ordersDao.save(testOrder);

        Order testOrderTwo = new Order();
        testOrderTwo.setBuyOrSell(BuyOrSell.BUY);
        testOrderTwo.setMpidInfo(testMpidInfo);
        testOrderTwo.setStock(testStock);
        testOrderTwo.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testOrderTwo.setSize(300);
        testOrderTwo.setTime(LocalDateTime.now().withNano(0));
        testOrderTwo.setComplete(false);
        ordersDao.save(testOrderTwo);

        Order testOrderThree = new Order();
        testOrderThree.setBuyOrSell(BuyOrSell.SELL);
        testOrderThree.setMpidInfo(testMpidInfo);
        testOrderThree.setStock(testStock);
        testOrderThree.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testOrderThree.setSize(300);
        testOrderThree.setTime(LocalDateTime.now().withNano(0));
        testOrderThree.setComplete(false);
        ordersDao.save(testOrderThree);

        //ACT
        List<Order> buyOrders = ordersDao.getBuyAll();
        List<Order> sellOrders = ordersDao.getSellAll();

        //ASSERT
        assertEquals(2, buyOrders.size());
        assertEquals(1, sellOrders.size());

        assertTrue(buyOrders.contains(testOrder));
        assertTrue(buyOrders.contains(testOrderTwo));
        assertFalse(buyOrders.contains(testOrderThree));

        assertFalse(sellOrders.contains(testOrder));
        assertFalse(sellOrders.contains(testOrderTwo));
        assertTrue(sellOrders.contains(testOrderThree));
    }

    @Test
    public void testGetMinAndMaxPriceOrder() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testOrder = new Order();
        testOrder.setBuyOrSell(BuyOrSell.BUY);
        testOrder.setMpidInfo(testMpidInfo);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP));
        testOrder.setSize(500);
        testOrder.setTime(LocalDateTime.now().withNano(0));
        testOrder.setComplete(false);
        ordersDao.save(testOrder);

        Order testOrderTwo = new Order();
        testOrderTwo.setBuyOrSell(BuyOrSell.BUY);
        testOrderTwo.setMpidInfo(testMpidInfo);
        testOrderTwo.setStock(testStock);
        testOrderTwo.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testOrderTwo.setSize(300);
        testOrderTwo.setTime(LocalDateTime.now().withNano(0));
        testOrderTwo.setComplete(false);
        ordersDao.save(testOrderTwo);

        Order testOrderThree = new Order();
        testOrderThree.setBuyOrSell(BuyOrSell.SELL);
        testOrderThree.setMpidInfo(testMpidInfo);
        testOrderThree.setStock(testStock);
        testOrderThree.setPrice(new BigDecimal(345.15).setScale(2, RoundingMode.HALF_UP));
        testOrderThree.setSize(300);
        testOrderThree.setTime(LocalDateTime.now().withNano(0));
        testOrderThree.setComplete(false);
        ordersDao.save(testOrderThree);

        //ACT
        BigDecimal retrievedMin = ordersDao.getMinPrice("GOOG");
        BigDecimal retrievedMax = ordersDao.getMaxPrice("GOOG");

        //ASSERT
        assertEquals(1, retrievedMax.compareTo(retrievedMin));
    }




}
