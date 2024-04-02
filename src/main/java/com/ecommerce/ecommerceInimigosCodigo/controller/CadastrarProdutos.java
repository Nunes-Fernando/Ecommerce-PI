package com.ecommerce.ecommerceInimigosCodigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import ecommerce.connection.ProductDatabaseConnection;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CadastrarProdutos {

    @GetMapping("/cadastrar-produtos")
    public String showProductForm() {
        return "cadastrar-produtos";
    }

    @PostMapping("/cadastrarProduto")
    public String cadastrarProduto(@RequestParam("productName") String productName,
                                   @RequestParam("productPrice") String productPriceStr,
                                   @RequestParam("productQuantity") String productQuantityStr,
                                   @RequestParam("productDescription") String productDescription,
                                   @RequestParam("productRating") String productRatingStr,
                                   @RequestParam(value = "productImages", required = false) MultipartFile[] productImages,
                                   @RequestParam("mainImageIndex") String mainImageIndexStr,
                                   Model model) {
        // Verificar se os valores numéricos são válidos
        int productPrice = 0;
        int productQuantity = 0;
        int productRating = 0;
        int mainImageIndex = 0;

        try {
            if (!productPriceStr.isEmpty()) {
                productPrice = Integer.parseInt(productPriceStr);
            }
            if (!productQuantityStr.isEmpty()) {
                productQuantity = Integer.parseInt(productQuantityStr);
            }
            if (!productRatingStr.isEmpty()) {
                productRating = Integer.parseInt(productRatingStr);
            }
            if (!mainImageIndexStr.isEmpty()) {
                mainImageIndex = Integer.parseInt(mainImageIndexStr);
            }
        } catch (NumberFormatException e) {
            // Tratar caso os valores numéricos não sejam válidos
            e.printStackTrace();
            return "redirect:/error";
        }

        // Processar as imagens apenas se elas foram enviadas
        List<byte[]> imageDataList = new ArrayList<>();
        if (productImages != null && productImages.length > 0) {
            for (MultipartFile image : productImages) {
                try {
                    byte[] imageData = image.getBytes();
                    imageDataList.add(imageData);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/error";
                }
            }
        }


            // Salvar o produto no banco de dados e obter o ID do produto inserido
            int productId = ProductDatabaseConnection.saveProduct(productName, productPrice, productQuantity, productDescription, productRating, imageDataList, mainImageIndex);

            if (productId != -1) {
                return "redirect:/lista-produtos"; // Redireciona para a página de listagem de produtos
            } else {
                return "redirect:/error"; // Redireciona para a página de erro em caso de falha ao salvar o produto
            }

    }
}
