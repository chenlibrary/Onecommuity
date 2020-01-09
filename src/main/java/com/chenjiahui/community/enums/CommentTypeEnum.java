package com.chenjiahui.community.enums;

public enum  CommentTypeEnum {
    Question(1),COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }
    CommentTypeEnum(Integer type){
        this.type=type;
    }

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum cte:CommentTypeEnum.values()){
            if(cte.type==type){
                    return true;
            }
        }
        return false;
    }
}
