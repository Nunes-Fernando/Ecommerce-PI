package ecommerce.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/produtos";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados de produtos estabelecida com sucesso.");
            }
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão com o banco de dados de produtos fechada com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean saveProduct(String productName, double productPrice, int productQuantity,
            String productDescription, int productRating, byte[] productImage) {
        String query = "INSERT INTO produtos (nome, preco, quantidade, descricao, avaliacao, imagem) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, productPrice);
            preparedStatement.setInt(3, productQuantity);
            preparedStatement.setString(4, productDescription);
            preparedStatement.setInt(5, productRating);
            preparedStatement.setBytes(6, productImage);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
