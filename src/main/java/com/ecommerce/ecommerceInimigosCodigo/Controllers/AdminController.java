package com.ecommerce.ecommerceInimigosCodigo.Controllers;


import com.ecommerce.ecommerceInimigosCodigo.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/admin/principal")
    public String adminPrincipal(HttpSession session, Model model) {
        String userEmail = sessionService.getUserEmailFromSession(session);
        if (userEmail == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver autenticado
        }
        String userRole = sessionService.getUserRoleFromSession(session);
        if (!"ADMIN".equals(userRole)) {
            return "redirect:/loja"; // Redireciona para a página da loja se o usuário não for um admin
        }
        // Se o usuário estiver autenticado e for um admin, permite o acesso à página principal do admin
        return "admin-principal";
    }

    // Implemente o mesmo controle de acesso para outras páginas do admin
}