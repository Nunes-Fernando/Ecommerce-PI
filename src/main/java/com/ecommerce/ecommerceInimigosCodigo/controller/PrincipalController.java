package com.ecommerce.ecommerceInimigosCodigo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.connection.Connection;

@Controller
public class PrincipalController {

    @GetMapping("/principal")
    public String showPrincipalPage() {
        return "principal";
    }

    @GetMapping("/listar-cliente")
    public String redirectToCadastroUsuario() {
        return "redirect:/cadastro-usuarios";
    }
    
    @GetMapping("/buscar-usuarios")
    @ResponseBody // Adicione esta anotação
    public List<Map<String, Object>> buscarUsuariosPorNome(@RequestParam("nome") String nome, Model model) {
        Connection connection = new Connection();

        List<Map<String, Object>> usuariosEncontrados = connection.buscarUsuariosPorNome(nome);

        return usuariosEncontrados;
    }

}
