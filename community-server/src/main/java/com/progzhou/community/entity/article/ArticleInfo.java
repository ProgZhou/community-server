package com.progzhou.community.entity.article;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ArticleInfo {

    private String articleId;
    private String title;
    private String authorId;
    private String authorName;
    private Integer authorLevel;
    private String category;
    private String labels;
    private Integer postType;
    private String content;
    private String images;
    private Integer likeCount;
    private Integer commentCount;
    private Integer viewCount;
    private Integer collectionCount;
    private Date createTime;
    private Date publishTime;
    private Date updateTime;
    private String deleted;

}
