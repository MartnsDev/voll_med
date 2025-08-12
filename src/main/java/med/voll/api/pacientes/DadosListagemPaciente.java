package med.voll.api.pacientes;

public record DadosListagemPaciente(
        String nome,
        String email,
        String telefone,
        String cpf) {

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf());
    }
}
