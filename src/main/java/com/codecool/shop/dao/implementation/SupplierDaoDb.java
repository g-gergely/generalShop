package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoDb implements SupplierDao {

    private static SupplierDaoDb instance = null;

    private static final String DATABASE = System.getenv("DATABASE");
    private static final String DBUSER = System.getenv("USER");
    private static final String DBPASSWORD = System.getenv("PASSWORD");

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoDb() {
    }

    public static SupplierDaoDb getInstance() {
        if (instance == null) {
            instance = new SupplierDaoDb();
        }
        return instance;
    }

    private List<Supplier> executeQuery(String query) {
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public Supplier find(String name) {
        String sql = "SELECT * FROM supplier WHERE name=?;";
        Supplier supplier = null;
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                supplier = new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    @Override
    public void add(Supplier supplier) {
        String sql = "INSERT INTO supplier (id, name, description) VALUES (?,?,?);";
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, supplier.getId());
            statement.setString(2, supplier.getName());
            statement.setString(3, supplier.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        String sql = "SELECT * FROM supplier WHERE id=?;";
        Supplier supplier = null;
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                supplier = new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM supplier WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(DATABASE, DBUSER, DBPASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        String sql = "SELECT * FROM supplier;";
        return executeQuery(sql);
    }
}
