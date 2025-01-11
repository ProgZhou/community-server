package com.progzhou.community.service.article;

import com.progzhou.community.common.entity.QueryPageInfo;
import com.progzhou.community.vo.request.ArticleOptRequest;
import com.progzhou.community.vo.request.ArticlePublishRequest;
import com.progzhou.community.vo.request.ArticleQueryInfo;
import com.progzhou.community.vo.response.ArticleQueryResponse;

public interface ArticleService {

    /**
     * 根据文章id查询具体文章内容
     *
     * @param request
     * @return
     */
    ArticleQueryResponse getArticleWithArticleId(ArticleQueryInfo request);

    /**
     * 分页查询当前用户的所有文章
     *
     * @param request
     * @return
     */
    QueryPageInfo<ArticleQueryResponse> getCurrentUserArticle(ArticleQueryInfo request);


    /**
     * 首页查询所有用户的文章
     *
     * @param request
     * @return
     */
    QueryPageInfo<ArticleQueryResponse> getArticlePage(ArticleQueryInfo request);

    /**
     * 增加浏览量
     *
     * @param articleId
     * @return
     */
    int incrViewCount(String articleId);

    /**
     * 文章埋点请求：点赞、点踩/ 收藏、取消收藏
     *
     * @param request
     * @return
     */
    int optLikeCount(ArticleOptRequest request);

    /**
     * 文章发布请求
     *
     * @param request
     */
    void publishArticle(ArticlePublishRequest request);
}
