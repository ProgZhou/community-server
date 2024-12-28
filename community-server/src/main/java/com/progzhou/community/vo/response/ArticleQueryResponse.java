package com.progzhou.community.vo.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleQueryResponse implements Serializable {

    //如果是已发布的文章，则填充这个字段
    private String articleId;

    //如果是还在审核的文章，则填充这个字段
    private String postId;

    private String title;

    private String authorId;

    private String authorName;

    private Integer authorLevel;

    private String authorAvatar;

    private String category;

    private List<String> labels;

    private Integer postType;

    private List<String> imageUrls;

    private String content;

    private Integer likeCount;

    private Integer commentCount;

    private Integer viewCount;

    private Integer collectionCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
