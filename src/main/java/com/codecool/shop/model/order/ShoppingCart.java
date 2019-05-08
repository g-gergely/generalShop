package com.codecool.shop.model.order;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;

import java.util.SortedMap;
import java.util.TreeMap;

public class ShoppingCart {
    private SortedMap<Integer, Integer> cart = new TreeMap<Integer, Integer>() {};

    public SortedMap<Product, Integer> getCart(ProductDao productDataStore) {
        SortedMap<Product, Integer> goodCart = new TreeMap<>();

        for (Integer prodId : cart.keySet()) {
            goodCart.put(productDataStore.find(prodId), cart.get(prodId));
        }
        return goodCart;
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

    public float getTotalPrice(ProductDao productDataStore) {
        float totalPrice = 0;
        for (Integer productId: cart.keySet()) {
            totalPrice += productDataStore.find(productId).getDefaultPrice() * cart.get(productId);
        }
        return totalPrice;
    }
}
