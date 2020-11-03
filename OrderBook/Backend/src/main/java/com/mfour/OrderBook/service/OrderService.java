package com.mfour.OrderBook.service;

import com.mfour.OrderBook.daos.MpidInfoDao;
import com.mfour.OrderBook.daos.OrdersDao;
import com.mfour.OrderBook.daos.StockDao;
import com.mfour.OrderBook.entities.BuyOrSell;
import com.mfour.OrderBook.entities.MpidInfo;
import com.mfour.OrderBook.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    @Autowired
    OrdersDao ordersDao;

    @Autowired
    MpidInfoDao mpidInfoDao;

    @Autowired
    StockDao stockDao;

    public String createOrder(Order order, String buyorsell) throws ExceptionHandler{
        String type = "Created";

        if(order.getId() != 0) { //check to see if order is being updated or created
            type = "Updated";
            if(buyorsell!= "BUY" || buyorsell != "SELL"){
                buyorsell = order.getBuyOrSell().toString();
            }
        }

        try{
            if(order.getSize() <= 0){
                return "Please ensure the number of shares is not equal to 0";
            }
            order.setTime(LocalDateTime.now());
            if (buyorsell.toUpperCase().equals("BUY")) {
                System.out.println(buyorsell);
                order.setBuyOrSell(BuyOrSell.BUY);
            }else {
                System.out.println(order.getBuyOrSell());
                order.setBuyOrSell(BuyOrSell.SELL);
            }
            ordersDao.save(order);
        }
        catch(NoSuchElementException ex){
            System.out.println("catching error");
            return("Order creation failed");
        }
        catch(HttpMessageNotReadableException ex){
            return("Invalid Input, price and size must be integer");
        }
        catch(Exception ex){
            return "Field left empty. Please ensure all fields are filled";
        }

        return "Successfully "+type+" order";
    }

    public List<Order> getBuyAll(){
        return ordersDao.getBuyAll();
    }

    public List<Order> getSellAll(){
        return ordersDao.getSellAll();
    }


    public BigDecimal getSpread(int stockid){
        String symbol = stockDao.findById(stockid).get().getSymbol();
        BigDecimal price1 = ordersDao.getMaxPrice(symbol);
        BigDecimal price2 = ordersDao.getMinPrice(symbol);
        return price1.subtract(price2);
    }

    public String deleteOrder(Order order)throws ExceptionHandler{
        try{
            ordersDao.delete(order);
        }
        catch(NoSuchElementException err){
            throw new ExceptionHandler("Invalid Order entered");
        }
        return "Order Deleted";
    }

    public List<MpidInfo> allMpid(){
        return mpidInfoDao.findAll();
    }
}
