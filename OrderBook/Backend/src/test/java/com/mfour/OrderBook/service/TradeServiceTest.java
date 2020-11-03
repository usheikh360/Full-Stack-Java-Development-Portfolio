package com.mfour.OrderBook.service;

import com.mfour.OrderBook.daos.*;
import com.mfour.OrderBook.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TradeServiceTest {

    @Autowired
    MpidInfoDao mpidInfoDao;

    @Autowired
    StockDao stockDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    OrdersDao ordersDao;

    @Autowired
    TradesDao tradesDao;

    @Autowired
    TradeService tradeService;

    @BeforeEach
    void setUp() {
        mpidInfoDao.deleteAll();
        ordersDao.deleteAll();
        stockDao.deleteAll();
        tradesDao.deleteAll();
        userInfoDao.deleteAll();
    }

    @Test
    void createTradeWithValidOrders() {
        //ARRANGE
        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");
        userInfoDao.save(testUser);
        int savedUserId = testUser.getId();

        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testBuyOrder = new Order();
        testBuyOrder.setBuyOrSell(BuyOrSell.BUY);
        testBuyOrder.setMpidInfo(testMpidInfo);
        testBuyOrder.setStock(testStock);
        testBuyOrder.setPrice(new BigDecimal(27.45).setScale(2, RoundingMode.HALF_UP));
        testBuyOrder.setSize(500);
        testBuyOrder.setTime(LocalDateTime.now().withNano(0));
        testBuyOrder.setComplete(false);
        ordersDao.save(testBuyOrder);
        int savedBuyOrderId = testBuyOrder.getId();

        Order testSellOrder = new Order();
        testSellOrder.setBuyOrSell(BuyOrSell.SELL);
        testSellOrder.setMpidInfo(testMpidInfo);
        testSellOrder.setStock(testStock);
        testSellOrder.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testSellOrder.setSize(300);
        testSellOrder.setTime(LocalDateTime.now().withNano(0));
        testSellOrder.setComplete(false);
        ordersDao.save(testSellOrder);
        int savedSellOrderId = testSellOrder.getId();

        try {
            String result = tradeService.createTrade(savedBuyOrderId, savedSellOrderId, savedUserId);
            assertEquals(result,"SUCCESS - new trade created" );
        } catch (ExceptionHandler exceptionHandler) {

        }
    }

    @Test
    void createTradeWithNotMatchingStocks() {
        //ARRANGE
        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");
        userInfoDao.save(testUser);
        int savedUserId = testUser.getId();

        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        Stock testStockTwo = new Stock();
        testStockTwo.setSymbol("AAPL");
        testStockTwo.setName("Apple");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testBuyOrder = new Order();
        testBuyOrder.setBuyOrSell(BuyOrSell.BUY);
        testBuyOrder.setMpidInfo(testMpidInfo);
        testBuyOrder.setStock(testStock);
        testBuyOrder.setPrice(new BigDecimal(27.45).setScale(2, RoundingMode.HALF_UP));
        testBuyOrder.setSize(500);
        testBuyOrder.setTime(LocalDateTime.now().withNano(0));
        testBuyOrder.setComplete(false);
        ordersDao.save(testBuyOrder);
        int savedBuyOrderId = testBuyOrder.getId();

        Order testSellOrder = new Order();
        testSellOrder.setBuyOrSell(BuyOrSell.SELL);
        testSellOrder.setMpidInfo(testMpidInfo);
        testSellOrder.setStock(testStock);
        testSellOrder.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testSellOrder.setSize(300);
        testSellOrder.setTime(LocalDateTime.now().withNano(0));
        testSellOrder.setComplete(false);
        ordersDao.save(testSellOrder);
        int savedSellOrderId = testSellOrder.getId();

        try {
            String result = tradeService.createTrade(savedBuyOrderId, savedSellOrderId, savedUserId);
        } catch (ExceptionHandler e) {
            assertEquals(e.getMessage(),"Incompatible stock - Please enter orders with the same Stock Symbol" );
        }
    }

    @Test
    void createTradeWithSellPriceHigher() {
        //ARRANGE
        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");
        userInfoDao.save(testUser);
        int savedUserId = testUser.getId();

        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);

        Stock testStockTwo = new Stock();
        testStockTwo.setSymbol("AAPL");
        testStockTwo.setName("Apple");
        stockDao.save(testStock);

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testBuyOrder = new Order();
        testBuyOrder.setBuyOrSell(BuyOrSell.BUY);
        testBuyOrder.setMpidInfo(testMpidInfo);
        testBuyOrder.setStock(testStock);
        testBuyOrder.setPrice(new BigDecimal(27.45).setScale(2, RoundingMode.HALF_UP));
        testBuyOrder.setSize(500);
        testBuyOrder.setTime(LocalDateTime.now().withNano(0));
        testBuyOrder.setComplete(false);
        ordersDao.save(testBuyOrder);
        int savedBuyOrderId = testBuyOrder.getId();

        Order testSellOrder = new Order();
        testSellOrder.setBuyOrSell(BuyOrSell.SELL);
        testSellOrder.setMpidInfo(testMpidInfo);
        testSellOrder.setStock(testStock);
        testSellOrder.setPrice(new BigDecimal(35.12).setScale(2, RoundingMode.HALF_UP));
        testSellOrder.setSize(300);
        testSellOrder.setTime(LocalDateTime.now().withNano(0));
        testSellOrder.setComplete(false);
        ordersDao.save(testSellOrder);
        int savedSellOrderId = testSellOrder.getId();

        try {
            String result = tradeService.createTrade(savedBuyOrderId, savedSellOrderId, savedUserId);
        } catch (ExceptionHandler e) {
            assertEquals(e.getMessage(),"Incompatible stock - Please select a matching stock with a buy price higher than the sell price" );
        }
    }
}