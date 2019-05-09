package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductDaoMem implements ProductDao {

    private static ProductDaoMem instance = null;
    private List<Product> data = new ArrayList<>();

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(product.getId());
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getProducts(String category, String supplier) {
        Stream<Product> productStream = instance.getAll().stream();

        if (!category.equals("")) {
            productStream = productStream.filter(product -> product.getProductCategory().getName().equals(category));
        }

        if (!supplier.equals("")) {
            productStream = productStream.filter(product -> product.getSupplier().getName().equals(supplier));
        }

        return productStream.collect(Collectors.toList());
    }
}
