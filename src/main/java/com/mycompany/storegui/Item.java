/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

public class Item {
    private String name;
    private long id;
    private Float cost;
    private int quantity;
    private String imageAddress;

    public Item(String name, float cost, long id, int quantity) {
        this.name = name;
        this.cost = cost;
        this.id = id;
        this.quantity = quantity;
    }

    public Item(String name, String imageAddress, float cost) {
        this.name = name;
        this.cost = cost;
        this.imageAddress = imageAddress;
    }

    public Item(String name, String imageAddress) {
        this.name = name;
        this.imageAddress = imageAddress;
    }

    public Item() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

}
