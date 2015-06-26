package com.poplar.server.context;

/**
 * User: FR
 * Time: 5/18/15 5:17 PM
 */
public enum Status {
    //100
    CONTINUE(100, "CONTINUE"),
    switching_protocols(101, "SWITCHING PROTOCOLS"),

    //200
    OK(200,"OK"),

    //400
    NOT_FOUND(404,"URL NOT FOUND"),

    //500
    SERVER_ERROR(500,"SERVER ERROR");

    private int code;
    private String des;
    Status(int status, String des) {
        this.code =status;
        this.des=des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    public static Status getStatus(int code){
        for(Status status : Status.values()){
            if(status.getCode() == code){
                return status;
            }
        }
        return null;
    }
}
