package com.chenjiahui.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionPage {
    private List<QuestionUser> questionUser;
    private boolean showPrevious;//上页
    private boolean showNext;//下页
    private boolean showFirstPage;//首页
    private boolean showEndPage;//最后页
    private Integer page;//当前页
    private Integer previousPage;
    private Integer firstPage;
    private Integer nextPage;
    private Integer endPage;
    private List<Integer> pages=new ArrayList<>();//页面存的页码

    public void setAll(Integer pageAll, Integer pageNum, Integer size) {
        this.page=pageNum;
        //是否有上页
        if(pageNum-1>0){
            previousPage=pageNum-1;
            showPrevious=true;
        }else{
            showPrevious=false;
        }
        //是否有下页
        if(pageNum+1<=pageAll){
            nextPage=pageNum+1;
            showNext=true;
        }else{
            showNext=false;
        }
        //首页
        if(pageNum!=1){
            firstPage=1;
            showFirstPage=true;
        }else{
            showFirstPage=false;
        }
        //尾页
        if(pageNum!=pageAll&&pageAll!=0){
            endPage=pageAll;
            showEndPage=true;
        }else{
            showEndPage=false;
        }
        //加载的页数
        if(pageAll!=0){
            pages.add(pageNum);
        }
        for(int i=1;i<=3;i++){
                if(pageNum-i>0){
                    pages.add(0,pageNum-i);
                }
                if(pageNum+i<=pageAll){
                    pages.add(pages.size(),pageNum+i);
                }
        }

    }
}
