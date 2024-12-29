package com.progzhou.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.progzhou.community.entity.article.ArticleContentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleContentMapper extends BaseMapper<ArticleContentEntity> {

    /**
     * 根据文章id查询文章具体内容
     *
     * @param articleId
     * @return
     */
    ArticleContentEntity selectByArticleBasicInfoId(String articleId);

    /**
     * 根据发布id查询内容
     *
     * @param postId
     * @return
     */
    ArticleContentEntity selectByPostId(String postId);

    /**
     * 根据文章id查询文章内容
     *
     * @param articleId
     * @return
     */
    ArticleContentEntity selectByArticleId(String articleId);


    /**
     * 根据postId批量查询
     *
     * @param postIds
     * @return
     */
    List<ArticleContentEntity> selectByPostIds(List<String> postIds);

}
