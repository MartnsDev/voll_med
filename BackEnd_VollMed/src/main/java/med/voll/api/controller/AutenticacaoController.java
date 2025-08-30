package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import med.voll.api.infra.security.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController

public class AutenticacaoController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            // Autentica o usuário
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var authentication = manager.authenticate(authenticationToken);

            var userDetails = (UserDetails) authentication.getPrincipal();
            var token = tokenService.gerarToken(userDetails.getUsername());

            return ResponseEntity.ok(new TokenResponse(token, "Login realizado com sucesso"));

        } catch (Exception ex) {
            System.out.println("Falha no login: " + ex.getMessage());
            // Retorna 401 Unauthorized para qualquer falha de login
            return ResponseEntity.status(401).body("Login ou senha inválidos");
        }
    }

    private record TokenResponse(String token, String mensagem) {}

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            // Verifica se o usuário já existe
            var usuario = repository.findByLogin(dados.login());
            if (usuario != null) {
                return ResponseEntity.status(409)
                        .body(new ErrorResponse("Usuário já existe"));
            }
            // Cria novo usuário
            var novoUsuario = new Usuario(dados.login(), passwordEncoder.encode(dados.senha()));
            repository.save(novoUsuario);
            // Gera token
            var token = tokenService.gerarToken(novoUsuario.getUsername());
            return ResponseEntity.ok(new TokenResponse(token, "Usuário criado e autenticado com sucesso"));
        } catch (Exception e) {
            // Captura qualquer erro inesperado
            return ResponseEntity.status(500)
                    .body(new ErrorResponse("Erro interno: " + e.getMessage()));
        }
    }
    private record ErrorResponse(String mensagem) {}


    }
