package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    ProductDao productDataStore = ProductDaoMem.getInstance();
    ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    SupplierDao supplierDao = SupplierDaoMem.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, resp, request.getServletContext());

        Map params = new HashMap<String, Object>() {{
            put("categ", productCategoryDataStore.find(1));
            put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
            put("suppliers", supplierDao.getAll());
            put("categories", productCategoryDataStore.getAll());
        }};

        params.forEach(((key, value) -> context.setVariable(String.valueOf(key), value)));
        engine.process("product/index", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        String category = request.getParameter("category");
        String supplier = request.getParameter("supplier");

        System.out.println(category);
        System.out.println(supplier);

        Map params = new HashMap<String, Object>() {{
            put("suppliers", supplierDao.getAll());
            put("categories", productCategoryDataStore.getAll());
        }};

        if (category == null && supplier == null) {
            response.sendRedirect("/");
        } else {
            Stream<Product> productStream = productDataStore.getAll().stream();

            if (category != null) {
                productStream = productStream.filter(product -> product.getProductCategory().getName().equals(category));

            }
            if (supplier != null) {
                productStream = productStream.filter(product -> product.getSupplier().getName().equals(supplier));
            }

            List<Product> products = productStream.collect(Collectors.toList());

            if (products.size() > 0) {
                ProductCategory newCategory = products.stream().map(Product::getProductCategory).findFirst().orElse(null);

                params.put("products", products);
                params.put("categ", newCategory);
            }

            params.forEach(((key, value) -> context.setVariable(String.valueOf(key), value)));
            engine.process("product/index", context, response.getWriter());
        }
    }
}
