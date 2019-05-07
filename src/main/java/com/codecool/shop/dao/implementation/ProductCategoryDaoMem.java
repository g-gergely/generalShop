package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoMem implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoMem instance = null;

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/store";
    private static final String DBUSER = System.getenv("USER");
    private static final String DBPASSWORD = System.getenv("PASSWORD");

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMem() {
    }

    public static ProductCategoryDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMem();
        }
        return instance;
    }

    private List<ProductCategory> executeQuery(String query){
        List<ProductCategory> categories = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)){
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                ProductCategory category = new ProductCategory
                        (rs.getString("name"), rs.getString("department"),
                        rs.getString("description"));
                categories.add(category);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return categories;
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
        try(Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                category = new ProductCategory
                        (rs.getString("name"), rs.getString("department"),
                                rs.getString("description"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public void add(ProductCategory category) {
        String sql = "INSERT INTO product_category VALUES name = ?, department = ?, description = ?;";
        try(Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDepartment());
            statement.setString(3, category.getDescription());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM product_category WHERE id = ?;";
        try(Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
