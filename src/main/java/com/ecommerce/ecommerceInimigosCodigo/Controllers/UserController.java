package com.ecommerce.ecommerceInimigosCodigo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/loja")
    public String loja() {
        return "loja";
    }

}