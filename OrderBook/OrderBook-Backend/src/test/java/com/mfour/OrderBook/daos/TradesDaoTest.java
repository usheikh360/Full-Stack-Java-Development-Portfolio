package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TradesDaoTest {

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
    public void testAddGetTrade(){
        MpidInfo mpid= new MpidInfo();
        mpid.setMpid("GOOG");
        mpid.setName("Google");
        mpidInfoDao.save(mpid);
        Stock stock = new Stock();
        stock.setName("GOOG");
        stock.setSymbol("GOOG");
        stockDao.save(stock);

        Order order1 = new Order();
        Order order2 = new Order();
        order1.setBuyOrSell(BuyOrSell.BUY);
        order2.setBuyOrSell(BuyOrSell.SELL);
        order1.setMpidInfo(mpid);
        order2.setMpidInfo(mpid);
        order1.setPrice(new BigDecimal(90.0));
        order2.setPrice(new BigDecimal(90.0));
        order1.setStock(stock);
        order2.setStock(stock);
        order1.setSize(100);
        order2.setSize(100);
        order1.setTime(LocalDateTime.now().withNano(0));
        order2.setTime(LocalDateTime.now().withNano(0));
        order1.setComplete(true);
        order2.setComplete(true);
        ordersDao.save(order1);
        ordersDao.save(order2);

        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");
        userInfoDao.save(testUser);

        Trades trades = new Trades();
        trades.setUserid(testUser);
        trades.setBuyOrder(order1);
        trades.setSellOrder(order2);
        trades.setTime(LocalDateTime.now().withNano(0));
        tradesDao.save(trades);

        Trades testTrade = tradesDao.findById(trades.getId()).get();

        assertEquals(trades.getSellOrder(),testTrade.getSellOrder());
        assertEquals(trades,testTrade);
    }

    @Test
    public void testGetAllTrades(){

        MpidInfo mpid= new MpidInfo();
        mpid.setMpid("GOOG");
        mpid.setName("Google");
        mpidInfoDao.save(mpid);
        Stock stock = new Stock();
        stock.setName("GOOG");
        stock.setSymbol("GOOG");
        stockDao.save(stock);

        Order order1 = new Order();
        Order order2 = new Order();
        order1.setBuyOrSell(BuyOrSell.BUY);
        order2.setBuyOrSell(BuyOrSell.SELL);
        order1.setMpidInfo(mpid);
        order2.setMpidInfo(mpid);
        order1.setPrice(new BigDecimal(90.0));
        order2.setPrice(new BigDecimal(90.0));
        order1.setStock(stock);
        order2.setStock(stock);
        order1.setSize(100);
        order2.setSize(100);
        order1.setTime(LocalDateTime.now().withNano(0));
        order2.setTime(LocalDateTime.now().withNano(0));
        order1.setComplete(true);
        order2.setComplete(true);
        ordersDao.save(order1);
        ordersDao.save(order2);

        MpidInfo mpid2= new MpidInfo();
        mpid2.setMpid("GOOD");
        mpid2.setName("GooDdle");
        mpidInfoDao.save(mpid2);
        Stock stock2 = new Stock();
        stock2.setName("GOOD");
        stock2.setSymbol("GOOD");
        stockDao.save(stock2);

        Order order3 = new Order();
        Order order4 = new Order();
        order3.setBuyOrSell(BuyOrSell.BUY);
        order4.setBuyOrSell(BuyOrSell.SELL);
        order3.setMpidInfo(mpid2);
        order4.setMpidInfo(mpid2);
        order3.setPrice(new BigDecimal(90.0));
        order4.setPrice(new BigDecimal(90.0));
        order3.setStock(stock2);
        order4.setStock(stock2);
        order3.setSize(100);
        order4.setSize(100);
        order3.setTime(LocalDateTime.now().withNano(0));
        order4.setTime(LocalDateTime.now().withNano(0));
        order3.setComplete(true);
        order4.setComplete(true);
        ordersDao.save(order3);
        ordersDao.save(order4);

        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");
        userInfoDao.save(testUser);
        UserInfo testUser2 = new UserInfo();
        testUser2.setUsername("User");
        testUser2.setPassword("123456");
        userInfoDao.save(testUser2);

        Trades trades = new Trades();
        trades.setUserid(testUser);
        trades.setBuyOrder(order1);
        trades.setSellOrder(order2);
        trades.setTime(LocalDateTime.now().withNano(0));
        tradesDao.save(trades);
        Trades tradesTest = new Trades();
        tradesTest.setUserid(testUser2);
        tradesTest.setBuyOrder(order3);
        tradesTest.setSellOrder(order4);
        tradesTest.setTime(LocalDateTime.now().withNano(0));
        tradesDao.save(tradesTest);

        List<Trades> tradesList = tradesDao.findAll();

        assertEquals(tradesDao.findAll().size(),2);
        assertEquals(trades,tradesList.get(0));
        assertEquals(tradesTest,tradesList.get(1));
    }

    @Test
    public void testUpdateTrade(){

        MpidInfo mpid= new MpidInfo();
        mpid.setMpid("GOOG");
        mpid.setName("Google");
        mpidInfoDao.save(mpid);
        Stock stock = new Stock();
        stock.setName("GOOG");
        stock.setSymbol("GOOG");
        stockDao.save(stock);

        Order order1 = new Order();
        Order order2 = new Order();
        order1.setBuyOrSell(BuyOrSell.BUY);
        order2.setBuyOrSell(BuyOrSell.SELL);
        order1.setMpidInfo(mpid);
        order2.setMpidInfo(mpid);
        order1.setPrice(new BigDecimal(90.0));
        order2.setPrice(new BigDecimal(90.0));
        order1.setStock(stock);
        order2.setStock(stock);
        order1.setSize(100);
        order2.setSize(100);
        order1.setTime(LocalDateTime.now().withNano(0));
        order2.setTime(LocalDateTime.now().withNano(0));
        order1.setComplete(true);
        order2.setComplete(true);
        ordersDao.save(order1);
        ordersDao.save(order2);


        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");
        userInfoDao.save(testUser);

        Trades trades = new Trades();
        trades.setUserid(testUser);
        trades.setBuyOrder(order1);
        trades.setSellOrder(order2);
        trades.setTime(LocalDateTime.now().withNano(0));
        tradesDao.save(trades);

        Trades tempTrades = tradesDao.findById(trades.getId()).get();
        assertEquals(trades,tempTrades);

        MpidInfo mpid2= new MpidInfo();
        mpid2.setMpid("GOOD");
        mpid2.setName("GooDdle");
        mpidInfoDao.save(mpid2);
        Stock stock2 = new Stock();
        stock2.setName("GOOD");
        stock2.setSymbol("GOOD");
        stockDao.save(stock2);

        Order order3 = new Order();
        Order order4 = new Order();
        order3.setBuyOrSell(BuyOrSell.BUY);
        order4.setBuyOrSell(BuyOrSell.SELL);
        order3.setMpidInfo(mpid2);
        order4.setMpidInfo(mpid2);
        order3.setPrice(new BigDecimal(90.0));
        order4.setPrice(new BigDecimal(90.0));
        order3.setStock(stock2);
        order4.setStock(stock2);
        order3.setSize(100);
        order4.setSize(100);
        order3.setTime(LocalDateTime.now().withNano(0));
        order4.setTime(LocalDateTime.now().withNano(0));
        order3.setComplete(true);
        order4.setComplete(true);
        ordersDao.save(order3);
        ordersDao.save(order4);

        trades.setTime(LocalDateTime.now().withNano(0));
        trades.setSellOrder(order4);
        trades.setBuyOrder(order3);
        tradesDao.save(trades);

        tempTrades = tradesDao.findById(trades.getId()).get();

        assertEquals(tempTrades,trades);

    }

    @Test
    public void testDeleteTrade(){

        MpidInfo mpid= new MpidInfo();
        mpid.setMpid("GOOG");
        mpid.setName("Google");
        mpidInfoDao.save(mpid);
        Stock stock = new Stock();
        stock.setName("GOOG");
        stock.setSymbol("GOOG");
        stockDao.save(stock);

        Order order1 = new Order();
        Order order2 = new Order();
        order1.setBuyOrSell(BuyOrSell.BUY);
        order2.setBuyOrSell(BuyOrSell.SELL);
        order1.setMpidInfo(mpid);
        order2.setMpidInfo(mpid);
        order1.setPrice(new BigDecimal(90.0));
        order2.setPrice(new BigDecimal(90.0));
        order1.setStock(stock);
        order2.setStock(stock);
        order1.setSize(100);
        order2.setSize(100);
        order1.setTime(LocalDateTime.now().withNano(0));
        order2.setTime(LocalDateTime.now().withNano(0));
        order1.setComplete(true);
        order2.setComplete(true);
        ordersDao.save(order1);
        ordersDao.save(order2);


        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");
        userInfoDao.save(testUser);

        Trades trades = new Trades();
        trades.setUserid(testUser);
        trades.setBuyOrder(order1);
        trades.setSellOrder(order2);
        trades.setTime(LocalDateTime.now().withNano(0));
        tradesDao.save(trades);

        Trades tempTrades = tradesDao.findById(trades.getId()).get();

        assertEquals(trades,tempTrades);

        tradesDao.delete(trades);

        assertEquals(tradesDao.findById(trades.getId()), Optional.empty());
    }
}
