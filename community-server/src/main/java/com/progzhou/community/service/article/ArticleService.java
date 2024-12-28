package com.progzhou.community.service.article;

import com.progzhou.community.common.QueryPageInfo;
import com.progzhou.community.vo.request.ArticleQueryInfo;
import com.progzhou.community.vo.response.ArticleQueryResponse;

public interface ArticleService {

    /**
     * 根据文章id查询具体文章内容
     * @param request
     * @return
     */
    ArticleQueryResponse getArticleWithArticleId(ArticleQueryInfo request);

    /**
     *
     * @param request
     * @return
     */
    QueryPageInfo<ArticleQueryResponse> getCurrentUserArticle(ArticleQueryInfo request);
}
