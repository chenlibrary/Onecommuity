package com.chenjiahui.community.advice;

import com.alibaba.fastjson.JSON;
import com.chenjiahui.community.dto.ResultDto;
import com.chenjiahui.community.exception.CustomizeErrorCode;
import com.chenjiahui.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable e, Model model, HttpServletResponse response, HttpServletRequest request) {
       String contentType =request.getContentType();
        System.out.println(contentType);
       if("application/json".equals(contentType)){
           //判段json数据
           ResultDto resultDto=null;
           if(e instanceof CustomizeException){
                resultDto =ResultDto.errorOf((CustomizeException)e);
           }else{
               resultDto=ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
           }
           try {
               response.setCharacterEncoding("utf-8");
               response.setStatus(200);
               response.setContentType("application/json");
               PrintWriter writer = response.getWriter();
               writer.write(JSON.toJSONString(resultDto));
               writer.close();
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       }else{
           //不是json数据
           if (e instanceof CustomizeException){
               model.addAttribute("message",e.getMessage());
           }else{
               model.addAttribute("message","服务器冒烟了");
           }
       }


        return new ModelAndView("error");
    }


}