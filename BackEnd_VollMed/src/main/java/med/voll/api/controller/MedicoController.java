package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repositorio;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosDadosMedico dados, UriComponentsBuilder uriBuilder) {
        List<String> erros = new ArrayList<>();
        if (repositorio.existsByEmail(dados.email())) erros.add("Email");
        if (repositorio.existsByTelefone(dados.telefone())) erros.add("Telefone");
        if (repositorio.existsByCrm(dados.crm())) erros.add("CRM");
        if (!erros.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Campos já cadastrados: " + String.join(", ", erros));
        }
        var medico = repositorio.save(new Medico(dados));
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repositorio.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarDadosMedico(@RequestBody @Valid AtualizarDadosMedico dados) {
        var medico = repositorio.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativarMedico(@PathVariable Long id) {
        var medico = repositorio.getReferenceById(id);
        medico.excluirDesativarMedico();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativar")
    @Transactional
    public ResponseEntity ativarMedico(@PathVariable Long id) {
        var medico = repositorio.getReferenceById(id);
        medico.ativarMedico(); // método que você deve ter na entidade Medico para marcar como ativo
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repositorio.findById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico.get()));
    }



}