package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoDb;
import com.codecool.shop.dao.implementation.ProductDaoDb;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoDb;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private ProductDao productDataStore = ProductDaoDb.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDb.getInstance();
    private SupplierDao supplierDao = SupplierDaoDb.getInstance();
    private final ProductCategory defaultCategory = productCategoryDataStore.find(1);
    private Map<String, Object> params = new HashMap<>();

    private static Map<String, Integer> cartMap = new HashMap<>();
    public static Map<String, Integer> getCartMap() {
        return cartMap;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        List<Product> products = selectProducts(request);
        params.put("products", products);

        params.forEach(((key, value) -> context.setVariable(String.valueOf(key), value)));
        response.setCharacterEncoding("UTF-8");
        engine.process("product/index", context, response.getWriter());

        HttpSession session = request.getSession();
        String addId = request.getParameter("item_id");

        if (addId != null) {
            Product chosen = productDataStore.find(Integer.parseInt(addId));
            String identifier = chosen.getDefaultPrice() + "!" + addId + "?" + chosen.getName();
            int amount = cartMap.get(identifier) != null ? cartMap.get(identifier) + 1 : 1;
            cartMap.put(identifier, amount);
        }
    }

    private List<Product> selectProducts(HttpServletRequest request) {
        List<Product> products;

        String categoryName = request.getParameter("category");
        ProductCategory category = productCategoryDataStore.find(categoryName);
        String supplierName = request.getParameter("supplier");
        Supplier supplier =supplierDao.find(supplierName);

        if (category == null && supplier == null) {
            products = productDataStore.getBy(defaultCategory);
            System.out.println(categoryName);
            System.out.println(supplierName);
        }
        else if (category == null) {
            products = productDataStore.getBy(supplier);
        }
        else if (supplier == null) {
            products = productDataStore.getBy(category);
        } else {
            // TODO is getProducts(Obj, Obj) necessary?
            products = productDataStore.getProducts(categoryName, supplierName);
        }

        params.put("categ", category);
        params.put("selectedCateg", categoryName);
        params.put("selectedSupplier", supplierName);
        params.put("suppliers", supplierDao.getAll());
        params.put("categories", productCategoryDataStore.getAll());

        return products;
    }
}
