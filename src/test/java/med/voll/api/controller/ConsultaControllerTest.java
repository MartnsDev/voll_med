package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsultaControllerTest {

    @Mock
    private AgendaDeConsultas agenda;

    @InjectMocks
    private ConsultaController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve agendar consulta e retornar ResponseEntity.ok com os dados")
    void agendar() {
        // Arrange: cria dados de entrada
        DadosAgendamentoConsulta dadosEntrada = new DadosAgendamentoConsulta(
                1L, // idPaciente
                2L, // idMedico
                LocalDateTime.now().plusDays(1), // data da consulta
                Especialidade.CARDIOLOGIA        // exemplo de especialidade
        );

        // Simula retorno do service
        DadosDetalhamentoConsulta dadosRetorno = new DadosDetalhamentoConsulta(
                10L, 1L, 2L, LocalDateTime.now().plusDays(1)
        );

        when(agenda.agendar(dadosEntrada)).thenReturn(dadosRetorno);

        // Act: chama o controller
        ResponseEntity response = controller.agendar(dadosEntrada);

        // Assert: valida o retorno
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dadosRetorno, response.getBody());

        // Verifica se o service foi chamado
        verify(agenda, times(1)).agendar(dadosEntrada);
    }
}
