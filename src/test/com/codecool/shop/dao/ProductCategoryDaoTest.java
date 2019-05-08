package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoDb;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    private ProductCategory test;

    private static Stream<ProductCategoryDao> getClasses() {
        return Stream.of(
                ProductCategoryDaoMem.getInstance(),
                ProductCategoryDaoDb.getInstance());
    }

    @BeforeEach
    private void init(){
        test = new ProductCategory(400, "Roman2", "Lieutenant", "Let's go");
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testAdd(ProductCategoryDao category){
        category.add(test);
        assertEquals(test, category.find(400));
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testRemove(ProductCategoryDao category){
        category.remove(400);
        assertNull(category.find(400));
    }
}