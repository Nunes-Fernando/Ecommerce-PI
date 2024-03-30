package com.ecommerce.ecommerceInimigosCodigo.controller;

import java.security.MessageDigest;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecommerce.connection.Connection;

 // Importe a classe Connection corretamente

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
                                @RequestParam(value = "status", defaultValue = "0") String status,
                                Model model) {

        // Verificar se a senha e a confirmação de senha são iguais
        if (!senha.equals(verificarSenha)) {
            model.addAttribute("erroSenha", "A senha e a confirmação de senha não coincidem");
            return "cadastro-usuarios";
        }

        // Encriptar as senhas antes de salvar no banco de dados
        String senhaEncriptada = encriptarSenha(senha);
        String verificarSenhaEncriptada = encriptarSenha(verificarSenha);

        // Restante do seu código...

        try (java.sql.Connection conn = Connection.getConnection()) {
            if (conn != null) {
                String query = "INSERT INTO usuarios (nome, cpf, senha, verificarSenha, grupo, email, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    preparedStatement.setString(1, nome);
                    preparedStatement.setString(2, cpf);
                    preparedStatement.setString(3, senhaEncriptada); // Salvar senha encriptada no banco
                    preparedStatement.setString(4, verificarSenhaEncriptada); // Salvar senha de verificação encriptada no banco
                    preparedStatement.setString(5, grupo);
                    preparedStatement.setString(6, email);
                    preparedStatement.setString(7, status);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        // Remover o campo "cpf" do model antes de redirecionar
                        model.asMap().remove("cpf");

                        // Redirecionar para a página de login após o cadastro bem-sucedido
                        return "redirect:/login";
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

    // Método para encriptar a senha usando SHA-256
    private String encriptarSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
