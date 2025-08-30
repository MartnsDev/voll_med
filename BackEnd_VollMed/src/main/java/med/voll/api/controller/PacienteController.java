package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import med.voll.api.domain.consulta.DadosListagemConsulta;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repositorio;


    // Cadastro de paciente com validação de CPF único
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        // Verifica se já existe paciente com o mesmo CPF
        if (repositorio.existsByCpf(dados.cpf())) {
            return ResponseEntity
                    .badRequest()
                    .body("Já existe um paciente cadastrado com esse CPF.");
        }

        var paciente = repositorio.save(new Paciente(dados));
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    // Listagem de pacientes ativos com paginação
    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repositorio.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }


    // Atualização de dados do paciente
    @PutMapping
    @Transactional
    public ResponseEntity atualizarDadosPaciente(@RequestBody @Valid AtualizarDadosPaciente dados) {
        var paciente = repositorio.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    // Desativação do paciente
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativarPaciente(@PathVariable Long id) {
        var paciente = repositorio.getReferenceById(id);
        paciente.excluirDesativarPaciente();

        return ResponseEntity.noContent().build();
    }

    // Detalhes de um paciente específico
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        var paciente = repositorio.findById(id);

        if (paciente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente.get()));
    }
}
