package com.mycompany.storegui;

public class Statistical {
    private int totalOrder;
    private int totalProduct;
    private float totalCost;
    private float totalProfit;

    public Statistical() {
    }

    public Statistical(int totalOrder, int totalProduct, float totalCost, float totalProfit) {
        this.totalOrder = totalOrder;
        this.totalProduct = totalProduct;
        this.totalCost = totalCost;
        this.totalProfit = totalProfit;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit) {
        this.totalProfit = totalProfit;
    }
}
