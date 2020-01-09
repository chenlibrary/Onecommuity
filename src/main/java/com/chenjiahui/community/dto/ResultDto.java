package com.chenjiahui.community.dto;

import com.chenjiahui.community.exception.CustomizeErrorCode;
import com.chenjiahui.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

@Data
public class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;


    public static ResultDto  errorOf(Integer code,String message){
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeErrorCode customizeErrorCode) {
        return errorOf(customizeErrorCode.getCode(),customizeErrorCode.getMessage());
    }

    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }
    public static ResultDto okOf(){
        return errorOf(200,"ok");
    }

    public static <T> ResultDto okOf(T t){
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return resultDto;
    }
}
