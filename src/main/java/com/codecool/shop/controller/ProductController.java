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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDao = SupplierDaoMem.getInstance();

        Map params = new HashMap<String, Object>() {{
            put("categ", productCategoryDataStore.find(1));
            put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
            put("suppliers", supplierDao.getAll());
            put("categories", productCategoryDataStore.getAll());
        }};

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, resp, request.getServletContext());
        params.forEach(((key, value) -> context.setVariable(String.valueOf(key), value)));
        engine.process("product/index", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDao = SupplierDaoMem.getInstance();
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
            List<Product> products = new ArrayList<>();

            if (category != null && supplier == null) {
                products = productDataStore.getAll().stream()
                        .filter(product -> product.getProductCategory().getName().equals(category))
                        .collect(Collectors.toList());

            } else if (category == null && supplier != null) {
                products = productDataStore.getAll().stream()
                        .filter(product -> product.getSupplier().getName().equals(supplier))
                        .collect(Collectors.toList());

            } else if (category != null && supplier != null) {
                products = productDataStore.getAll().stream()
                        .filter(product -> product.getProductCategory().getName().equals(category))
                        .filter(product -> product.getSupplier().getName().equals(supplier))
                        .collect(Collectors.toList());
            }

            if (products.size() > 0) {
                ProductCategory newCateg = products.stream().map(Product::getProductCategory).findFirst().orElse(null);

                params.put("products", products);
                params.put("categ", newCateg);
            }

            params.forEach(((key, value) -> context.setVariable(String.valueOf(key), value)));
            engine.process("product/index", context, response.getWriter());

        }
    }
}
