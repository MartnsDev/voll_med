package med.voll.api.controller;

import med.voll.api.medico.AtualizarDadosMedico;
import med.voll.api.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repositorio;

    @PostMapping
    @Transactional
    public void cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados) {
        repositorio.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listarPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repositorio.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizarDadosPaciente(@RequestBody @Valid AtualizarDadosPaciente dados) {
        var paciente = repositorio.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void desativarPaciente(@PathVariable Long id) {
        var paciente = repositorio.getReferenceById(id);
        paciente.excluirDesativarPaciente();
    }

}
