package com.ecommerce.ecommerceInimigosCodigo.Services;

import java.util.List;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Produto;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> buscarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    // Outros métodos de serviço relacionados aos produtos...
}

