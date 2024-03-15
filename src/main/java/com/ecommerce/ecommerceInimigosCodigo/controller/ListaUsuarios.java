package com.ecommerce.ecommerceInimigosCodigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ecommerce.connection.Connection;

@Controller
public class ListaUsuarios {

    @GetMapping("/lista-usuarios")
    public String showUserListPage(Model model) {
        Connection connection = new Connection();
        try {
            // Obtém a lista de todos os usuários
            List<Map<String, Object>> usuarios = connection.buscarTodosUsuarios();

            // Cria uma nova lista para armazenar os usuários com informações específicas
            List<Map<String, Object>> usuariosFormatados = new ArrayList<>();

            // Itera sobre cada usuário e seleciona apenas as informações desejadas
            for (Map<String, Object> usuario : usuarios) {
                Map<String, Object> usuarioFormatado = new HashMap<>();
                usuarioFormatado.put("nome", usuario.get("nome"));
                usuarioFormatado.put("email", usuario.get("email"));
                usuarioFormatado.put("grupo", usuario.get("grupo"));
                usuarioFormatado.put("status", usuario.get("status"));
                usuariosFormatados.add(usuarioFormatado);
            }

            // Adiciona a lista formatada ao modelo
            model.addAttribute("usuarios", usuariosFormatados);

            return "lista-usuario";
        } catch (Exception e) {
            e.printStackTrace();
            // Adicione mensagens de erro ou registre em logs para análise posterior
            model.addAttribute("errorMessage", "Erro ao carregar lista de usuários");
            return "error-page";  // Crie uma página de erro apropriada
        }
    }
    
}
