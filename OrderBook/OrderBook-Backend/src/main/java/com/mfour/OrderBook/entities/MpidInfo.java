package com.mfour.OrderBook.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "mpidinfo")
public class MpidInfo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "mpid", length = 4,nullable = false)
    private String mpid;

    @Column(name = "name", length = 50,nullable = false)
    private String name;

    public String getMpid() {
        return mpid;
    }

    public void setMpid(String mpid) {
        this.mpid = mpid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MpidInfo mpidInfo = (MpidInfo) o;
        return id == mpidInfo.id &&
                Objects.equals(mpid,mpidInfo.mpid) &&
                Objects.equals(name,mpidInfo.name) ;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id,mpid,name);
    }

}
