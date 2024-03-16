package ecommerce.connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connection {
    private static String url = "jdbc:mysql://localhost:3307/consulta";
    private static String user = "root";
    private static String password = "1234";
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

    public List<Map<String, Object>> buscarTodosUsuarios() {
        String query = "SELECT nome, email, grupo, status FROM usuarios";
        List<Map<String, Object>> usuarios = new ArrayList<>();

        try (java.sql.Connection connection = getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Map<String, Object> usuario = new HashMap<>();
                            usuario.put("nome", resultSet.getString("nome"));
                            usuario.put("email", resultSet.getString("email"));
                            usuario.put("grupo", resultSet.getString("grupo"));
                            usuario.put("status", resultSet.getString("status"));
                            usuarios.add(usuario);
                        }
                    }
                }
            } else {
                System.out.println("A conexão não está aberta. Verifique sua configuração.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
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

    public boolean inserirUsuario(String nome, String cpf, String senha, String verificarSenha, String grupo,
            String email, String status) {
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

    public List<Map<String, Object>> buscarUsuarios() {
        String query = "SELECT nome, email, grupo, status FROM usuarios";
        List<Map<String, Object>> usuarios = new ArrayList<>();

        try (java.sql.Connection connection = getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Map<String, Object> usuario = new HashMap<>();
                            usuario.put("nome", resultSet.getString("nome"));
                            usuario.put("email", resultSet.getString("email"));
                            usuario.put("grupo", resultSet.getString("grupo"));
                            usuario.put("status", resultSet.getString("status"));
                            usuarios.add(usuario);
                        }
                    }
                }
            } else {
                System.out.println("A conexão não está aberta. Verifique sua configuração.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean editarUsuario(String nome, String cpf, String senha) {
        String query = "UPDATE usuarios SET cpf = ?, senha = ? WHERE nome = ?";
        try (java.sql.Connection connection = getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, cpf);
                    preparedStatement.setString(2, senha);
                    preparedStatement.setString(3, nome);
                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0;
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

    public Map<String, Object> obterInformacoesUsuario(String nome) {
        String query = "SELECT * FROM usuarios WHERE nome = ?";
        Map<String, Object> usuario = new HashMap<>();

        try (java.sql.Connection connection = getConnection()) {
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, nome);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            usuario.put("nome", resultSet.getString("nome"));
                            usuario.put("cpf", resultSet.getString("cpf"));
                            usuario.put("senha", resultSet.getString("senha"));
                            // Adicione outros campos conforme necessário
                        }
                    }
                }
            } else {
                System.out.println("A conexão não está aberta. Verifique sua configuração.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
