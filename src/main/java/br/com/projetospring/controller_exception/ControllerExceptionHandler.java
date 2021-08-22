package br.com.projetospring.controller_exception;

import br.com.projetospring.exceptions.AuthorizationException;
import br.com.projetospring.exceptions.DataIntegrityException;
import br.com.projetospring.exceptions.FileException;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/*Objeto de interceçao que caso houver uma exceçao ele vai capturar e fazer o tratamento*/
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        StandartError error = new StandartError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler({DataIntegrityException.class})
    public ResponseEntity<StandartError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

        StandartError error = new StandartError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

//        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),System.currentTimeMillis());
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());

        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            error.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<StandartError> authorization(AuthorizationException e, HttpServletRequest request) {

        StandartError error = new StandartError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler({FileException.class})
    public ResponseEntity<StandartError> file(FileException e, HttpServletRequest request) {

        StandartError error = new StandartError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({AmazonServiceException.class})
    public ResponseEntity<StandartError> amazonService(AmazonServiceException e, HttpServletRequest request) {
        HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
        StandartError error = new StandartError(code.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler({AmazonClientException.class})
    public ResponseEntity<StandartError> amazonClient(AmazonClientException e, HttpServletRequest request) {

        StandartError error = new StandartError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({AmazonS3Exception.class})
    public ResponseEntity<StandartError> amazonClient(AmazonS3Exception e, HttpServletRequest request) {

        StandartError error = new StandartError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
