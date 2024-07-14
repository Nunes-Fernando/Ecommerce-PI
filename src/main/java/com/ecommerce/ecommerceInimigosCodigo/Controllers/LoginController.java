package com.ecommerce.ecommerceInimigosCodigo.Controllers;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import com.ecommerce.ecommerceInimigosCodigo.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final UserServices userService;

    @Autowired
    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.userService = UserServices.getInstance(usuarioRepository);
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, Model model) {
        boolean loginSucess = userService.verificarCredenciais(email, senha);
        if (loginSucess) {
            return "redirect:/principal";
        } else {
            model.addAttribute("error", "Credenciais inválidas. Por favor, tente novamente.");
            return "login";
        }
    }
}