package ufpb.project.todolist.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class TratadorDeErro {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity TratadorErro404(EntityNotFoundException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity TratadorErro400(MethodArgumentNotValidException e){
        var erros = e.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity TratadorErro400(DataIntegrityViolationException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity TratadorErro400(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity TrataErro500(Exception e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity TrataErro400(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest().body(new DadosErroValidacao(e.getName(), e.getMessage()));
    }


    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError campo){
            this(campo.getField(), campo.getDefaultMessage());
        }
    }

}
