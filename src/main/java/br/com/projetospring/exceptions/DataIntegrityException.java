package br.com.projetospring.exceptions;

public class DataIntegrityException extends  RuntimeException{
//    private static final long

    public DataIntegrityException(String msg){
        super(msg);
    }

    public DataIntegrityException(String msg, Throwable cause){
        super(msg, cause);
    }

}
