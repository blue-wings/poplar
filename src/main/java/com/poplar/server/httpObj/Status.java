package com.poplar.server.httpObj;

/**
 * User: FR
 * Time: 5/18/15 5:17 PM
 */
public enum Status {
    //100
    CONTINUE(100, "CONTINUE"),
    SWITCHING_PROTOCOLS(101, "SWITCHING PROTOCOLS"),
    PROCESSING(102, "PROCESSING"),

    //200
    OK(200,"OK"),
    CREATED(201,"CREATED"),
    ACCEPTED(202,"ACCEPTED"),


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
