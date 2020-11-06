package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.Trades;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Configurable
public interface TradesDao extends JpaRepository<Trades,Integer> {
}
