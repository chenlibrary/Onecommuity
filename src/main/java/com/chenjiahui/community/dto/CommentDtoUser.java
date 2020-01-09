package com.chenjiahui.community.dto;

import com.chenjiahui.community.Model.User;
import lombok.Data;

@Data
public class CommentDtoUser {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private String content;
    private Long likeCount;
    private User user;
}
