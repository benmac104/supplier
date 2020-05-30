package com.project.hit.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int orderNumberCount = 1000;
    private Supplier supplier;
    private String details;
    private double price;
    private Date date;
    private int orderNumber;

    public Order(Supplier supplier, double price, String details) {
        this.supplier = supplier;
        this.price = price;
        this.details = details;
        this.date = Calendar.getInstance().getTime();
        this.orderNumber = orderNumberCount++;
    }


    public int getOrderNumber() {
        return orderNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.price, price) == 0 &&
                Objects.equals(supplier, order.supplier) &&
                Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, price, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", orderNumber=" + orderNumber +
                '}';
    }
}
