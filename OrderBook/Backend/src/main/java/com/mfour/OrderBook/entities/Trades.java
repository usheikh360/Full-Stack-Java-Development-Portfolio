package com.mfour.OrderBook.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name="trades")
public class Trades {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false)
    private UserInfo userid;

    @OneToOne
    @JoinColumn(name="buyid", referencedColumnName = "id", nullable = false)
    private Order buyOrder;

    @OneToOne
    @JoinColumn(name="askid", referencedColumnName = "id", nullable = false)
    private Order sellOrder;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private LocalDateTime time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserInfo getUserid() {
        return userid;
    }

    public void setUserid(UserInfo userid) {
        this.userid = userid;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(Order buyid) {
        this.buyOrder = buyid;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public void setSellOrder(Order sellid) {
        this.sellOrder = sellid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trades trades = (Trades) o;
        return id == trades.id &&
                Objects.equals(userid,trades.userid) &&
                Objects.equals(buyOrder,trades.buyOrder) &&
                Objects.equals(sellOrder,trades.sellOrder); //&&
                //Objects.equals(time, trades.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,userid, buyOrder, sellOrder,time);
    }
}
