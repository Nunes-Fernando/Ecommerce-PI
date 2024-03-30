package ecommerce.connection;

import java.sql.*;
import java.sql.Connection;
import java.util.*;

public class ProductDatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/cadastroProdutos";
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

    public static int saveProduct(String productName, double productPrice, int productQuantity,
                                  String productDescription, int productRating, List<byte[]> productImages, int mainImageIndex) {
        String query = "INSERT INTO produtos (nome, preco, quantidade, descricao, avaliacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, productPrice);
            preparedStatement.setInt(3, productQuantity);
            preparedStatement.setString(4, productDescription);
            preparedStatement.setInt(5, productRating);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                return -1; // Retorne -1 em caso de falha ao salvar o produto
            }

            // Obtém o ID do produto inserido
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int productId;
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID do produto.");
            }

            // Associa as imagens ao produto
            boolean imagesSaved = saveProductImages(productId, productImages, mainImageIndex);

            if (!imagesSaved) {
                throw new SQLException("Falha ao associar as imagens ao produto.");
            }

            return productId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorne -1 em caso de exceção
        }
    }


    public static boolean setMainProductImage(int productId, int mainImageIndex) {
        String query = "UPDATE imagens SET is_main = CASE WHEN id = ? THEN 1 ELSE 0 END WHERE produto_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, mainImageIndex);
            preparedStatement.setInt(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false em caso de erro
        }
    }



    public static boolean saveProductImages(int productId, List<byte[]> imageDataList, int mainImageIndex) {
        String query = "INSERT INTO imagens (produto_id, imagem_path, is_main) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            for (int i = 0; i < imageDataList.size(); i++) {
                preparedStatement.setInt(1, productId);
                preparedStatement.setBytes(2, imageDataList.get(i));
                preparedStatement.setBoolean(3, i == mainImageIndex); // Define se é a imagem principal ou não
                preparedStatement.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<Map<String, Object>> getProducts() {
        List<Map<String, Object>> productList = new ArrayList<>();
        String query = "SELECT p.id, p.nome AS nome_produto, p.preco, p.quantidade, p.descricao, p.avaliacao, i.imagem_path " +
                "FROM produtos p LEFT JOIN imagens i ON p.id = i.produto_id WHERE i.is_main = TRUE";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> product = new HashMap<>();
                product.put("id", resultSet.getInt("id"));
                product.put("nome", resultSet.getString("nome_produto"));
                product.put("preco", resultSet.getDouble("preco"));
                product.put("quantidade", resultSet.getInt("quantidade"));
                product.put("descricao", resultSet.getString("descricao"));
                product.put("avaliacao", resultSet.getInt("avaliacao"));

                Blob imageDataBlob = resultSet.getBlob("imagem_path");
                if (imageDataBlob != null) {
                    byte[] imageData = imageDataBlob.getBytes(1, (int) imageDataBlob.length());
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    product.put("imagem", base64Image);
                } else {
                    product.put("imagem", null);
                }

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

}
