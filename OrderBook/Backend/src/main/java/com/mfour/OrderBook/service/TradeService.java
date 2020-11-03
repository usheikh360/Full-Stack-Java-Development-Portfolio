package com.mfour.OrderBook.service;

import com.mfour.OrderBook.daos.OrdersDao;
import com.mfour.OrderBook.daos.TradesDao;
import com.mfour.OrderBook.daos.UserInfoDao;
import com.mfour.OrderBook.entities.Order;
import com.mfour.OrderBook.entities.Trades;
import com.mfour.OrderBook.entities.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TradeService {

    @Autowired
    TradesDao tradesDao;

    @Autowired
    OrdersDao orderDao;

    @Autowired
    UserInfoDao userInfoDao;

    public List<Trades> getAllTrades() {
        return tradesDao.findAll();
    }

    public String createTrade(int buyID, int sellID, int userID) throws ExceptionHandler {

        try {
            Order buyOrder = orderDao.findById(buyID).get();
            Order sellOrder = orderDao.findById(sellID).get();
            UserInfo user = userInfoDao.findById(userID).get();

            if(!buyOrder.getStock().equals(sellOrder.getStock())){
                throw new ExceptionHandler("Incompatible stock - Please enter orders with the same Stock Symbol");
            }

            if (sellOrder.getPrice().compareTo(buyOrder.getPrice()) == 1 ) {
                throw new ExceptionHandler("Incompatible stock - Please select a matching stock with a buy price higher than the sell price");
            }

            int size;
            if(buyOrder.getSize() > sellOrder.getSize()){
                size = sellOrder.getSize();
            }
            else{
                size = buyOrder.getSize();
            }
            buyOrder.setSize(buyOrder.getSize()-size);
            sellOrder.setSize(sellOrder.getSize()-size);
            if(buyOrder.getSize() == 0){
                buyOrder.setComplete(true);
            }
            if(sellOrder.getSize() == 0){
                sellOrder.setComplete(true);
            }

            orderDao.save(buyOrder);
            orderDao.save(sellOrder);

            Trades newTrade = new Trades();
            newTrade.setBuyOrder(buyOrder);
            newTrade.setSellOrder(sellOrder);
            newTrade.setTime(LocalDateTime.now());
            newTrade.setUserid(user);
            newTrade.setSize(size);

            tradesDao.save(newTrade);
            return "SUCCESS - new trade created";
        } catch (NoSuchElementException e) {
            throw new ExceptionHandler("ERROR: Order does not exist");
        }
    }
}
