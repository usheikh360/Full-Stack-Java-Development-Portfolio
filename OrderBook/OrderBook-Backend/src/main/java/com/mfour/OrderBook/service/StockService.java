package com.mfour.OrderBook.service;

import com.mfour.OrderBook.daos.StockDao;
import com.mfour.OrderBook.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    StockDao stockDao;

    public List<String> getIncompleteStocks() {
        return stockDao.getIncompleteSymbols();
    }

    public Stock getStockBySymbol(String symbol) {
        return stockDao.getStockBySymbol(symbol);
    }

    public List<Stock> getAllStocks(){
        return stockDao.getAllStocks();
    }

}
