package com.progzhou.community.service.article.impl;

import com.alibaba.fastjson2.JSON;
import com.progzhou.community.entity.article.ArticleBasicInfoEntity;
import com.progzhou.community.entity.article.ArticleContentEntity;
import com.progzhou.community.mapper.ArticleBasicInfoMapper;
import com.progzhou.community.mapper.ArticleContentMapper;
import com.progzhou.community.service.article.ArticleService;
import com.progzhou.community.vo.request.ArticleQueryRequest;
import com.progzhou.community.vo.response.ArticleQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleBasicInfoMapper articleBasicInfoMapper;

    @Resource
    private ArticleContentMapper articleContentMapper;

    @Override
    public ArticleQueryResponse getArticleWithArticleId(ArticleQueryRequest request) throws Exception {
        Assert.notNull(request, "article query request must not be null");
        //TODO 使用统一日志工具进行日志打印处理
        log.info("查询文章具体信息请求: traceId: {}, request: {}", request.getBase().getTraceId(), JSON.toJSONString(request));
        if (StringUtils.isBlank(request.getArticleId())) {
            //TODO 自定义异常以及全局异常处理
            throw new Exception("article id不能为空");
        }
        //查询文章基本信息
        ArticleBasicInfoEntity articleBasicInfoEntity = articleBasicInfoMapper.selectById(request.getArticleId());
        if (ObjectUtils.isEmpty(articleBasicInfoEntity)) {
            return null;
        }
        ArticleContentEntity articleContentEntity = articleContentMapper.selectByArticleBasicInfoId(articleBasicInfoEntity.getId());
        if (ObjectUtils.isEmpty(articleContentEntity)) {
            return null;
        }

        return buildQueryResponse(articleBasicInfoEntity, articleContentEntity);
    }

    private ArticleQueryResponse buildQueryResponse(ArticleBasicInfoEntity basicInfo, ArticleContentEntity contentEntity) {
        ArticleQueryResponse response = new ArticleQueryResponse();
        response.setArticleId(basicInfo.getId());
        response.setTitle(basicInfo.getTitle());
        response.setAuthorId(basicInfo.getAuthorId());
        response.setAuthorName(basicInfo.getAuthorName());
        response.setAuthorLevel(basicInfo.getAuthorLevel());
        response.setAuthorAvatar(basicInfo.getAuthorAvatar());
        response.setCategory(basicInfo.getCategory());
        String basicInfoLabels = basicInfo.getLabels();
        if (StringUtils.isNotBlank(basicInfoLabels)) {
            String[] splits = basicInfoLabels.split(",");
            List<String> labels = Arrays.asList(splits);
            response.setLabels(labels);
        }
        response.setPostType(response.getPostType());
        String contentEntityImages = contentEntity.getImages();
        if (StringUtils.isNotBlank(contentEntityImages)) {
            String[] split = contentEntityImages.split(",");
            response.setImageUrls(Arrays.asList(split));
        }
        response.setContent(contentEntity.getContent());
        response.setLikeCount(basicInfo.getLikeCount());
        response.setCommentCount(basicInfo.getCommentCount());
        response.setViewCount(basicInfo.getViewCount());
        response.setCollectionCount(basicInfo.getCollectionCount());
        response.setPublishTime(basicInfo.getPublishTime());
        response.setUpdateTime(basicInfo.getUpdateTime());
        return response;
    }
}
