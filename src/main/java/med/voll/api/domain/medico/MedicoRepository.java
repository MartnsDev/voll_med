package med.voll.api.domain.medico;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    // Verifica se já existe algum médico com o mesmo email, telefone ou CRM
    boolean existsByEmail(@NotBlank @Email String email);
    boolean existsByTelefone(@NotBlank @Pattern(regexp = "\\d{11}") String telefone);
    boolean existsByCrm(@NotBlank @Pattern(regexp = "\\d{4,6}") String crm);




    @Query("""
        SELECT m FROM Medico m
        WHERE m.especialidade = :especialidade
          AND m.ativo = true
          AND m.id NOT IN (
              SELECT c.medico.id FROM Consulta c
              WHERE c.data = :data
          )
        ORDER BY RAND()
        LIMIT 1
        """)
    Optional<Medico> escolherMedicoAleatorioLivreNaData(
            @Param("especialidade") Especialidade especialidade,
            @Param("data") LocalDateTime data
    );
}








