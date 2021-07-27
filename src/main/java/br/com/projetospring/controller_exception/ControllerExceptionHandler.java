package br.com.projetospring.controller_exception;

import br.com.projetospring.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/*Objeto de interceçao que caso houver uma exceçao ele vai capturar e fazer o tratamento*/
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){

        StandartError error = new StandartError(HttpStatus.NOT_FOUND.value(), e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
