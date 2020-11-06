package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.Stock;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Configurable
public interface StockDao extends JpaRepository<Stock,Integer> {

    @Query(value = "SELECT s.symbol FROM stock s " +
                   "JOIN orders o ON o.stockid = s.id WHERE o.complete = false",nativeQuery = true)
    List<String> getIncompleteSymbols();

    @Query(value = "SELECT * from stock s "+
                    "WHERE s.symbol = :symbol",nativeQuery = true)
    Stock getStockBySymbol(@Param("symbol")String symbol);

    @Query(value="SELECT s.* FROM stock s",nativeQuery = true)
    List<Stock> getAllStocks(); //need to redo testing as I have updated from s.symbol to s.*

}
