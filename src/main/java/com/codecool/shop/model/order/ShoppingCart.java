package com.codecool.shop.model.order;

import com.codecool.shop.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, Integer> cart = new HashMap<>();
    private float totalPrice;

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void addProduct(Product product) {
        cart.merge(product, 1, Integer::sum);
    }

    public void removeProduct(Product product) {
        if (cart.get(product) > 1) {
            cart.merge(product, -1, Integer::sum);
        } else {
            cart.remove(product);
        }
    }

    public float getTotalPrice() {
        totalPrice = 0;
        for (Product product: cart.keySet()) {
            totalPrice += product.getDefaultPrice() * cart.get(product);
        }
        return totalPrice;
    }
}
