package com.ecommerce.ecommerceInimigosCodigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecommerce.connection.ProductDatabaseConnection;

import java.util.List;
import java.util.Map;

@Controller
public class ListarProdutosController {

    @GetMapping("/lista-produtos")
    public String listarProdutos(Model model) {
        try {
            
            List<Map<String, Object>> products = ProductDatabaseConnection.getProducts();

            
            model.addAttribute("products", products);

            return "lista-produtos"; 
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error"; 
        }
    }
    
    @GetMapping("/redirecionar-para-cadastrar-produtos")
    public String redirecionarParaCadastrarProdutos() {
        return "redirect:/cadastrar-produtos"; // Corrija o caminho se necessário
    }
}
