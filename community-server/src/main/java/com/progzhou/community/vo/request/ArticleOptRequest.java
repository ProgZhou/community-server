package com.progzhou.community.vo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleOptRequest implements Serializable {

    /**
     * 操作  0: 点赞/点踩  1: 收藏/取消收藏
     */
    private Integer command;

    /**
     * 0: 点赞/收藏
     * 1: 取消点赞/取消收藏
     */
    private Integer opt;

    /**
     * 目标文章id
     */
    private String articleId;

    /**
     * 用户id
     */
    private String userId;

}
