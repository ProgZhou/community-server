package com.progzhou.community.entity.article;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.progzhou.community.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文章基本信息表
 */
@Data
@NoArgsConstructor
@TableName("tbl_article_info")
public class ArticleBasicInfoEntity extends BaseEntity {

    private String title;

    @TableField("author_id")
    private String authorId;

    @TableField("author_name")
    private String authorName;

    @TableField("author_level")
    private Integer authorLevel;

    @TableField("author_avatar")
    private String authorAvatar;

    private String category;

    private String images;

    private String labels;

    @TableField("post_type")
    private Integer postType;

    @TableField("like_count")
    private Integer likeCount;

    @TableField("comment_count")
    private Integer commentCount;

    @TableField("view_count")
    private Integer viewCount;

    @TableField("collection_count")
    private Integer collectionCount;

    @TableField("publish_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

}
