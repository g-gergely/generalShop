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
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private SupplierDao supplierDao = SupplierDaoMem.getInstance();
    private String category = "";
    private String supplier = "";

    private ProductCategory categoryObj= productCategoryDataStore.find(1);
    private Supplier supplierObj= null;

    private static Map<String, Integer> cartMap = new HashMap<>();

    public static Map<String, Integer> getCartMap() {
        return cartMap;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        Map params = new HashMap<String, Object>() {{
            put("categ", categoryObj);
            put("selectedCateg", category);
            put("selectedSupplier", supplier);
            put("suppliers", supplierDao.getAll());
            put("categories", productCategoryDataStore.getAll());
        }};

        List<Product> products = productDataStore.getProducts(supplierObj, categoryObj);
        params.put("products", products);


        params.forEach(((key, value) -> context.setVariable(String.valueOf(key), value)));
        response.setCharacterEncoding("UTF-8");
        engine.process("product/index", context, response.getWriter());

        String addId = request.getParameter("item_id");

        if (addId != null) {
            Product chosen = productDataStore.find(Integer.parseInt(addId));
            String identifier = chosen.getDefaultPrice() + "!" + addId + "?" + chosen.getName();
            int amount = cartMap.get(identifier) != null ? cartMap.get(identifier) + 1 : 1;
            cartMap.put(identifier, amount);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        category = request.getParameter("category");
        supplier = request.getParameter("supplier");

        Map params = new HashMap<String, Object>() {{
            put("suppliers", supplierDao.getAll());
            put("categories", productCategoryDataStore.getAll());
        }};

        if (category.equals("") && supplier.equals("")) {
            categoryObj= productCategoryDataStore.find(1);
            supplierObj= null;
            response.sendRedirect("/");
        } else {
            List<Product> products = productDataStore.getProducts(category, supplier);
            ProductCategory newCategory = products.stream()
                    .map(Product::getProductCategory)
                    .findFirst()
                    .orElse(null);


            if (products.size() > 0) {
                params.put("products", products);
                params.put("categ", newCategory);
                params.put("selectedCateg", newCategory.getName());
                params.put("selectedSupplier", supplier);
                category = products.get(0).getProductCategory().getName();
                supplier = products.get(0).getSupplier().getName();
            }

            categoryObj = productCategoryDataStore.getAll().stream()
                    .filter(categ -> categ.getName().equals(category))
                    .findFirst()
                    .orElse(null);

            supplierObj = supplierDao.getAll().stream()
                    .filter(supp -> supp.getName().equals(supplier))
                    .findFirst()
                    .orElse(null);

            params.forEach(((key, value) -> context.setVariable(String.valueOf(key), value)));
            response.setCharacterEncoding("UTF-8");
            engine.process("product/index", context, response.getWriter());
        }
    }
}
