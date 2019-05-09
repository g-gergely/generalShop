import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
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

    //these will be initialized before the tests run
    private ProductCategory testCategory;
    private Supplier testSupplier;


    //create a stream that holds an instance of every class that implements ProductCategoryDao
    private static Stream<ProductCategoryDao> getCategoryClasses() {
        return Stream.of(
                ProductCategoryDaoMem.getInstance(),
                ProductCategoryDaoDb.getInstance());
    }

    //create a stream that holds an instance of every class that implements SupplierDao
    private static Stream<SupplierDao> getSupplierClasses() {
        return Stream.of(
                SupplierDaoMem.getInstance(),
                SupplierDaoDb.getInstance());
    }

    //initialize a ProductCategory and a Supplier object with these attributes:
    @BeforeAll
    public void setUp(){
        testCategory = new ProductCategory(400, "RomanCategory", "Lieutenant", "Let's go");
        testSupplier = new Supplier(500, "RomanSupplier", "Colonel");
    }

    // The following tests will run multiple times each.
    // They will be called using every instance that we have created in the getCategoryClasses and in the getSupplierClasses methods.
    // If we have 2 instances in getCategoryClasses(), the first test will run 2 times, if we had 3, it would run 3 etc.

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

    //remove is left for last as it will remove the data from the database as well
    @Order(7)
    @ParameterizedTest
    @MethodSource("getCategoryClasses")
    public void testCategoryRemove(ProductCategoryDao category){
        category.remove(400);
        assertNull(category.find(400));
        assertEquals(0, category.getAll().size());
    }


    @Order(8)
    @ParameterizedTest
    @MethodSource("getSupplierClasses")
    public void testSupplierRemove(SupplierDao supplier){
        supplier.remove(500);
        assertNull(supplier.find(500));
        assertEquals(0, supplier.getAll().size());
    }
}