package com.ecommerce.ecommerceInimigosCodigo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ecommerce.connection.ProductDatabaseConnection;

@Controller
public class CadastrarProdutos {

	 @GetMapping("/cadastrar-produtos")
	    public String showProductForm() {
	        return "cadastrar-produtos";
	    }
	 @Controller
	 @RequestMapping("/cadastrarProduto")
	 public class CadastrarProdutosController {
	     
	     @PostMapping
	     public String cadastrarProduto(String productName, double productPrice, int productQuantity, String productDescription, int productRating, MultipartFile productImage) {
	         // Lógica para salvar o produto no banco de dados
	         try {
	             byte[] imageData = productImage.getBytes();
	             ProductDatabaseConnection.saveProduct(productName, productPrice, productQuantity, productDescription, productRating, imageData);
	         } catch (Exception e) {
	             e.printStackTrace();
	             // Lidar com o erro
	             return "redirect:/error";
	         }
	         
	         return "redirect:/cadastrar-produtos"; // Redireciona para a página de sucesso ou outra página adequada
	     }
	 }
}