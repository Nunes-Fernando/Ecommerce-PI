package com.ecommerce.ecommerceInimigosCodigo.Repositorio;
import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNome(String nome);

    boolean existsByEmail(String email);

    // Outros métodos conforme necessário
}



