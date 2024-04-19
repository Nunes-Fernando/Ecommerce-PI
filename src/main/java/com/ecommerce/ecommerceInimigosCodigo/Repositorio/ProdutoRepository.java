package com.ecommerce.ecommerceInimigosCodigo.Repositorio;
import com.ecommerce.ecommerceInimigosCodigo.Entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Consulta para buscar produtos pelo nome
    List<Produto> findByNome(String nome);

    // Consulta para buscar produtos por preço mínimo e máximo
    List<Produto> findByPrecoBetween(double precoMin, double precoMax);

    // Consulta para buscar produtos por quantidade mínima
    List<Produto> findByQuantidadeGreaterThanEqual(int quantidade);

    // Consulta para buscar produtos por avaliação mínima
    List<Produto> findByAvaliacaoGreaterThanEqual(int avaliacao);

    // Consulta para buscar produtos por descrição contendo um termo específico
    List<Produto> findByDescricaoContaining(String termo);

    // Consulta para buscar produtos por preço máximo
    List<Produto> findByPrecoLessThanEqual(double precoMax);

    // Consulta para buscar produtos por preço mínimo
    List<Produto> findByPrecoGreaterThanEqual(double precoMin);

}
