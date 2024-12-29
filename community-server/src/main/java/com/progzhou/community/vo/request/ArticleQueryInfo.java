package com.progzhou.community.vo.request;

import com.progzhou.community.common.request.BaseInfo;
import com.progzhou.community.common.entity.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 文章查询请求
 */
@Data
@NoArgsConstructor
public class ArticleQueryInfo extends PageInfo implements Serializable {

    @NotNull(message = "base不能为空")
    @Valid
    private BaseInfo base;

    private String articleId;

    private String authorId;

    private String category;

    private String title;



}
