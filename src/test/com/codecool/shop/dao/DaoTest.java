package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoDb;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;

import com.codecool.shop.dao.implementation.SupplierDaoDb;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DaoTest {

    private ProductCategory testCategory;
    private Supplier testSupplier;

    private static Stream<ProductCategoryDao> getCategoryClasses() {
        return Stream.of(
                ProductCategoryDaoMem.getInstance(),
                ProductCategoryDaoDb.getInstance());
    }

    private static Stream<SupplierDao> getSupplierClasses() {
        return Stream.of(
                SupplierDaoMem.getInstance(),
                SupplierDaoDb.getInstance());
    }

    @BeforeAll
    private void init(){
        testCategory = new ProductCategory(400, "RomanCategory", "Lieutenant", "Let's go");
        testSupplier = new Supplier(500, "RomanSupplier", "Colonel");
    }

    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testAdd(ProductCategoryDao category){
        category.add(testCategory);
        assertNotNull(category.find(400));
    }

    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testAdd(SupplierDao supplier){
        supplier.add(testSupplier);
        assertNotNull(supplier.find(500));
    }

    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testFind(ProductCategoryDao category){
        assertEquals("RomanCategory", category.find(400).getName());
    }

    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testFind(SupplierDao supplier){
        assertEquals("RomanSupplier", supplier.find(500).getName());
    }

    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testGetAll(ProductCategoryDao category){
        assertEquals(1, category.getAll().size());
    }

    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testGetAll(SupplierDao supplier){
        assertEquals(1, supplier.getAll().size());
    }

    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testRemove(ProductCategoryDao category){
        category.remove(400);
        assertNull(category.find(400));
    }

    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testRemove(SupplierDao supplier){
        supplier.remove(500);
        assertNull(supplier.find(500));
    }
}