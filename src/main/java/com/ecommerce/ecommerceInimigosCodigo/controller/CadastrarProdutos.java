package com.ecommerce.ecommerceInimigosCodigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import ecommerce.connection.ProductDatabaseConnection;


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
                                   @RequestParam("productPrice") double productPrice,
                                   @RequestParam("productQuantity") int productQuantity,
                                   @RequestParam("productDescription") String productDescription,
                                   @RequestParam("productRating") int productRating,
                                   @RequestParam("productImages") MultipartFile[] productImages,
                                   Model model) {
        try {
            List<byte[]> imageDataList = new ArrayList<>();
            for (MultipartFile image : productImages) {
                byte[] imageData = image.getBytes();
                imageDataList.add(imageData);
            }

            // Salvar o produto no banco de dados
            boolean produtoSalvo = ProductDatabaseConnection.saveProduct(productName, productPrice, productQuantity, productDescription, productRating, imageDataList);

            if (produtoSalvo) {
                return "redirect:/lista-produtos"; // Redireciona para a página de listagem de produtos
            } else {
                return "redirect:/error"; // Redireciona para a página de erro em caso de falha ao salvar o produto
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error"; // Redireciona para a página de erro em caso de falha
        }
    }
}