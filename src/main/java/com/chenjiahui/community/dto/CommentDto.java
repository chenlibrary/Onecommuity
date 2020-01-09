package com.chenjiahui.community.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer parentId;
    private Integer type;
    private String content;
}
