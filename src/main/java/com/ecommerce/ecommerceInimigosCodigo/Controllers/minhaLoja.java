package com.ecommerce.ecommerceInimigosCodigo.Controllers;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Produto;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class minhaLoja {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/minha-loja")
    public String showMinhaLojaPage(Model model) {
        List<Produto> produtos = produtoRepository.findAll();

        // Converter imagemPath para string base64
        for (Produto produto : produtos) {
            String base64Image = produto.getImagemPathAsBase64();
            produto.setImagemPathAsBase64(base64Image);
        }

        model.addAttribute("produtos", produtos);
        return "loja";
    }
}


