package com.codecool.shop.model.order;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Integer, Integer> cart = new HashMap<>();
    private float totalPrice;

    public Map<Product, Integer> getCart(ProductDao productDataStore) {
        Map<Product, Integer> goodCart = new HashMap<>();

        for (Integer prodId : cart.keySet()) {
            goodCart.put(productDataStore.find(prodId), cart.get(prodId));
        }
        return goodCart;
    }

    public void addProduct(int product) {
        cart.merge(product, 1, Integer::sum);
    }

    public void removeProduct(int product) {
        if (cart.get(product) > 1) {
            cart.merge(product, -1, Integer::sum);
        } else {
            cart.remove(product);
        }
    }

    public float getTotalPrice(ProductDao productDataStore) {
        totalPrice = 0;
        for (Integer productId: cart.keySet()) {
            totalPrice += productDataStore.find(productId).getDefaultPrice() * cart.get(productId);
        }
        return totalPrice;
    }
}
