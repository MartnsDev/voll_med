package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.infra.exception.ExceptionDTO.DadosErroValidacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class TratadorDeErros {

    //Erro 404 notFound
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    //Erro 400 para Invalidação (Problema com o cliente)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    //TratarErro noSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity tratarErro404MedicoNaoEncontrado(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado.");
    }

    //Tratar Json enviada incorretamente no Body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Campos incorretos ou Invalidos");
    }

    //Erro 500 - Erro interno no servidor
    private static final Logger log = LoggerFactory.getLogger(TratadorDeErros.class);
    @ExceptionHandler(Exception.class)
    public ModelAndView tratarErro500(Exception ex) {
        log.error("Erro inesperado: ", ex);
        ModelAndView mv = new ModelAndView("errors/erro500");
        mv.addObject("mensagem", "Erro inesperado ao processar sua solicitação. Entre em contato com o suporte.");
        mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mv;
    }

    //Erros de consulta para a classe AgendaDeConsulta
    public static class ConsultaException extends RuntimeException {
        private final HttpStatus status;
        public ConsultaException(String mensagem, HttpStatus status) {
            super(mensagem);
            this.status = status;
        }
        public HttpStatus getStatus() {
            return status;
        }
    }
    @ExceptionHandler(ConsultaException.class)
    public ResponseEntity<Map<String, String>> handleConsultaException(ConsultaException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("erro", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }




}


