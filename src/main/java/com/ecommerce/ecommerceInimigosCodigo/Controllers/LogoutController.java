package com.ecommerce.ecommerceInimigosCodigo.Controllers;

import com.ecommerce.ecommerceInimigosCodigo.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        sessionService.removeUserFromSession(session);
        return "redirect:/login";
    }
}