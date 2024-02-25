package com.ecommerce.ecommerceInimigosCodigo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListaUsuarios {
    
     @GetMapping("/lista-usuarios")
    public String showLoginPage() {
        return "lista-usuario"; 
    }

}
