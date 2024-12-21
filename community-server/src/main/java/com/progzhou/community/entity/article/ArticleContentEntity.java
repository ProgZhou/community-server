package com.progzhou.community.entity.article;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.progzhou.community.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章具体内容实体类
 */
@Data
@NoArgsConstructor
@TableName("tbl_article_content")
public class ArticleContentEntity extends BaseEntity {

    @TableField("article_id")
    private String articleId;

    @TableField("post_id")
    private String postId;

    private String content;

    private String images;

}
