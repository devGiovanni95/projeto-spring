package br.com.projetospring.controller_exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends  StandartError{

    private List<FieldMessage> listErrors = new ArrayList<>();

    public ValidationError(Long timeStamp, Integer status, String error, String message, String path) {
        super(timeStamp, status, error, message, path);
    }
    //    public ValidationError(Integer status, String msg, Long timeStamp) {
//        super(status, msg, timeStamp);
//    }
//
//    public List<FieldMessage> getList() {
//        return list;
//    }
//
    public List<FieldMessage> getErrors() {
        return listErrors;
    }

    public void addError(String fieldName, String message){
        listErrors.add(new FieldMessage(fieldName, message));
    }
}
