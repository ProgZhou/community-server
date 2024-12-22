package com.progzhou.community.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import com.progzhou.community.vo.request.ArticleQueryRequest;
import com.progzhou.community.vo.response.ArticleQueryResponse;

public interface ArticleService {

    /**
     * 根据文章id查询具体文章内容
     * @param request
     * @return
     */
    ArticleQueryResponse getArticleWithArticleId(ArticleQueryRequest request) throws Exception;



}
