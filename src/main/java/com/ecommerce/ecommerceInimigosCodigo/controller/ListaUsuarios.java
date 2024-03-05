package com.ecommerce.ecommerceInimigosCodigo.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.connection.Connection;

@Controller
public class ListaUsuarios {

	@GetMapping("/lista-usuarios")
	public String showLoginPage() {
		return "lista-usuario";
	}
    @PostMapping("/atualizar-usuario")
    @ResponseBody
    public String atualizarUsuario(@RequestBody Map<String, Object> usuarioAtualizado) {
        Connection connection = new Connection();

        // Obtenha os dados do usuário atualizados
        String nomeAntigo = (String) usuarioAtualizado.get("nomeAntigo");
        String novoNome = (String) usuarioAtualizado.get("novoNome");
        String novoCpf = (String) usuarioAtualizado.get("novoCpf");

        try {
            // Adicione a lógica para verificar se o usuário está ativo antes de atualizar
            if (!isUsuarioAtivo(nomeAntigo)) {
                return "Usuário inativo. Não é possível atualizar.";
            }

            // Adicione lógica para atualização no banco de dados
            if (connection.atualizarUsuario(nomeAntigo, novoNome, novoCpf)) {
                return "Usuário atualizado com sucesso!";
            } else {
                return "Falha ao atualizar usuário.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao conectar ao banco de dados";
        }
    }

    // Método para verificar se um usuário está ativo
    private boolean isUsuarioAtivo(String nome) {
        Connection connection = new Connection();
        List<Map<String, Object>> usuarios = connection.buscarUsuariosPorNome(nome);

        // Verifique se o usuário existe e está ativo
        return !usuarios.isEmpty() && "1".equals(usuarios.get(0).get("status").toString());
    }
}
