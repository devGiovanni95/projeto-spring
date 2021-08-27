package br.com.projetospring.controller_exception;

public class StandartError {

    private Long timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandartError(Long timeStamp, Integer status, String error, String message, String path) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //    private Integer status;
//    private String msg;
//    private Long timeStamp;
//
//    public StandartError(Integer status, String msg, Long timeStamp) {
//        this.status = status;
//        this.msg = msg;
//        this.timeStamp = timeStamp;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public Long getTimeStamp() {
//        return timeStamp;
//    }
//
//    public void setTimeStamp(Long timeStamp) {
//        this.timeStamp = timeStamp;
//    }
}
