package com.chenjiahui.community.exception;

public class CustomizeException extends RuntimeException{
    String message;
    Integer code;
    public  CustomizeException(ICustomizeErrorCode customizeErrorCode){
            this.message=customizeErrorCode.getMessage();
            this.code=customizeErrorCode.getCode();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode(){
        return code;
    }
}
