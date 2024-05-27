package com.ecommerce.ecommerceInimigosCodigo;

import com.ecommerce.ecommerceInimigosCodigo.Entidades.Usuario;
import com.ecommerce.ecommerceInimigosCodigo.Repositorio.UsuarioRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestesCadastro {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void incluirUsuarios(){

        Usuario usuario1 = new Usuario();
        usuario1.setNome("Marcio Silva");
        usuario1.setCpf("89079537080");
        usuario1.setSenha("123456");
        usuario1.setVerificarSenha("123456");
        usuario1.setEmail("Mario@gmail.com");
        usuario1.setGrupo("Admin");
        usuario1.setStatus("Ativo");

        assertEquals("Marcio Silva", usuario1.getNome());
        assertEquals("89079537080", usuario1.getCpf());
        assertEquals("123456", usuario1.getSenha());
        assertEquals("123456", usuario1.getVerificarSenha());
        assertEquals("Mario@gmail.com", usuario1.getEmail());
        assertEquals("Admin", usuario1.getGrupo());
        assertEquals("Ativo", usuario1.getStatus());

        usuarioRepository.save(usuario1);
    }

    @Test
    public void emailDuplicado() {
        // Configura o primeiro usuário com um e-mail
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Brenda Silva");
        usuario1.setCpf("69079537080");
        usuario1.setSenha("123456");
        usuario1.setVerificarSenha("123456");
        usuario1.setEmail("Brenda@gmail.com");
        usuario1.setGrupo("Admin");
        usuario1.setStatus("Ativo");

        // Salva o primeiro usuário
        usuarioRepository.save(usuario1);

        // Tenta criar um segundo usuário com o mesmo e-mail
        Usuario usuario2 = new Usuario();
        usuario2.setNome("Joana Santos");
        usuario2.setCpf("12345678901");
        usuario2.setSenha("abcdef");
        usuario2.setVerificarSenha("abcdef");
        usuario2.setEmail("Brenda@gmail.com"); // mesmo e-mail do usuario1
        usuario2.setGrupo("User");
        usuario2.setStatus("Ativo");

        // Tenta salvar e deve lançar uma exceção ou falhar a validação
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.save(usuario2);
        });
    }

    @Test
    public void cpfDuplicado() {
        // Configura o primeiro usuário com um CPF
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Brenda Silva");
        usuario1.setCpf("69079537080");
        usuario1.setSenha("123456");
        usuario1.setVerificarSenha("123456");
        usuario1.setEmail("brenda@gmail.com");
        usuario1.setGrupo("Admin");
        usuario1.setStatus("Ativo");

        // Salva o primeiro usuário
        usuarioRepository.save(usuario1);

        // Tenta criar um segundo usuário com o mesmo CPF
        Usuario usuario2 = new Usuario();
        usuario2.setNome("Joana Santos");
        usuario2.setCpf("69079537080"); // Mesmo CPF do primeiro usuário
        usuario2.setSenha("abcdef");
        usuario2.setVerificarSenha("abcdef");
        usuario2.setEmail("joana@gmail.com");
        usuario2.setGrupo("User");
        usuario2.setStatus("Ativo");

        // Tenta salvar e deve lançar uma exceção ou falhar a validação
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.save(usuario2);
        });
    }

        @Test
        public void senhaConfirmacaoDiferentes() {
            // Configura um novo usuário com senha e confirmação de senha diferentes
            Usuario usuario = new Usuario();
            usuario.setNome("Carlos Oliveira");
            usuario.setCpf("12345678901");S
            usuario.setSenha("abcdef");
            usuario.setVerificarSenha("differentPassword"); // Senha diferente da definida anteriormente
            usuario.setEmail("carlos@gmail.com");
            usuario.setGrupo("User");
            usuario.setStatus("Ativo");

            // Verifica se a senha e a confirmação de senha são diferentes
            (assertNotEquals(usuario.getSenha(), usuario.getVerificarSenha(), "A senha e a confirmação de senha devem ser iguais.");
        }
    }
