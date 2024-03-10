package com.ecommerce.ecommerceInimigosCodigo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ecommerce.connection.Connection;

@Controller
public class ListaUsuarios {

    @GetMapping("/lista-usuarios")
    public String showUserListPage(Model model) {
        Connection connection = new Connection();
        try {
            List<Map<String, Object>> usuarios = connection.buscarTodosUsuarios();
            model.addAttribute("usuarios", usuarios);
            return "lista-usuario";
        } catch (Exception e) {
            e.printStackTrace();
            // Adicione mensagens de erro ou registre em logs para análise posterior
            model.addAttribute("errorMessage", "Erro ao carregar lista de usuários");
            return "error-page";  // Crie uma página de erro apropriada
        }
    }
}