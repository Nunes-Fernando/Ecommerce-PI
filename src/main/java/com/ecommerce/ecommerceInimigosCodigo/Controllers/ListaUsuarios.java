package com.ecommerce.ecommerceInimigosCodigo.Controllers;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ListaUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/lista-usuarios")
    public String showUserListPage(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "lista-usuario";
    }
}

