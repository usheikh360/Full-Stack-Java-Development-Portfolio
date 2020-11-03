package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.Order;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Configurable
public interface OrdersDao extends JpaRepository<Order,Integer> {

    @Query(value = "SELECT * FROM orders o WHERE o.buyorsell = 'BUY' AND o.complete = false",nativeQuery = true)
    List<Order> getBuyAll();

    @Query(value = "SELECT * FROM orders o WHERE o.buyorsell = 'SELL' AND o.complete = false",nativeQuery = true)
    List <Order> getSellAll();

    @Query(value = "SELECT MIN(o.price) FROM orders o WHERE stockid IN (SElECT s.id FROM stock s WHERE s.symbol =:symbol)",nativeQuery = true)
    BigDecimal getMinPrice(@Param("symbol") String symbol);

    @Query(value = "SELECT MAX(o.price) FROM orders o WHERE stockid IN (SElECT s.id FROM stock s WHERE s.symbol =:symbol)",nativeQuery = true)
    BigDecimal getMaxPrice(@Param("symbol")String symbol);

}
