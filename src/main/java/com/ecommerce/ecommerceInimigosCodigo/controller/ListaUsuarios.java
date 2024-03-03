package com.ecommerce.ecommerceInimigosCodigo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.connection.Connection;

@Controller
public class ListaUsuarios {

	@GetMapping("/lista-usuarios")
	public String showLoginPage() {
		return "lista-usuario";
	}

	@PostMapping("/buscar-usuarios")
	@ResponseBody
	public List<Map<String, Object>> buscarUsuariosPorNome(@RequestParam("nome") String nome) {
		Connection connection = new Connection();
		List<Map<String, Object>> usuarios = connection.buscarUsuariosPorNome(nome);
		return usuarios;
	}
}
