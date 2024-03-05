package ecommerce.connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class Connection {
	private static String url = "jdbc:mysql://localhost:3306/consulta";
	private static String user = "root";
	private static String password = "root";
	private static java.sql.Connection conn = null;

	public static java.sql.Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(url, user, password);
				System.out.println("Conexão estabelecida com sucesso");
			}
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean verificarCredenciais(String email, String senha) {
	    String query = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

	    try (java.sql.Connection connection = getConnection()) {
	        if (connection != null) {
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                preparedStatement.setString(1, email);
	                preparedStatement.setString(2, senha);

	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    boolean hasMatch = resultSet.next();
	                    // Você pode processar ou armazenar informações do ResultSet aqui se necessário

	                    return hasMatch; // Retorna true se houver uma correspondência
	                }
	            }
	        } else {
	            System.out.println("A conexão não está aberta. Verifique sua configuração.");
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Trate as exceções adequadamente em um aplicativo real
	        return false;
	    }
	}

	public boolean inserirUsuario(String nome, String cpf, String senha, String verificarSenha, String grupo, String email, String status) {
	    String query = "INSERT INTO usuarios (nome, cpf, senha, verificarSenha, grupo, email, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (java.sql.Connection connection = getConnection()) {
	        if (connection != null) {
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
	                    return true;
	                } else {
	                    System.out.println("Falha ao inserir usuário. Nenhuma linha afetada.");
	                    return false;
	                }
	            }
	        } else {
	            System.out.println("A conexão não está aberta. Verifique sua configuração.");
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Trate as exceções adequadamente em um aplicativo real
	        return false;
	    }
	}
	
	public List<Map<String, Object>> buscarUsuariosPorNome(String nome) {
        String query = "SELECT nome, cpf, email FROM usuarios WHERE nome LIKE ?";
        List<Map<String, Object>> usuarios = new ArrayList<>();

        try (java.sql.Connection connection = getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, "%" + nome + "%");

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Map<String, Object> usuario = new HashMap<>();
                            usuario.put("nome", resultSet.getString("nome"));
                            usuario.put("email", resultSet.getString("email"));
                            usuario.put("cpf", resultSet.getString("cpf"));
                            usuarios.add(usuario);
                        }
                    }
                }
            } else {
                System.out.println("A conexão não está aberta. Verifique sua configuração.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Lide com a exceção de forma adequada em um aplicativo real
        }

        return usuarios;
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
	            // Use uma consulta de atualização com base no nome do usuário
	            String query = "UPDATE usuarios SET cpf=?, senha=?, verificarSenha=?, grupo=?, email=?, status=? WHERE nome=?";
	            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
	                preparedStatement.setString(1, cpf);
	                preparedStatement.setString(2, senha);
	                preparedStatement.setString(3, verificarSenha);
	                preparedStatement.setString(4, grupo);
	                preparedStatement.setString(5, email);
	                preparedStatement.setString(6, status);
	                preparedStatement.setString(7, nome); // Define o nome do usuário para a cláusula WHERE

	                int rowsAffected = preparedStatement.executeUpdate();

	                if (rowsAffected > 0) {
	                    System.out.println("Usuário atualizado com sucesso!");
	                    return "redirect:/login"; // Redireciona para a página de login após a atualização bem-sucedida
	                } else {
	                    model.addAttribute("erroBanco", "Falha ao atualizar usuário. Nenhuma linha afetada.");
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
	public boolean atualizarUsuario(String nomeAntigo, String novoNome, String novoCpf) {
        try (java.sql.Connection connection = getConnection()) {
            if (connection != null) {
                // Verificar se o usuário existe antes de atualizar
                String verificaExistenciaQuery = "SELECT * FROM usuarios WHERE nome = ?";
                try (PreparedStatement existenciaStatement = connection.prepareStatement(verificaExistenciaQuery)) {
                    existenciaStatement.setString(1, nomeAntigo);

                    try (ResultSet existenciaResultSet = existenciaStatement.executeQuery()) {
                        if (!existenciaResultSet.next()) {
                            System.out.println("Usuário não encontrado para atualização.");
                            return false;
                        }
                    }
                }

                // Atualizar o usuário
                String atualizarQuery = "UPDATE usuarios SET nome=?, cpf=? WHERE nome=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(atualizarQuery)) {
                    preparedStatement.setString(1, novoNome);
                    preparedStatement.setString(2, novoCpf);
                    preparedStatement.setString(3, nomeAntigo);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Usuário atualizado com sucesso!");
                        return true;
                    } else {
                        System.out.println("Falha ao atualizar usuário. Nenhuma linha afetada.");
                        return false;
                    }
                }
            } else {
                System.out.println("A conexão não está aberta. Verifique sua configuração.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}