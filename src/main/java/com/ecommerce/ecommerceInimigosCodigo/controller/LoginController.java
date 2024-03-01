package com.ecommerce.ecommerceInimigosCodigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
                // Credenciais inválidas, adicione uma mensagem de erro ao modelo e retorne para a página de login
                model.addAttribute("error", true);
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trate as exceções adequadamente em um aplicativo real
            return "redirect:/login?error";
        }
    }
}