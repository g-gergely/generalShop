package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoDb;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductCategoryDaoTest {

    private ProductCategory test;

    private static Stream<ProductCategoryDao> getClasses() {
        return Stream.of(
                ProductCategoryDaoMem.getInstance(),
                ProductCategoryDaoDb.getInstance());
    }

    @BeforeAll
    private void init(){
        test = new ProductCategory(400, "Roman3", "Lieutenant", "Let's go");
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testAdd(ProductCategoryDao category){
        category.add(test);
        category.add(new ProductCategory(1000, "Test", "Test", "Test"));
        assertNotNull(category.find(400));
        assertNotNull(category.find(1000));
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testFind(ProductCategoryDao category){
        assertEquals("Roman3", category.find(400).getName());
        assertEquals("Test", category.find(1000).getName());
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testGetAll(ProductCategoryDao category){
        assertEquals(2, category.getAll().size());
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testRemove(ProductCategoryDao category){
        category.remove(400);
        category.remove(1000);
        assertNull(category.find(400));
        assertNull(category.find(1000));
    }
}