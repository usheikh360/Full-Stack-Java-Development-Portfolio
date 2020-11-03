package com.mfour.OrderBook.service;

import com.mfour.OrderBook.daos.MpidInfoDao;
import com.mfour.OrderBook.daos.OrdersDao;
import com.mfour.OrderBook.daos.StockDao;
import com.mfour.OrderBook.entities.BuyOrSell;
import com.mfour.OrderBook.entities.MpidInfo;
import com.mfour.OrderBook.entities.Order;
import com.mfour.OrderBook.entities.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    StockDao stockDao;

    @Autowired
    OrdersDao ordersDao;

    @Autowired
    OrderService orderService;

    @Autowired
    MpidInfoDao mpidInfoDao;

    @BeforeEach
    void setUp() {
        mpidInfoDao.deleteAll();
        stockDao.deleteAll();
        ordersDao.deleteAll();
    }

    @Test
    void getSpread() {

        //ARRANGE
        Stock testStock = new Stock();
        testStock.setSymbol("GOOG");
        testStock.setName("Google");
        stockDao.save(testStock);
        int savedId = testStock.getId();

        MpidInfo testMpidInfo = new MpidInfo();
        testMpidInfo.setMpid("GDSA");
        testMpidInfo.setName("GOLDMAN SACHS & CO. LLC");
        mpidInfoDao.save(testMpidInfo);

        Order testOrder = new Order();
        testOrder.setBuyOrSell(BuyOrSell.BUY);
        testOrder.setMpidInfo(testMpidInfo);
        testOrder.setStock(testStock);
        testOrder.setPrice(new BigDecimal(27.45).setScale(2, RoundingMode.HALF_UP));
        testOrder.setSize(500);
        testOrder.setTime(LocalDateTime.now().withNano(0));
        testOrder.setComplete(false);
        ordersDao.save(testOrder);

        Order testOrderTwo = new Order();
        testOrderTwo.setBuyOrSell(BuyOrSell.SELL);
        testOrderTwo.setMpidInfo(testMpidInfo);
        testOrderTwo.setStock(testStock);
        testOrderTwo.setPrice(new BigDecimal(24.15).setScale(2, RoundingMode.HALF_UP));
        testOrderTwo.setSize(300);
        testOrderTwo.setTime(LocalDateTime.now().withNano(0));
        testOrderTwo.setComplete(false);
        ordersDao.save(testOrderTwo);

        //ACT AND ASSERT
        BigDecimal spreadReturned = orderService.getSpread(savedId);
        assertEquals(3.3, spreadReturned.doubleValue());
    }
}