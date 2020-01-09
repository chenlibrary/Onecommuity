package com.chenjiahui.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NO_FOUND(2001,"您找的问题不在了，换个问题看看"),
    TARGT_PARAM_NOT_FOUND(2002,"您访问的问题消失了"),
    NO_LOGIN(2003,"请先登陆，再评论"),
    SYS_ERROR(2004,"服务器猫眼了，请稍后再来"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不在了"),
    ISNULL_CONTENT(2007,"回复的评论不在了"),
    NO_COMMENT(2008,"内容不能为空");

    private String message;
    private Integer code;
    CustomizeErrorCode(Integer code, String message) {
        this.code=code;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
