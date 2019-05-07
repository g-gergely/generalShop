package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductDaoMem implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoMem instance = null;

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/store";
    private static final String DBUSER = System.getenv("USER");
    private static final String DBPASSWORD = System.getenv("PASSWORD");

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

    private List<Product> executeQuery(String query) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getString("name"),
                        rs.getFloat("default_price"),
                        rs.getString("currency"),
                        rs.getString("description"),
                        ProductCategoryDaoMem.getInstance().find(rs.getInt("product_category")),
                        SupplierDaoMem.getInstance().find(rs.getInt("supplier")));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO product (name, default_price, currency, description, supplier, product_category) " +
                    "VALUES (?,?,?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getDefaultPrice());
            statement.setString(3, product.getDefaultCurrency());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getSupplier().getId());
            statement.setInt(6, product.getProductCategory().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        String sql = "SELECT * FROM product WHERE id=?;";
        Product product = null;
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                product = new Product(
                        rs.getString("name"),
                        rs.getFloat("default_price"),
                        rs.getString("currency"),
                        rs.getString("description"),
                        ProductCategoryDaoMem.getInstance().find(rs.getInt("product_category")),
                        SupplierDaoMem.getInstance().find(rs.getInt("supplier")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM product WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT * FROM product;";
        return executeQuery(sql);
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        int id = supplier.getId();
        String sql = "SELECT * FROM product WHERE supplier = " + id +";";
        return executeQuery(sql);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        int id = productCategory.getId();
        String sql = "SELECT * FROM product WHERE product_category = " + id +";";
        return executeQuery(sql);
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

    @Override
    public List<Product> getProducts(Supplier supplierObj, ProductCategory categoryObj) {
        int supplierId = supplierObj.getId();
        int categoryId = categoryObj.getId();
        String sql = "SELECT * FROM product WHERE supplier = " + supplierId +
                " AND product_category = " + categoryId +";";
        return executeQuery(sql);
    }
}
