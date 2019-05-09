package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoDb;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDaoTest {

    private Product test;
    private Supplier testSupplier;
    private Supplier falseSupplier;
    private ProductCategory testCategory;
    private ProductCategory falseCategory;

    private static Stream<ProductDao> getProductClasses() {
        return Stream.of(
                ProductDaoMem.getInstance(),
                ProductDaoDb.getInstance());
    }

    @BeforeAll
    private void init() {
        testCategory = new ProductCategory(400, "RomanCategory", "Lieutenant", "Let's go");
        falseCategory = new ProductCategory(401, "FalseCategory", "Lieutenant", "Let's go");
        testSupplier = new Supplier(500, "RomanSupplier", "Colonel");
        falseSupplier = new Supplier(501, "FalseSupplier", "FalseColonel");
        test = new Product(100, "Oda Nobunaga", 1700, "TAL", "The first of the 3 unifiers of Japan, lord of Owari.", testCategory, testSupplier);
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductAdd(ProductDao product){
        product.add(test);
        assertNotNull(product.find(100));
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductFindByID(ProductDao product){
        assertEquals("Oda Nobunaga", product.find(100).getName());
    }

    @Order(3)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductGetAllProduct(ProductDao product){
        assertEquals(1, product.getAll().size());
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductFindBySupplier(ProductDao product){
        assertEquals(1, product.getBy(testSupplier).size());
    }

    @Order(5)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductFindByFalseSupplier(ProductDao product){
        assertEquals(0, product.getBy(falseSupplier).size());
    }

    @Order(6)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductFindByProductCategory(ProductDao product){
        assertEquals(1, product.getBy(testCategory).size());
    }

    @Order(7)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductFindByFalseProductCategory(ProductDao product){
        assertEquals(0, product.getBy(falseCategory).size());
    }

    @Order(8)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductGetProductsByString(ProductDao product){
        assertEquals(1, product.getProducts("RomanCategory","RomanSupplier").size());    //if i take out the second add it is okay
    }
    @Order(9)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductGetProductsByFalseString(ProductDao product){
        assertEquals(0, product.getProducts("RomanvfvfvfCategory","RomanvfvfvfSupplier").size());
    }

    @Order(10)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductGetProductByObject(ProductDao product){
        assertEquals(1, product.getProducts(testSupplier,testCategory).size());
    }

    @Order(11)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductGetProductByFalseCategory(ProductDao product){
        assertEquals(1, product.getProducts(testSupplier,falseCategory).size());
    }

    @Order(12)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductGetProductByFalseSupplier(ProductDao product){
        assertEquals(1, product.getProducts(falseSupplier,testCategory).size());
    }


    @Order(13)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductGetProductByFalseObject(ProductDao product){
        assertEquals(0, product.getProducts(falseSupplier,falseCategory).size());
    }

    @Order(14)
    @ParameterizedTest
    @MethodSource("getProductClasses")
    public void testProductRemove(ProductDao product){
        product.remove(400);
        assertNull(product.find(400));
    }
}
