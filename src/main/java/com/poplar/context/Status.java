package com.poplar.context;

/**
 * User: FR
 * Time: 5/18/15 5:17 PM
 */
public enum Status {
    OK(200,"OK"),
    SERVER_ERROR(500,"SERVER ERROR"),
    NOT_FOUND(404,"URL NOT FOUND");

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
