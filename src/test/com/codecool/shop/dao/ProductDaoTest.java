package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoDb;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDaoTest {

    private Product test;

    private static Stream<ProductDao> getClasses() {
        return Stream.of(
                ProductDaoMem.getInstance(),
                ProductDaoDb.getInstance());
    }

    @BeforeAll
    private void init() {
        ProductCategory japanese = new ProductCategory(5, "Japanese", "General", "Tenka Fubu.");
        Supplier owari = new Supplier(9, "Owari", "The start of something big");
        test = new Product(1000, "Oda Nobunaga", 1700, "TAL", "The first of the 3 unifiers of Japan, lord of Owari.", japanese, owari);
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testAdd(ProductDao product){
        product.add(test);
        assertNotNull(product.find(1000));
    }
}
