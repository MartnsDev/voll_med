package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicoControllerTest {

    @Mock
    private MedicoRepository repository;

    @InjectMocks
    private MedicoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar médico quando dados são válidos e não há duplicidade")
    void cadastrar() {
        // Arrange
        DadosDadosMedico dados = new DadosDadosMedico(
                "Dr. João", "joao@email.com", "61999999999", "123456",
                Especialidade.CARDIOLOGIA, new DadosEndereco("Rua A",  "Centro", "00000-000", "Brasília", "DF", null, null)
        );

        Medico medico = new Medico(dados);
        medico.setId(1L);

        when(repository.existsByEmail(dados.email())).thenReturn(false);
        when(repository.existsByTelefone(dados.telefone())).thenReturn(false);
        when(repository.existsByCrm(dados.crm())).thenReturn(false);
        when(repository.save(any(Medico.class))).thenReturn(medico);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");

        // Act
        ResponseEntity response = controller.cadastrar(dados, uriBuilder);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getHeaders().getLocation().toString().contains("/medicos/1"));
        assertInstanceOf(DadosDetalhamentoMedico.class, response.getBody());
        verify(repository, times(1)).save(any(Medico.class));
    }

    @Test
    @DisplayName("Deve listar médicos ativos")
    void listar() {
        Medico medico = new Medico(new DadosDadosMedico(
                "Maria", "maria@email.com", "61988888888", "654321",
                Especialidade.DERMATOLOGIA, new DadosEndereco("Rua B", "Centro", "11111-111", "Brasília", "DF", null, null)
        ));
        medico.setId(2L);

        Page<Medico> page = new PageImpl<>(List.of(medico));
        when(repository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<DadosListagemMedico>> response = controller.listar(Pageable.unpaged());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    @DisplayName("Deve atualizar dados de médico")
    void atualizarDadosMedico() {
        AtualizarDadosMedico dadosAtualizar = new AtualizarDadosMedico(1L, "Novo Nome", null, null);

        Medico medico = mock(Medico.class);
        when(repository.getReferenceById(dadosAtualizar.id())).thenReturn(medico);

        ResponseEntity response = controller.atualizarDadosMedico(dadosAtualizar);

        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(DadosDetalhamentoMedico.class, response.getBody());
        verify(medico, times(1)).atualizarInformacoes(dadosAtualizar);
    }

    @Test
    @DisplayName("Deve desativar médico")
    void desativarMedico() {
        Medico medico = mock(Medico.class);
        when(repository.getReferenceById(1L)).thenReturn(medico);

        ResponseEntity response = controller.desativarMedico(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(medico, times(1)).excluirDesativarMedico();
    }

    @Test
    @DisplayName("Deve detalhar médico por id")
    void detalhar() {
        Medico medico = new Medico(new DadosDadosMedico(
                "Ana", "ana@email.com", "61977777777", "789456",
                Especialidade.GINECOLOGIA, new DadosEndereco("Rua C", "Setor", "22222-222", "Brasília", "DF", null, null)
        ));
        medico.setId(3L);

        when(repository.findById(3L)).thenReturn(Optional.of(medico));

        ResponseEntity response = controller.detalhar(3L);

        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(DadosDetalhamentoMedico.class, response.getBody());
    }
}
