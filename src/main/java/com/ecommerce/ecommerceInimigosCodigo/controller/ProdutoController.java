package com.ecommerce.ecommerceInimigosCodigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProdutoController {

    @GetMapping("/lista-produtos")
    public String listaProdutos(HttpSession session, Model model) {
        // Verifica se o usuário está autenticado e se pertence ao grupo "estoquista"
        if (session.getAttribute("grupo") != null && session.getAttribute("grupo").equals("estoquista")) {
            // Se sim, permite o acesso à lista de produtos
            return "lista-produtos";
        } else {
            // Caso contrário, redireciona para uma página de acesso negado ou outra página adequada
            return "acesso-negado";
        }
    }
}