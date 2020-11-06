package com.mfour.OrderBook.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity(name = "orders")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "mpidid",nullable = false)
    private MpidInfo mpidInfo;

    @Column(name = "buyorsell")
    @Enumerated(EnumType.STRING)
    private BuyOrSell buyOrSell;

    @ManyToOne
    @JoinColumn(name = "stockid",nullable = false)
    private Stock stock;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "size",nullable = false)
    private int size;

    @Column(name = "time",nullable = false)
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime time;

    @Column(name = "complete")
    private boolean complete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MpidInfo getMpidInfo() {
        return mpidInfo;
    }

    public void setMpidInfo(MpidInfo mpidInfo) {
        this.mpidInfo = mpidInfo;
    }

    public BuyOrSell getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(BuyOrSell buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(mpidInfo,order.mpidInfo) &&
                Objects.equals(buyOrSell,order.buyOrSell) &&
                Objects.equals(stock,order.stock) &&
                price.compareTo(order.price) == 0 &&
                Objects.equals(size, order.size) &&
                Objects.equals(time,order.time) &&
                Objects.equals(complete,order.complete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,mpidInfo,buyOrSell,stock,price,size,time,complete);
    }
}
