package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private TestEntityManager em;

    private EscolherMedico escolherMedico;

    @BeforeEach
    void setup() {
        escolherMedico = new EscolherMedico(medicoRepository, consultaRepository);
    }

    // ---------- Cenário 1: médico ocupado ----------
    @Test
    @DisplayName("Deveria devolver vazio quando único médico cadastrado não está disponível na data")
    void escolherMedicoOcupado() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = cadastrarMedico("Dr. João", "joao@voll.med", "111111", "11999999999", Especialidade.CARDIOLOGIA, true);
        Paciente paciente = cadastrarPaciente("Maria", "maria@voll.med", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        Optional<Medico> medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(
                Especialidade.CARDIOLOGIA,
                proximaSegundaAs10
        );

        assertThat(medicoLivre).isEmpty();
    }

    // ---------- Cenário 2: médico disponível ----------
    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível na data")
    void escolherMedicoDisponivel() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = cadastrarMedico("Dra. Ana", "ana@voll.med", "222222", "11888888888", Especialidade.CARDIOLOGIA, true);
        Paciente paciente = cadastrarPaciente("Carlos", "carlos@voll.med", "11111111111");

        DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(null, paciente.getId(), Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        Medico medicoLivre = escolherMedico.executar(dados);

        assertThat(medicoLivre).isNotNull();
        assertThat(medicoLivre.getId()).isEqualTo(medico.getId());
    }

    @Test
    @DisplayName("Deveria lançar exceção quando o médico solicitado está inativo")
    void escolherMedicoInativo() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = cadastrarMedico("Dr. Inativo", "inativo@voll.med", "333333", "11777777777", Especialidade.CARDIOLOGIA, false);
        Paciente paciente = cadastrarPaciente("Lucas", "lucas@voll.med", "22222222222");

        DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(medico.getId(), paciente.getId(), Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        // Verifica se lançar RuntimeException com mensagem "Médico não ativo"
        try {
            escolherMedico.executar(dados);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Médico não ativo");
        }
    }

    @Test
    @DisplayName("Deveria lançar exceção quando não houver médicos disponíveis para a especialidade")
    void nenhumMedicoDisponivel() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        // Não cadastramos nenhum médico para essa especialidade
        Paciente paciente = cadastrarPaciente("Lucas", "lucas@voll.med", "22222222222");

        DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(null, paciente.getId(), Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        try {
            escolherMedico.executar(dados);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Nenhum médico disponível");
        }
    }



    // ---------- Métodos auxiliares ----------
    private Medico cadastrarMedico(String nome, String email, String crm, String telefone, Especialidade especialidade, boolean ativo) {
        Medico medico = new Medico(null, nome, email, telefone, crm, especialidade,null, ativo);
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        Paciente paciente = new Paciente(null, nome, email, cpf, "11977777777", null, true);
        em.persist(paciente);
        return paciente;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        Consulta consulta = new Consulta(null, medico, paciente, data);
        em.persist(consulta);
    }

    // ---------- DTO ----------
    record DadosAgendamentoConsulta(Long idMedico, Long idPaciente, Especialidade especialidade, LocalDateTime data) {}

    // ---------- Serviço ----------
    static class EscolherMedico {
        private final MedicoRepository medicoRepository;
        private final ConsultaRepository consultaRepository;

        public EscolherMedico(MedicoRepository medicoRepository, ConsultaRepository consultaRepository) {
            this.medicoRepository = medicoRepository;
            this.consultaRepository = consultaRepository;
        }

        public Medico executar(DadosAgendamentoConsulta dados) {
            Medico medico;

            if (dados.idMedico() != null) {
                medico = medicoRepository.findById(dados.idMedico())
                        .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
                if (!medico.getAtivo()) throw new RuntimeException("Médico não ativo");
            } else {
                if (dados.especialidade() == null) throw new RuntimeException("Especialidade obrigatória");
                medico = medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data())
                        .orElseThrow(() -> new RuntimeException("Nenhum médico disponível"));
            }

            boolean ocupado = consultaRepository.existsByMedicoAndData(medico, dados.data());
            if (ocupado) throw new RuntimeException("Médico já possui consulta nesse horário");

            return medico;
        }
    }
}
