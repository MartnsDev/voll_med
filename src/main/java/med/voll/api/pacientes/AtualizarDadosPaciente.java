package med.voll.api.pacientes;

import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.Endereco;

public record AtualizarDadosPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid Endereco endereco
) {
}