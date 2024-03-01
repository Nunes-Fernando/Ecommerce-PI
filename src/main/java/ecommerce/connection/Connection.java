package ecommerce.connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connection {
	private static String url = "jdbc:mysql://localhost:3306/login";
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
        String query = "SELECT * FROM usuarios WHERE email = ? AND senha_hash = ?";

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
}