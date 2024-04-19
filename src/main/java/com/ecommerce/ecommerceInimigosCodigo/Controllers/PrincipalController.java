package com.ecommerce.ecommerceInimigosCodigo.Controllers;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class PrincipalController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/principal")
    public String showPrincipalPage() {
        return "principal";
    }

    @GetMapping("/buscar-usuarios")
    @ResponseBody
    public List<Usuario> buscarUsuarios() {
        return usuarioRepository.findAll();
    }
}
