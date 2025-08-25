package med.voll.api.controller;

import med.voll.api.domain.usuario.AutenticacaoService;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AutenticacaoControllerTest {

    @InjectMocks
    private AutenticacaoController controller;

    @Mock
    private AutenticacaoService autenticacaoService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioRepository repository; // mock do repositório

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve Fazer Login e devolver o token sem erros")
    void efetuarLogin() {
        // Arrange
        DadosAutenticacao dados = new DadosAutenticacao("user1", "1234");
        Usuario usuario = new Usuario("user1", "1234");
        when(autenticacaoService.loadUserByUsername("user1")).thenReturn(usuario);
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
        when(tokenService.gerarToken(any())).thenReturn("token123");
        // Act
        ResponseEntity<?> response = controller.efetuarLogin(dados);
        Object body = response.getBody();
        // Assert
        assertNotNull(body);
        assertTrue(body.toString().contains("token123"));
        verify(authenticationManager, times(1)).authenticate(any());
        verify(tokenService, times(1)).gerarToken(any());
    }

    @Test
    @DisplayName("Deve cadastrar usuário e devolver token real")
    void cadastrarUsuario() {
        // Arrange
        DadosAutenticacao dados = new DadosAutenticacao("teste@gmail.com", "123456");
        when(repository.findByLogin(dados.login())).thenReturn(null);
        Usuario usuarioSalvo = new Usuario(dados.login(), dados.senha());
        when(repository.save(any())).thenReturn(usuarioSalvo);
        // Instancia real do TokenService
        TokenService realTokenService = new TokenService();
        ReflectionTestUtils.setField(realTokenService, "secret", "MinhaChaveSuperSecreta1234567890123456");

        // Injeta o TokenService real no controller mesmo sendo private
        ReflectionTestUtils.setField(controller, "tokenService", realTokenService);
        // Act
        ResponseEntity<?> response = controller.cadastrarUsuario(dados);
        var body = response.getBody();
        // Assert
        assertNotNull(body);
        System.out.println("Token real gerado: " + body.toString());
    }

    @Test
    @DisplayName("Deve Fazer Login e devolver token real")
    void efetuarLoginComTokenReal() {
        // Arrange
        DadosAutenticacao dados = new DadosAutenticacao("user1", "1234");
        Usuario usuario = new Usuario("user1", "1234");

        when(autenticacaoService.loadUserByUsername("user1")).thenReturn(usuario);
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
        // Instancia real do TokenService
        TokenService realTokenService = new TokenService();
        ReflectionTestUtils.setField(realTokenService, "secret", "MinhaChaveSuperSecreta1234567890123456");
        // Injeta o TokenService real no controller
        ReflectionTestUtils.setField(controller, "tokenService", realTokenService);
        // Act
        ResponseEntity<?> response = controller.efetuarLogin(dados);
        var body = response.getBody();
        // Assert
        assertNotNull(body);
        System.out.println("Token real gerado no login: " + body.toString());
    }

    @BeforeEach
    void seteUp() {
        MockitoAnnotations.openMocks(this);

        // Injeta o PasswordEncoder real no controller
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ReflectionTestUtils.setField(controller, "passwordEncoder", passwordEncoder);
    }


}


