package com.progzhou.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.progzhou.community.entity.article.ArticleContentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleContentMapper extends BaseMapper<ArticleContentEntity> {

    /**
     * 根据文章id查询文章具体内容
     * @param articleId
     * @return
     */
    ArticleContentEntity selectByArticleBasicInfoId(String articleId);

}
