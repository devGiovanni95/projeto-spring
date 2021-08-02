package br.com.projetospring.controller_exception;

import br.com.projetospring.exceptions.DataIntegrityException;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/*Objeto de interceçao que caso houver uma exceçao ele vai capturar e fazer o tratamento*/
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){

        StandartError error = new StandartError(HttpStatus.NOT_FOUND.value(), e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


   @ExceptionHandler({DataIntegrityException.class})
    public ResponseEntity<StandartError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){

        StandartError error = new StandartError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e, HttpServletRequest request){

//        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),System.currentTimeMillis());
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação",System.currentTimeMillis());

        for (FieldError x: e.getBindingResult().getFieldErrors()){
            error.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
