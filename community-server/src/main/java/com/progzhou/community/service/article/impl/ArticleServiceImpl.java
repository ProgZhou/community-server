package com.progzhou.community.service.article.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.progzhou.community.common.BusinessException;
import com.progzhou.community.common.Constant;
import com.progzhou.community.common.entity.QueryPageInfo;
import com.progzhou.community.common.util.KafkaProducerUtil;
import com.progzhou.community.common.util.RedisTemplateUtil;
import com.progzhou.community.entity.article.ArticleBasicInfoEntity;
import com.progzhou.community.entity.article.ArticleContentEntity;
import com.progzhou.community.entity.article.ArticlePostEntity;
import com.progzhou.community.mapper.ArticleBasicInfoMapper;
import com.progzhou.community.mapper.ArticleContentMapper;
import com.progzhou.community.mapper.ArticlePostMapper;
import com.progzhou.community.service.article.ArticleService;
import com.progzhou.community.vo.request.ArticleOptRequest;
import com.progzhou.community.vo.request.ArticlePublishRequest;
import com.progzhou.community.vo.request.ArticleQueryInfo;
import com.progzhou.community.vo.response.ArticleQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleBasicInfoMapper articleBasicInfoMapper;
    @Resource
    private ArticleContentMapper articleContentMapper;

    @Resource
    private ArticlePostMapper articlePostMapper;

    @Resource
    private RedisTemplateUtil redisTemplateUtil;

    @Resource
    private KafkaProducerUtil kafkaProducerUtil;

    @Value("${}")
    private String articleTopicName;

    @Override
    public ArticleQueryResponse getArticleWithArticleId(ArticleQueryInfo request) {
        Assert.notNull(request, "article query request must not be null");
        //TODO 使用统一日志工具进行日志打印处理
        log.info("查询文章具体信息请求: traceId: [{}], request: {}", request.getBase().getTraceId(), JSON.toJSONString(request));
        if (StringUtils.isBlank(request.getArticleId())) {
            throw new BusinessException("article id不能为空", request.getBase().getTraceId());
        }
        //查询缓存
        String redisKey = String.format("%s%s", Constant.RedisCache.ARTICLE_QUERY_CACHE_PREFIX, request.getArticleId());
        String redisValue = redisTemplateUtil.get(redisKey);
        ArticleBasicInfoEntity articleBasicInfoEntity;
        ArticleContentEntity articleContentEntity;
        if (StringUtils.isBlank(redisValue)) {
            //查询文章基本信息
            articleBasicInfoEntity = articleBasicInfoMapper.selectById(request.getArticleId());
            if (ObjectUtils.isEmpty(articleBasicInfoEntity)) {
                redisTemplateUtil.set(redisKey, "");
                return null;
            } else {
                redisTemplateUtil.set(redisKey, JSON.toJSONString(articleBasicInfoEntity));
                articleContentEntity = articleContentMapper.selectByArticleBasicInfoId(articleBasicInfoEntity.getId());
                if (ObjectUtils.isEmpty(articleContentEntity)) {
                    return null;
                } else {
                    return buildQueryResponse(articleBasicInfoEntity, articleContentEntity);
                }
            }
        } else {
            articleBasicInfoEntity = JSON.parseObject(redisValue, ArticleBasicInfoEntity.class);
            articleContentEntity = articleContentMapper.selectByArticleBasicInfoId(articleBasicInfoEntity.getId());
        }
        return buildQueryResponse(articleBasicInfoEntity, articleContentEntity);
    }

    @Override
    public QueryPageInfo<ArticleQueryResponse> getCurrentUserArticle(ArticleQueryInfo request) {
        Assert.notNull(request, "article query request must not be null");
        log.info("查询当前用户文章请求: traceId: [{}], request: {}", request.getBase().getTraceId(), JSON.toJSONString(request));
        //构造请求参数
        QueryWrapper<ArticlePostEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Constant.TableColumn.AUTHOR_ID, request.getBase().getUserId());
        queryWrapper.eq(Constant.TableColumn.DELETED, Constant.UN_DELETED);
        Page<ArticlePostEntity> pageRequest = Page.of((long) request.getPage(), (long) request.getSize());
        //默认按照发布时间降序排序
        queryWrapper.orderByDesc(Constant.TableColumn.PUBLISH_TIME);
        Page<ArticlePostEntity> articlePostEntityPage = articlePostMapper.selectPage(pageRequest, queryWrapper);
        if (ObjectUtils.isEmpty(articlePostEntityPage) || articlePostEntityPage.getTotal() == 0) {
            return QueryPageInfo.empty();
        }
        List<ArticlePostEntity> records = articlePostEntityPage.getRecords();
