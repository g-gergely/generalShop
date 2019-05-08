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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Order(1)
    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testCategoryAdd(ProductCategoryDao category){
        category.add(testCategory);
        assertNotNull(category.find(400));
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testSupplierAdd(SupplierDao supplier){
        supplier.add(testSupplier);
        assertNotNull(supplier.find(500));
    }

    @Order(3)
    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testCategoryFind(ProductCategoryDao category){
        assertEquals("RomanCategory", category.find(400).getName());
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testSupplierFind(SupplierDao supplier){
        assertEquals("RomanSupplier", supplier.find(500).getName());
    }

    @Order(5)
    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testGetAllCategories(ProductCategoryDao category){
        assertEquals(1, category.getAll().size());
    }

    @Order(6)
    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testGetAllSuppliers(SupplierDao supplier){
        assertEquals(1, supplier.getAll().size());
    }

    @Order(7)
    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testCategoryRemove(ProductCategoryDao category){
        category.remove(400);
        assertNull(category.find(400));
    }

    @Order(8)
    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testSupplierRemove(SupplierDao supplier){
        supplier.remove(500);
        assertNull(supplier.find(500));
    }
}