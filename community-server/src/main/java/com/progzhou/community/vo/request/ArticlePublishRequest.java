package com.progzhou.community.vo.request;

import com.progzhou.community.common.request.BaseInfo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ArticlePublishRequest implements Serializable {

    @Valid
    @NotNull(message = "base参数不能为空")
    private BaseInfo base;

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章分类不能为空")
    private String category;

    private List<String> labels;

    private Integer postType;

    @NotBlank(message = "文章内容不能为空")
    private String contentText;

    private List<String> imageUrls;

}
