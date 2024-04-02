package com.ecommerce.ecommerceInimigosCodigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecommerce.connection.ProductDatabaseConnection;


import java.util.List;
import java.util.Map;

@Controller
public class minhaLoja {


    @GetMapping("/minha-loja")
    public String minhaLoja(Model model) {
        try {

            List<Map<String, Object>> products = ProductDatabaseConnection.getProducts();


            model.addAttribute("products", products);

            return "loja";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}