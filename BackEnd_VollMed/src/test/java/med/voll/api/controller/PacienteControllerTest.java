//package med.voll.api.controller;
//
//import med.voll.api.domain.endereco.DadosEndereco;
//import med.voll.api.domain.paciente.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class PacienteControllerTest {
//
//    @Mock
//    private PacienteRepository repository;
//
//    @InjectMocks
//    private PacienteController controller;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("Deve cadastrar paciente com sucesso")
//    void cadastrarPaciente() {
//        // Arrange
//        DadosCadastroPaciente dados = new DadosCadastroPaciente(
//                "Carlos",
//                "carlos@email.com",
//                "61999999999",
//                "12345678900",
//                new DadosEndereco("Rua A", "Centro", "00000-000", "Brasília", "DF", null, null)
//        );
//
//        Paciente paciente = new Paciente(dados);
//        paciente.setId(1L);
//
//        when(repository.save(any(Paciente.class))).thenReturn(paciente);
//
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");
//
//        // Act
//        ResponseEntity response = controller.cadastrarPaciente(dados, uriBuilder);
//
//        // Assert
//        assertEquals(201, response.getStatusCodeValue());
//        assertTrue(response.getHeaders().getLocation().toString().contains("/pacientes/1"));
//        assertInstanceOf(DadosDetalhamentoPaciente.class, response.getBody());
//        verify(repository, times(1)).save(any(Paciente.class));
//    }
//
//    @Test
//    @DisplayName("Deve listar pacientes ativos")
//    void listarPacientes() {
//        Paciente paciente = new Paciente(new DadosCadastroPaciente(
//                "Maria",
//                "maria@email.com",
//                "61988888888",
//                "98765432100",
//                new DadosEndereco("Rua B", "Centro", "11111-111", "Brasília", "DF", null, null)
//        ));
//        paciente.setId(2L);
//
//        Page<Paciente> page = new PageImpl<>(List.of(paciente));
//        when(repository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(page);
//
//        ResponseEntity<Page<DadosListagemPaciente>> response = controller.listarPacientes(Pageable.unpaged());
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(1, response.getBody().getTotalElements());
//    }
//
//    @Test
//    @DisplayName("Deve atualizar dados de paciente")
//    void atualizarDadosPaciente() {
//        AtualizarDadosPaciente dadosAtualizar = new AtualizarDadosPaciente(1L, "Novo Nome", null, null);
//
//        Paciente paciente = mock(Paciente.class);
//        when(repository.getReferenceById(dadosAtualizar.id())).thenReturn(paciente);
//
//        ResponseEntity response = controller.atualizarDadosPaciente(dadosAtualizar);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertInstanceOf(DadosDetalhamentoPaciente.class, response.getBody());
//        verify(paciente, times(1)).atualizarInformacoes(dadosAtualizar);
//    }
//
//    @Test
//    @DisplayName("Deve desativar paciente")
//    void desativarPaciente() {
//        Paciente paciente = mock(Paciente.class);
//        when(repository.getReferenceById(1L)).thenReturn(paciente);
//
//        ResponseEntity response = controller.desativarPaciente(1L);
//
//        assertEquals(204, response.getStatusCodeValue());
//        verify(paciente, times(1)).excluirDesativarPaciente();
//    }
//
//    @Test
//    @DisplayName("Deve detalhar paciente existente")
//    void detalhar() {
//        Paciente paciente = new Paciente(new DadosCadastroPaciente(
//                "Ana",
//                "ana@email.com",
//                "61977777777",
//                "12312312399",
//                new DadosEndereco("Rua C", "Setor", "22222-222", "Brasília", "DF", null, null)
//        ));
//        paciente.setId(3L);
//
//        when(repository.findById(3L)).thenReturn(Optional.of(paciente));
//
//        ResponseEntity response = controller.detalhar(3L);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertInstanceOf(DadosDetalhamentoPaciente.class, response.getBody());
//    }
//
//    @Test
//    @DisplayName("Deve retornar 404 ao detalhar paciente inexistente")
//    void detalharPacienteInexistente() {
//        when(repository.findById(99L)).thenReturn(Optional.empty());
//
//        ResponseEntity response = controller.detalhar(99L);
//
//        assertEquals(404, response.getStatusCodeValue());
//    }
//}
