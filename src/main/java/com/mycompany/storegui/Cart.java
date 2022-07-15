package com.mycompany.storegui;

public class Cart {
    private String nameProduct;
    private int sl;
    private float cost;
    private float totalCost;
    private float chietkhau;

    public Cart() {

    }

    public Cart(String nameProduct, int sl, float cost, float totalCost, float chietkhau) {
        this.nameProduct = nameProduct;
        this.sl = sl;
        this.cost = cost;
        this.totalCost = totalCost;
        this.chietkhau = chietkhau;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getChietkhau() {
        return chietkhau;
    }

    public void setChietkhau(float chietkhau) {
        this.chietkhau = chietkhau;
    }

}