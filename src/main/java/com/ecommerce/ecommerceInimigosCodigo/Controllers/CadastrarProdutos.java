package com.ecommerce.ecommerceInimigosCodigo.Controllers;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Produto;
import com.ecommerce.ecommerceInimigosCodigo.Entidades.ProdutoDTO;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.Base64Utils;
import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CadastrarProdutos {


    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/cadastrar-produtos")
    public String showCadastroProdutosPage() {
        return "cadastrar-produtos";
    }

    @PostMapping("/processarCadastroProduto")
    public String processarCadastroProduto(@RequestParam("productName") String productName,
                                           @RequestParam("productPrice") double productPrice,
                                           @RequestParam("productQuantity") int productQuantity,
                                           @RequestParam("productDescription") String productDescription,
                                           @RequestParam("productRating") int productRating,
                                           @RequestParam("productImage") MultipartFile productImage,
                                           Model model) {
        Produto produto = new Produto();
        produto.setNome(productName);
        produto.setPreco(productPrice);
        produto.setQuantidade(productQuantity);
        produto.setDescricao(productDescription);
        produto.setAvaliacao(productRating);

        if (!productImage.isEmpty()) {
            try {
                byte[] imageBytes = productImage.getBytes();
                produto.setImagemPath(imageBytes);
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Erro ao processar a imagem: " + e.getMessage());
                return "cadastrar-produtos";
            }
        } else {
            model.addAttribute("errorMessage", "Por favor, selecione uma imagem para o produto.");
            return "cadastrar-produtos";
        }

        produtoRepository.save(produto);
        return "redirect:/listar-produtos-cadastrados";
    }


    @GetMapping("/listar-produtos-cadastrados")
    public String listarProdutosCadastrados(Model model) {
        List<Produto> produtos = produtoRepository.findAll();

        // Converter imagem para Base64 e adicionar ao produto
        List<ProdutoDTO> produtosDTO = produtos.stream().map(produto -> {
            ProdutoDTO dto = new ProdutoDTO();
            dto.setId(produto.getId());
            dto.setNome(produto.getNome());
            dto.setPreco(produto.getPreco());
            dto.setQuantidade(produto.getQuantidade());
            dto.setDescricao(produto.getDescricao());
            dto.setAvaliacao(produto.getAvaliacao());
            if (produto.getImagemPath() != null) {
                dto.setImagemBase64(Base64Utils.encodeToString(produto.getImagemPath()));
            }
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("produtos", produtosDTO);
        return "listar-produtos";
    }


    @GetMapping("/editar-produto/{id}")
    public String editarProduto(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do produto inválido: " + id));
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setQuantidade(produto.getQuantidade());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setAvaliacao(produto.getAvaliacao());
        if (produto.getImagemPath() != null) {
            produtoDTO.setImagemBase64(Base64Utils.encodeToString(produto.getImagemPath()));
        }
        model.addAttribute("produto", produtoDTO);
        return "editar-produto";
    }

    @PostMapping("/atualizar-produto/{id}")
    public String atualizarProduto(@PathVariable("id") Long id,
                                   @ModelAttribute ProdutoDTO produtoDTO,
                                   @RequestParam("imagem") MultipartFile imagem,
                                   Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do produto inválido: " + id));

        produto.setNome(produtoDTO.getNome());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setAvaliacao(produtoDTO.getAvaliacao());

        if (!imagem.isEmpty()) {
            try {
                byte[] imageBytes = imagem.getBytes();
                produto.setImagemPath(imageBytes);
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Erro ao processar a imagem: " + e.getMessage());
                return "editar-produto";
            }
        }

        produtoRepository.save(produto);
        return "redirect:/listar-produtos-cadastrados";
    }

    @PostMapping("/deletar-produto/{id}")
    public String deletarProduto(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        produtoRepository.delete(produto);
        return "redirect:/listar-produtos-cadastrados";
    }
}
