package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.SupplierDaoDb;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.junit.jupiter.api.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SupplierDaoTest {

    private Supplier test;

    private static Stream<SupplierDao> getClasses() {
        return Stream.of(
                SupplierDaoMem.getInstance(),
                SupplierDaoDb.getInstance());
    }

    @BeforeAll
    private void init(){
        test = new Supplier(400, "Roman3", "Let's Go!");
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testAdd(SupplierDao supplier){
        supplier.add(test);
        supplier.add(new Supplier(1000, "Test", "Test"));
        assertNotNull(supplier.find(400));
        assertNotNull(supplier.find(1000));
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testFind(SupplierDao supplier){
        assertEquals("Roman3", supplier.find(400).getName());
        assertEquals("Test", supplier.find(1000).getName());
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testGetAll(SupplierDao supplier){
        assertEquals(2, supplier.getAll().size());
    }

    @ParameterizedTest
    @MethodSource("getClasses")
    public void testRemove(SupplierDao supplier){
        supplier.remove(400);
        supplier.remove(1000);
        assertNull(supplier.find(400));
        assertNull(supplier.find(1000));
    }
}