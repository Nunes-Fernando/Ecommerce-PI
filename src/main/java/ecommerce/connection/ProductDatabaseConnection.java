package ecommerce.connection;

import java.sql.*;
import java.sql.Connection;
import java.util.*;

public class ProductDatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3307/cadastroProdutos";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
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
                                      String productDescription, int productRating, List<byte[]> productImages) {
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
                return false;
            }

            // Obtém o ID do produto inserido
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int productId;
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID do produto.");
            }

            // Insere as imagens na tabela imagens associadas ao produto
            String insertImageQuery = "INSERT INTO imagens (produto_id, imagem_path) VALUES (?, ?)";
            for (byte[] imageData : productImages) {
                PreparedStatement imageStatement = conn.prepareStatement(insertImageQuery);
                imageStatement.setInt(1, productId);
                imageStatement.setBytes(2, imageData);
                imageStatement.executeUpdate();
                imageStatement.close();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveProductImages(String productName, List<byte[]> imageDataList) {
        String query = "INSERT INTO imagens (produto_id, imagem_path) SELECT id, ? FROM produtos WHERE nome = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            for (byte[] imageData : imageDataList) {
                preparedStatement.setBytes(1, imageData);
                preparedStatement.setString(2, productName);
                preparedStatement.addBatch();
            }

            int[] rowsAffected = preparedStatement.executeBatch();
            for (int row : rowsAffected) {
                if (row <= 0) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<Map<String, Object>> getProducts() {
        List<Map<String, Object>> productList = new ArrayList<>();
        String query = "SELECT p.*, i.imagem_path FROM produtos p LEFT JOIN imagens i ON p.id = i.produto_id";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> product = new HashMap<>();
                product.put("id", resultSet.getInt("id"));
                product.put("nome", resultSet.getString("nome"));
                product.put("preco", resultSet.getDouble("preco"));
                product.put("quantidade", resultSet.getInt("quantidade"));
                product.put("descricao", resultSet.getString("descricao"));
                product.put("avaliacao", resultSet.getInt("avaliacao"));

                // Verifica se há imagem associada ao produto
                Blob imageDataBlob = resultSet.getBlob("imagem_path");
                if (imageDataBlob != null) {
                    byte[] imageData = imageDataBlob.getBytes(1, (int) imageDataBlob.length());
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    product.put("imagem", base64Image);
                } else {
                    product.put("imagem", null); // Se não houver imagem, coloque como nulo
                }

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
    }