//        List<String> postIds = records.stream().map(ArticlePostEntity::getId).collect(Collectors.toList());
//        List<ArticleContentEntity> articleContentEntities = articleContentMapper.selectByPostIds(postIds);
        List<ArticleQueryResponse> articleQueryResponseList = records.stream().map(record -> {
            ArticleContentEntity articleContentEntity = articleContentMapper.selectByPostId(record.getId());
            return buildQueryResponse(record, articleContentEntity);
        }).collect(Collectors.toList());
        return QueryPageInfo.of(articleQueryResponseList, articlePostEntityPage.getCurrent(),
                articlePostEntityPage.getSize(), articlePostEntityPage.getTotal());

    }

    @Override
    public QueryPageInfo<ArticleQueryResponse> getArticlePage(ArticleQueryInfo request) {
        Assert.notNull(request, "article query request must not be null");
        log.info("查询所有用户文章请求: traceId: [{}], request: {}", request.getBase().getTraceId(), JSON.toJSONString(request));

        //构造请求参数
        QueryWrapper<ArticleBasicInfoEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getTitle())) {
            queryWrapper.like(Constant.TableColumn.TITLE, "%"+request.getTitle()+"%");
        }
        if (StringUtils.isNotBlank(request.getCategory())) {
            queryWrapper.eq(Constant.TableColumn.CATEGORY, request.getCategory());
        }
        queryWrapper.eq(Constant.TableColumn.DELETED, Constant.UN_DELETED);
        Page<ArticleBasicInfoEntity> pageRequest = Page.of((long) request.getPage(), (long) request.getSize());
        queryWrapper.orderByDesc(Constant.TableColumn.PUBLISH_TIME);
        Page<ArticleBasicInfoEntity> articleBasicInfoEntityPage = articleBasicInfoMapper.selectPage(pageRequest, queryWrapper);
        if (ObjectUtils.isEmpty(articleBasicInfoEntityPage) || articleBasicInfoEntityPage.getTotal() == 0) {
            return QueryPageInfo.empty();
        }

        List<ArticleBasicInfoEntity> records = articleBasicInfoEntityPage.getRecords();
        List<ArticleQueryResponse> articleQueryResponseList = records.stream().map(record -> {
            ArticleContentEntity articleContentEntity = articleContentMapper.selectByArticleId(record.getId());
            return buildQueryResponse(record, articleContentEntity);
        }).collect(Collectors.toList());

        return QueryPageInfo.of(articleQueryResponseList, articleBasicInfoEntityPage.getCurrent(),
                articleBasicInfoEntityPage.getSize(), articleBasicInfoEntityPage.getTotal());
    }

    //TODO 需要添加事务处理
    @Override
    public int incrViewCount(String articleId) {
        Assert.notNull(articleId, "incr view count request article id must not be null");
        String redisKey = String.format("%s%s", Constant.RedisCache.ARTICLE_VIEW_COUNT_PREFIX, articleId);
        return (int) redisTemplateUtil.incr(redisKey);
    }

    @Override
    public int optLikeCount(ArticleOptRequest request) {
        return 0;
    }

    @Override
    public void publishArticle(ArticlePublishRequest request) {
        Assert.notNull(request, "article publish request must not be null");
        //1. 根据用户id查询用户信息
        String userId = request.getBase().getUserId();

        //2. 将请求转换为实体类
        ArticlePostEntity postEntity = new ArticlePostEntity();

        ArticleContentEntity contentEntity = new ArticleContentEntity();

        //3. 将postEntity与contentEntity保存至数据库中
        articlePostMapper.insert(postEntity);
        contentEntity.setPostId(postEntity.getId());
        articleContentMapper.insert(contentEntity);

        //4. 将postId发送至kafka通知审核服务进行审核
        kafkaProducerUtil.sendMessage(articleTopicName, JSON.toJSONString(postEntity), request.getBase().getTraceId());
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
        response.setPostType(basicInfo.getPostType());
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

    private ArticleQueryResponse buildQueryResponse(ArticlePostEntity postEntity, ArticleContentEntity contentEntity) {
        ArticleQueryResponse response = new ArticleQueryResponse();
        response.setPostId(postEntity.getId());
        response.setTitle(postEntity.getTitle());
        response.setAuthorId(postEntity.getAuthorId());
        response.setAuthorName(postEntity.getAuthorName());
        response.setAuthorLevel(postEntity.getAuthorLevel());
        response.setAuthorAvatar(postEntity.getAuthorAvatar());
        response.setCategory(postEntity.getCategory());
        String basicInfoLabels = postEntity.getLabels();
        if (StringUtils.isNotBlank(basicInfoLabels)) {
            String[] splits = basicInfoLabels.split(",");
            List<String> labels = Arrays.asList(splits);
            response.setLabels(labels);
        }
        response.setPostType(postEntity.getPostType());
        String contentEntityImages = contentEntity.getImages();
        if (StringUtils.isNotBlank(contentEntityImages)) {
            String[] split = contentEntityImages.split(",");
            response.setImageUrls(Arrays.asList(split));
        }
        response.setContent(contentEntity.getContent());
        response.setPublishTime(postEntity.getPublishTime());
        response.setUpdateTime(postEntity.getUpdateTime());
        return response;
    }
}
