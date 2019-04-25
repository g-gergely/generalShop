package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    private Map<String, Integer> cart = ProductController.getCartMap();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        String addKey = request.getParameter("add");
        String removeKey = request.getParameter("remove");

        if (addKey != null) {
            int value = cart.get(addKey);
            cart.put(addKey, value + 1);
        }

        if (removeKey != null) {
            int value = cart.get(removeKey);
            if (value < 2) {
                cart.remove(removeKey);
            } else {
                cart.put(removeKey, value - 1);
            }
        }

        double cartValue = cart.keySet().stream().mapToDouble(x -> Double.parseDouble(x.substring(0, x.indexOf("!"))) * (double) cart.get(x)).sum();

        context.setVariable("cartMap", cart);
        context.setVariable("cartValue", cartValue);
        engine.process("product/cart", context, response.getWriter());
    }
}