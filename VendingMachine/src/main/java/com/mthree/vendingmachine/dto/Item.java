/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author umairsheikh
 */
public class Item {//class which represents the various items that will are stored in the vending machine
    private int id;
    private String itemName;
    private BigDecimal itemCost;
    private int numberOfItemsInInventory;

    public Item(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getNumberOfItemsInInventory() {
        return numberOfItemsInInventory;
    }

    public void setNumberOfItemsInInventory(int numberOfItemsInInventory) {
        this.numberOfItemsInInventory = numberOfItemsInInventory;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.itemName);
        hash = 31 * hash + Objects.hashCode(this.itemCost);
        hash = 31 * hash + this.numberOfItemsInInventory;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.numberOfItemsInInventory != other.numberOfItemsInInventory) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.itemCost, other.itemCost)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", itemName=" + itemName + ", itemCost=" + itemCost + ", numberOfItemsInInventory=" + numberOfItemsInInventory + '}';
    }
}
