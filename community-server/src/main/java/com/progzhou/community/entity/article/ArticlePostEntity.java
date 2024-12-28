package com.progzhou.community.entity.article;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.progzhou.community.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("tbl_article_post")
public class ArticlePostEntity extends BaseEntity {

    private String title;

    @TableField("author_id")
    private String authorId;

    @TableField("author_name")
    private String authorName;

    @TableField("author_level")
    private Integer authorLevel;

    @TableField("author_avatar")
    private String authorAvatar;

    @TableField("post_type")
    private Integer postType;

    private String category;

    private String images;

    private String labels;

    private Integer status;

    @TableField("publish_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

}
