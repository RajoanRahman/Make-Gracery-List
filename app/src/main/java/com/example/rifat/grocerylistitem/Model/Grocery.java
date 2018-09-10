package com.example.rifat.grocerylistitem.Model;

/**
 * Created by Rifat on 8/31/2018.
 */

public class Grocery {
    public String name;
    public String quantity;
    public String dateItemadded;
    public int id;

    public Grocery() {
    }

    public Grocery(String name, String quantity, String dateItemadded, int id) {
        this.name = name;
        this.quantity = quantity;
        this.dateItemadded = dateItemadded;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateItemadded() {
        return dateItemadded;
    }

    public void setDateItemadded(String dateItemadded) {
        this.dateItemadded = dateItemadded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
