package com.ecommerce.ecommerceInimigosCodigo.Services;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import java.util.List;
import java.util.Optional;

public class UserServices {

    private static UserServices instance;
    private UsuarioRepository usuarioRepository;


    private UserServices(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public static synchronized UserServices getInstance(UsuarioRepository usuarioRepository) {
        if (instance == null) {
            instance = new UserServices(usuarioRepository);
        }
        return instance;
    }

    public Usuario salvarUsuario(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscarUsuarioPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public boolean verificarSeUsuarioExistePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public boolean verificarCredenciais(String email, String senha) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return usuario.getSenha().equals(senha);
        }
        return false;
    }
}
