package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoDb;
import com.codecool.shop.model.order.Order;
import com.codecool.shop.model.order.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    private ProductDao productDataStore = ProductDaoDb.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        String addId = request.getParameter("add");
        String removeId = request.getParameter("remove");

        Order order = (Order) request.getSession().getAttribute("order");
        ShoppingCart cart = order.getShoppingCart();

        if (addId != null) {
            cart.addProduct(Integer.parseInt(addId));
        }

        if (removeId != null) {
            cart.removeProduct(Integer.parseInt(removeId));
        }

        String url = (String) request.getSession().getAttribute("url");

        context.setVariable("cartMap", cart.getCart(productDataStore));
        context.setVariable("cartValue", cart.getTotalPrice(productDataStore));
        context.setVariable("previousURL", url == null ? "/" : url);
        engine.process("product/cart", context, response.getWriter());
    }
}