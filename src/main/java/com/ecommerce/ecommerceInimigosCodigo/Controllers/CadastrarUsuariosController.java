package com.ecommerce.ecommerceInimigosCodigo.Controllers;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CadastrarUsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cadastro-usuarios")
    public String showCadastroUsuariosPage() {
        return "cadastro-usuarios";
    }

    @PostMapping("/salvar-usuario")
    public String salvarUsuario(@RequestParam("nome") String nome,
                                @RequestParam("cpf") String cpf,
                                @RequestParam("email") String email,
                                @RequestParam("senha") String senha,
                                @RequestParam("verificarSenha") String verificarSenha,
                                Model model) {

        // Verifique se a senha e a confirmação de senha são iguais
        if (!senha.equals(verificarSenha)) {
            model.addAttribute("erroSenha", "A senha e a confirmação de senha não coincidem");
            return "cadastro-usuarios";
        }

        // Certifique-se de que verificarSenha tenha um valor válido
        if (verificarSenha == null || verificarSenha.isEmpty()) {
            model.addAttribute("erroVerificarSenha", "A confirmação de senha não pode estar vazia");
            return "cadastro-usuarios";
        }

        // Crie uma nova instância de usuário e defina 'Usuario' como valor padrão para 'role'
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setVerificarSenha(verificarSenha);
        usuario.setRole("Usuario"); // Defina 'Usuario' como valor padrão para 'role'

        // Salve o usuário no banco de dados
        usuarioRepository.save(usuario);

        return "redirect:/loja";
    }
}
