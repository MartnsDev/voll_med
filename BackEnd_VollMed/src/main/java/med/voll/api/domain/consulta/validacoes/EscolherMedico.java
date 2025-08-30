    package med.voll.api.domain.consulta.validacoes;

    import med.voll.api.domain.consulta.ConsultaRepository;
    import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
    import med.voll.api.domain.medico.Medico;
    import med.voll.api.domain.medico.MedicoRepository;
    import med.voll.api.infra.exception.TratadorDeErros.ConsultaException;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Component;

    @Component
    public class EscolherMedico {

        private final MedicoRepository medicoRepository;
        private final ConsultaRepository consultaRepository;

        public EscolherMedico(MedicoRepository medicoRepository, ConsultaRepository consultaRepository) {
            this.medicoRepository = medicoRepository;
            this.consultaRepository = consultaRepository;
        }

        public Medico executar(DadosAgendamentoConsulta dados) {
            Medico medico;

            // Se o ID do médico foi fornecido
            if (dados.idMedico() != null) {
                medico = medicoRepository.findById(dados.idMedico())
                        .orElseThrow(() -> new ConsultaException("Médico não encontrado", HttpStatus.NOT_FOUND));

                if (!medico.getAtivo()) {
                    throw new ConsultaException("Médico não está ativo", HttpStatus.BAD_REQUEST);
                }

            } else {
                // Se não passou médico, deve passar especialidade
                if (dados.especialidade() == null) {
                    throw new ConsultaException("Especialidade é obrigatória quando médico não for escolhido", HttpStatus.BAD_REQUEST);
                }

                medico = medicoRepository.escolherMedicoAleatorioLivreNaData(
                                dados.especialidade(),
                                dados.data()
                        )
                        .orElseThrow(() -> new ConsultaException("Não há médico disponível nessa data", HttpStatus.BAD_REQUEST));
            }

            // ✅ Regra 5: médico não pode estar ocupado no mesmo horário
            boolean medicoOcupado = consultaRepository.existsByMedicoAndData(medico, dados.data());
            if (medicoOcupado) {
                throw new ConsultaException("Médico já possui outra consulta nesse horário", HttpStatus.BAD_REQUEST);
            }

            return medico;
        }
    }
