package com.ecommerce.ecommerceInimigosCodigo.Services;

import java.util.List;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Outros métodos de serviço relacionados aos usuários...
}

