package com.codecool.shop.model.order;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private String username;
    private String emailAddress;
    private String phoneNumber;
    private Map<String, String> billingAddress = new HashMap<>();
    private Map<String, String> shippingAddress = new HashMap<>();
    private ShoppingCart shoppingCart = new ShoppingCart();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, String> getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Map<String, String> billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Map<String, String> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Map<String, String> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
