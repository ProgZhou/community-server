package com.progzhou.community.common;

/**
 * 常量
 */
public class Constant {

    public static final String UN_DELETED = "0";

    public static class TableColumn {
        //列名常量
        public static final String AUTHOR_ID = "author_id";
        public static final String DELETED = "deleted";
        public static final String PUBLISH_TIME = "publish_time";
        public static final String TITLE = "title";
        public static final String CATEGORY = "category";
    }

    public static class RedisCache {
        public static final String ARTICLE_VIEW_COUNT_PREFIX = "article:view:count:";
        public static final String ARTICLE_QUERY_CACHE_PREFIX = "article:query:";
    }

}
