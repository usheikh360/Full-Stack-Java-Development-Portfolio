package com.mfour.OrderBook.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "stock")
public class Stock {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "symbol",length = 5)
    private String symbol;

    @Column(name = "name",length = 30)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return id == stock.id &&
                Objects.equals(symbol,stock.symbol) &&
                Objects.equals(name,stock.name);

    }
    @Override
    public int hashCode() {
        return Objects.hash(id,symbol,name);
    }

}
