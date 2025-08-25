package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consulta")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {


        private final AgendaDeConsultas agenda;
        public ConsultaController(AgendaDeConsultas agenda) {
            this.agenda = agenda;
        }

        @PostMapping
        @Transactional
        public ResponseEntity agendar (@RequestBody @Valid DadosAgendamentoConsulta dados) {
            var dadosAgendamentoDTO = agenda.agendar(dados);
            return ResponseEntity.ok(dadosAgendamentoDTO);
        }
    }
