package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.TratadorDeErros.ConsultaException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean antesDaAbertura = dataConsulta.getHour() < 7;
        boolean depoisDoEncerramento = dataConsulta.getHour() > 18;

        if (domingo || antesDaAbertura || depoisDoEncerramento) {
            throw new ConsultaException("Horário fora do funcionamento da clínica", HttpStatus.BAD_REQUEST);
        }
    }
}
