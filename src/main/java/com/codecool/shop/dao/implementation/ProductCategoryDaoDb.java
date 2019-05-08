package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDb implements ProductCategoryDao {

    private static ProductCategoryDaoDb instance = null;

    private static final String DATABASE = System.getenv("DATABASE");
    private static final String DBUSER = System.getenv("USER");
    private static final String DBPASSWORD = System.getenv("PASSWORD");

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoDb() {
    }

    public static ProductCategoryDaoDb getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoDb();
        }
        return instance;
    }

    private List<ProductCategory> executeQuery(String query) {
        List<ProductCategory> categories = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ProductCategory category = new ProductCategory(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("description"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public ProductCategory find(String name) {
        String sql = "SELECT * FROM product_category WHERE name=?;";
        ProductCategory category = null;
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                category = new ProductCategory(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }


    @Override
    public List<ProductCategory> getAll() {
        String sql = "SELECT * FROM product_category;";
        return executeQuery(sql);
    }

    @Override
    public ProductCategory find(int id) {
        String sql = "SELECT * FROM product_category WHERE id=?;";
        ProductCategory category = null;
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                category = new ProductCategory(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public void add(ProductCategory category) {
        String sql = "INSERT INTO product_category (id, name, department, description) VALUES (?, ?, ?, ?);";
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, category.getId());
            statement.setString(2, category.getName());
            statement.setString(3, category.getDepartment());
            statement.setString(4, category.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM product_category WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
