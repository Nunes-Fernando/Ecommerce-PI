package com.ecommerce.ecommerceInimigosCodigo.Controllers;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Produto;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CadastrarProdutos {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/cadastrar-produtos")
    public String showCadastroProdutosPage() {
        return "cadastrar-produtos";
    }

    @PostMapping("/cadastrarProduto")
    public String cadastrarProduto(@RequestParam("productName") String productName,
                                   @RequestParam("productPrice") double productPrice,
                                   @RequestParam("productQuantity") int productQuantity,
                                   @RequestParam("productDescription") String productDescription,
                                   @RequestParam("productRating") int productRating,
                                   @RequestParam("productImage") MultipartFile productImage,
                                   Model model) {
        // Cria uma nova instância de produto
        Produto produto = new Produto();
        produto.setNome(productName);
        produto.setPreco(productPrice);
        produto.setQuantidade(productQuantity);
        produto.setDescricao(productDescription);
        produto.setAvaliacao(productRating);

        // Processar a imagem se presente
        if (!productImage.isEmpty()) {
            try {
                byte[] imageBytes = productImage.getBytes();
                produto.setImagemPath(imageBytes);
            } catch (IOException e) {
                // Exibe uma mensagem de erro no formulário em caso de falha no upload
                model.addAttribute("errorMessage", "Erro ao processar a imagem: " + e.getMessage());
                return "cadastrar-produtos";
            }
        } else {
            model.addAttribute("errorMessage", "Por favor, selecione uma imagem para o produto.");
            return "cadastrar-produtos";
        }

        // Salva o produto no banco de dados
        produtoRepository.save(produto);

        // Redireciona para a página da loja após o cadastro
        return "redirect:/minha-loja";
    }
}
