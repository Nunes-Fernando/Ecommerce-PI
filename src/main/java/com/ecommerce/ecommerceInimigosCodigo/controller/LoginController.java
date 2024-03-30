package com.ecommerce.ecommerceInimigosCodigo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecommerce.connection.Connection;  // Certifique-se de que a importação está correta

@Controller
public class LoginController {
    
    private Connection connection = new Connection();

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }
    
    @PostMapping("/processar-login")
    public String processLogin(@RequestParam String usuario, @RequestParam String senha, Model model) {
        try {
            if (connection.verificarCredenciais(usuario, senha)) {
                // Usuário autenticado, redirecione para a página principal
                return "redirect:/principal";
            } else {
                model.addAttribute("error", true);  // Define o atributo de erro como verdadeiro
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            model.addAttribute("error", true);
            return "login";
        }
    }
    
}