package com.ecommerce.ecommerceInimigosCodigo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import ecommerce.connection.ProductDatabaseConnection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
		public String cadastrarProduto(String productName, double productPrice, int productQuantity, String productDescription, int productRating, MultipartFile productImage, Model model) {
			try {
				byte[] imageData = productImage.getBytes();
				boolean produtoCadastrado = ProductDatabaseConnection.saveProduct(productName, productPrice, productQuantity, productDescription, productRating, imageData);
				if (produtoCadastrado) {
					model.addAttribute("mensagem", "Produto cadastrado com sucesso!");
				} else {
					model.addAttribute("mensagem", "Erro ao cadastrar o produto. Por favor, tente novamente.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("mensagem", "Erro ao cadastrar o produto. Por favor, tente novamente.");
			}
			return "cadastrar-erro";
		}
	}

	@PostMapping
	public String cadastrarProduto(@RequestParam("productName") String productName,
								   @RequestParam("productPrice") double productPrice,
								   @RequestParam("productQuantity") int productQuantity,
								   @RequestParam("productDescription") String productDescription,
								   @RequestParam("productRating") int productRating,
								   @RequestParam("productImages") MultipartFile[] productImages,
								   Model model) {
		try {
			List<String> imageNames = new ArrayList<>();
			for (MultipartFile image : productImages) {
				// Salvar a imagem e obter o nome do arquivo salvo
				String imageName = saveImage(image);
				imageNames.add(imageName);
			}

			// Agora você pode salvar o produto no banco de dados junto com os nomes das imagens
			// productService.saveProduct(productName, productPrice, productQuantity, productDescription, productRating, imageNames);

			// Redirecionar para a página de sucesso ou outra página adequada
			return "redirect:/lista-produtos";
		} catch (Exception e) {
			e.printStackTrace();
			// Lidar com o erro
			return "redirect:/error";
		}
	}

	private String saveImage(MultipartFile image) throws IOException {
		String imageName = "prefixo_" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
		byte[] imageData = image.getBytes();
		Path imagePath = Paths.get("caminho/para/salvar/as/imagens/" + imageName);
		Files.write(imagePath, imageData);
		return imageName;
	}
}