package br.com.projetospring.exceptions;

public class ObjectNotFoundException extends  RuntimeException{
//    private static final long

    public ObjectNotFoundException(String msg){
        super(msg);
    }

    public ObjectNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }

}
