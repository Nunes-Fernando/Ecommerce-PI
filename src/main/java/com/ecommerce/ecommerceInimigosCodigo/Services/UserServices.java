package com.ecommerce.ecommerceInimigosCodigo.Services;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UserServices(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        // Aqui você pode adicionar lógica de validação antes de salvar o usuário, se necessário
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
            // Verificar se a senha fornecida corresponde à senha armazenada para o usuário
            return usuario.getSenha().equals(senha);
        }
        return false; // Retorna false se não houver usuário com o email fornecido
    }

    // Outros métodos conforme necessário
}
