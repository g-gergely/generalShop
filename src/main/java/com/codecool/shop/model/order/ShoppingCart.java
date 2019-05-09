package com.codecool.shop.model.order;

import com.codecool.shop.dao.implementation.ProductDaoDb;

import java.util.LinkedHashMap;
import java.util.Map;


public class ShoppingCart {
    private Map<Integer, Integer> cart = new LinkedHashMap<Integer, Integer>() {};

    public Map<Integer, Integer> getCart() {
        return cart;
    }

    public void addProduct(int productId) {
        cart.merge(productId, 1, Integer::sum);
    }

    public void removeProduct(int productId) {
        if (cart.get(productId) > 1) {
            cart.merge(productId, -1, Integer::sum);
        } else {
            cart.remove(productId);
        }
    }

    public float getTotalPrice() {
        float totalPrice = 0;
        for (Integer productId: cart.keySet()) {
            totalPrice += ProductDaoDb.getInstance().find(productId).getDefaultPrice() * cart.get(productId);
        }
        return totalPrice;
    }
}
