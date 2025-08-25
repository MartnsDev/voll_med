package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.TratadorDeErros.ConsultaException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorDataFutura implements ValidadorAgendamentoDeConsulta {

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.data().isBefore(LocalDateTime.now())) {
            throw new ConsultaException("Não é possível agendar consultas em datas passadas", HttpStatus.BAD_REQUEST);
        }
    }
}
