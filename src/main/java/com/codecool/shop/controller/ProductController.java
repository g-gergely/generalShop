package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoDb;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.order.Order;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private ProductDao productDataStore = ProductDaoDb.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        String categoryName = request.getParameter("category");
        String supplierName = request.getParameter("supplier");

        List<Product> products = productDataStore.selectProducts(categoryName, supplierName, request.getSession());
        Map<String, Object> parameters = productDataStore.getServletParameters(categoryName, supplierName, products);

        String addId = request.getParameter("item_id");

        if (addId != null) {
            addToCart(request);
            String url = (String) request.getSession().getAttribute("url");

            if (url == null) {
                url = "/";
            }
            response.sendRedirect(url);
        } else {
            parameters.forEach(((key, value) -> context.setVariable(String.valueOf(key), value)));
            response.setCharacterEncoding("UTF-8");
            engine.process("product/index", context, response.getWriter());
        }
    }

    private void addToCart(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            order = new Order();
        } else {
            order = (Order) session.getAttribute("order");
        }

        String addId = request.getParameter("item_id");
        if (addId != null) {
            order.getShoppingCart().addProduct(Integer.parseInt(addId));
        }
        session.setAttribute("order", order);
    }
}
