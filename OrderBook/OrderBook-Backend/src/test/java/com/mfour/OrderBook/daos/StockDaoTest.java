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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StockDaoTest {
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
    public void testAddGetStock() {
        List<Stock> retrievedList = stockDao.findAll();
        assertEquals(0, retrievedList.size());

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        //ACT
        stockDao.save(testStock);
        int savedId = testStock.getId();

        Stock retrievedStock = stockDao.findById(savedId).get();

        //ASSERT
        assertEquals(testStock, retrievedStock);
    }

    @Test
    public void testGetAllStocks() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        Stock testStockTwo = new Stock();
        testStockTwo.setSymbol("TSLA");
        testStockTwo.setName("Tesla");
        stockDao.save(testStockTwo);

        //ACT
        List<Stock> stocks = stockDao.findAll();

        //ASSERT
        assertEquals(2, stocks.size());
        assertTrue(stocks.contains(testStock));
        assertTrue(stocks.contains(testStockTwo));
    }

    @Test
    public void testUpdateStocks() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        stockDao.save(testStock);
        int savedId = testStock.getId();;

        //ACT ASSERT BEFORE UPDATE
        Stock retrievedStockFromDao = stockDao.findById(savedId).get();
        assertEquals(testStock, retrievedStockFromDao);

        //ACT ASSERT DURING UPDATE
        testStock.setName("Alphabet Inc");
        stockDao.save(testStock);
        assertNotEquals(testStock, retrievedStockFromDao);

        //ACT AND ASSERT AFTER UPDATE
        retrievedStockFromDao = stockDao.findById(savedId).get();
        assertEquals(testStock, retrievedStockFromDao);
    }

    @Test
    public void testDeleteStocks() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        stockDao.save(testStock);
        int savedId = testStock.getId();;

        //ACT ASSERT BEFORE DELETE
        Stock retrievedStockFromDao = stockDao.findById(savedId).get();
        assertEquals(testStock, retrievedStockFromDao);

        //ACT ASSERT AFTER DELETE
        stockDao.delete(testStock);
        List<Stock> retrievedStockListFromDao = stockDao.findAll();
        assertEquals(0, retrievedStockListFromDao.size());

        assertTrue(stockDao.findById(savedId).isEmpty());
    }

    @Test
    public void testGetIncompleteStocks() {
        List<Stock> retrievedList = stockDao.findAll();
        assertEquals(0, retrievedList.size());

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        Stock testStockTwo = new Stock();
        testStockTwo.setSymbol("TSLA");
        testStockTwo.setName("Tesla");
        stockDao.save(testStockTwo);

        Stock testStockThree = new Stock();
        testStockThree.setSymbol("AAPL");
        testStockThree.setName("Apple");
        stockDao.save(testStockThree);

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
        testOrderTwo.setStock(testStockTwo);
        testOrderTwo.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testOrderTwo.setSize(300);
        testOrderTwo.setTime(LocalDateTime.now().withNano(0));
        testOrderTwo.setComplete(true);
        ordersDao.save(testOrderTwo);

        Order testOrderThree = new Order();
        testOrderThree.setBuyOrSell(BuyOrSell.BUY);
        testOrderThree.setMpidInfo(testMpidInfo);
        testOrderThree.setStock(testStockThree);
        testOrderThree.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testOrderThree.setSize(300);
        testOrderThree.setTime(LocalDateTime.now().withNano(0));
        testOrderThree.setComplete(false);
        ordersDao.save(testOrderThree);

        //ACT
        List<String> retrievedIncompleteSymbolList = stockDao.getIncompleteSymbols();

        //ASSERT
        assertEquals(2,retrievedIncompleteSymbolList.size());

        assertTrue(retrievedIncompleteSymbolList.contains("GOOG"));
        assertTrue(retrievedIncompleteSymbolList.contains("AAPL"));

        assertFalse(retrievedIncompleteSymbolList.contains("TSLA"));
    }

    @Test
    public void testGetStockBySymbol() {
        List<Stock> retrievedList = stockDao.findAll();
        assertEquals(0, retrievedList.size());

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        //ACT
        stockDao.save(testStock);
        int savedId = testStock.getId();

        Stock retrievedStock = stockDao.getStockBySymbol("GOOG");

        //ASSERT
        assertEquals(testStock, retrievedStock);

    }


}
