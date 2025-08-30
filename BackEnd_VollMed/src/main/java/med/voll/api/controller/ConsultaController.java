package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosListagemConsulta; // ADICIONAR ESTE IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // ADICIONAR ESTE IMPORT
import org.springframework.data.domain.Pageable; // ADICIONAR ESTE IMPORT
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    private final AgendaDeConsultas agenda;

    public ConsultaController(AgendaDeConsultas agenda) {
        this.agenda = agenda;
    }

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dadosAgendamentoDTO = agenda.agendar(dados);
        return ResponseEntity.ok(dadosAgendamentoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsulta>> listarConsultas(
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {

        Page<DadosListagemConsulta> page = consultaRepository.findAll(paginacao)
                .map(DadosListagemConsulta::new); // transforma Consulta em DadosListagemConsulta

        return ResponseEntity.ok(page);
    }

}