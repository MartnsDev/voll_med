package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosListagemConsulta(
        Long id,
        String nomeMedico,
        String nomePaciente,
        String hora,
        LocalDateTime data
) {
    public DadosListagemConsulta(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getMedico() != null ? consulta.getMedico().getNome() : "Não informado",
                consulta.getPaciente() != null ? consulta.getPaciente().getNome() : "Não informado",
                consulta.getData() != null ? consulta.getData().toLocalTime().toString() : "Não informado",
                consulta.getData()
        );
    }
}
