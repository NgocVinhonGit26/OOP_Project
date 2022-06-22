/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.util.Scanner;


/**
 *
 * @author Bassam Muhammad
 */
public class Customer {
    private String name;
    private String address;
    private String password;
    private long phoneNumber;
    
    
    public Customer(String name, String address, String password, long phoneNumber) {
        this.name = name;
        this.address = address;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
