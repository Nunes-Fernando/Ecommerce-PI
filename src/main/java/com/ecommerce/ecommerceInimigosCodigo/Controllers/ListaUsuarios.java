package com.ecommerce.ecommerceInimigosCodigo.Controllers;


import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ListaUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Exibe a lista de usuários na página.
     *
     * @param model Modelo para adicionar atributos.
     * @return O nome da view que renderiza a lista de usuários.
     */
    @GetMapping("/lista")
    public String showUserListPage(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "lista-usuario";
    }

    /**
     * Obtém as informações de um usuário específico.
     *
     * @param id O ID do usuário.
     * @return ResponseEntity com o usuário ou um código HTTP 404 caso não seja encontrado.
     */
    @GetMapping("/obter-usuario")
    public ResponseEntity<Usuario> obterUsuario(@RequestParam Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            return ResponseEntity.ok(optionalUsuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualiza as informações de um usuário específico.
     *
     * @param usuario O usuário com as informações a serem atualizadas.
     * @param model   Modelo para adicionar mensagens de sucesso ou erro.
     * @return Redireciona para a lista de usuários.
     */
    @PutMapping("/editar-usuario")
    public ResponseEntity<String> editarUsuario(@RequestBody Usuario usuario, Model model) {
        // Verifica se o usuário existe
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario.getId());

        if (optionalUsuario.isPresent()) {
            Usuario usuarioExistente = optionalUsuario.get();

            // Atualiza o nome, o CPF e a senha do usuário existente
            usuarioExistente.setNome(usuario.getNome());
            usuarioExistente.setCpf(usuario.getCpf());

            // Atualiza a senha somente se ela foi fornecida e não está vazia
            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                usuarioExistente.setSenha(usuario.getSenha());
            }

            // Salva as alterações no banco de dados
            usuarioRepository.save(usuarioExistente);

            // Mensagem de sucesso
            model.addAttribute("message", "Informações do usuário atualizadas com sucesso.");
            return ResponseEntity.ok("Informações do usuário atualizadas com sucesso.");
        } else {
            // Se o usuário não foi encontrado
            model.addAttribute("error", "Usuário não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @DeleteMapping("/deletar-usuario")
    public ResponseEntity<String> deletarUsuario(@RequestParam Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            usuarioRepository.delete(optionalUsuario.get());
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
}
