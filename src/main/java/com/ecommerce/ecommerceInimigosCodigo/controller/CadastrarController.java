package com.ecommerce.ecommerceInimigosCodigo.controller;

import java.sql.PreparedStatement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecommerce.connection.Connection;

@Controller
public class CadastrarController {

    @GetMapping("/cadastrar-usuarios")
    public String showLoginPage() {
        return "cadastro-usuarios";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@RequestParam("nome") String nome,
                                @RequestParam("cpf") String cpf,
                                @RequestParam("email") String email,
                                @RequestParam("senha") String senha,
                                @RequestParam("verificarSenha") String verificarSenha,
                                @RequestParam("grupo") String grupo,
                                @RequestParam("status") String status,
                                Model model) {

        // Verificar se a senha e a confirmação de senha são iguais
        if (!senha.equals(verificarSenha)) {
            model.addAttribute("erroSenha", "A senha e a confirmação de senha não coincidem");
            return "cadastro-usuarios";
        }

        Connection connection = new Connection();

        try (java.sql.Connection conn = connection.getConnection()) {
            if (conn != null) {
                String query = "INSERT INTO usuarios (nome, cpf, senha, verificarSenha, grupo, email, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    preparedStatement.setString(1, nome);
                    preparedStatement.setString(2, cpf);
                    preparedStatement.setString(3, senha);
                    preparedStatement.setString(4, verificarSenha);
                    preparedStatement.setString(5, grupo);
                    preparedStatement.setString(6, email);
                    preparedStatement.setString(7, status);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Usuário inserido com sucesso!");
                        return "redirect:/login"; // Redireciona para a página de login após o cadastro bem-sucedido
                    } else {
                        model.addAttribute("erroBanco", "Falha ao inserir usuário. Nenhuma linha afetada.");
                        return "cadastro-usuarios";
                    }
                }
            } else {
                model.addAttribute("erroBanco", "A conexão não está aberta. Verifique sua configuração.");
                return "cadastro-usuarios";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erroBanco", "Erro ao conectar ao banco de dados");
            return "cadastro-usuarios";
        }
    }

}

