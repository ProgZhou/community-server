package com.progzhou.community.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPageInfo<T> implements Serializable {

    @SuppressWarnings("rawtypes")
    private static final QueryPageInfo EMPTY = new QueryPageInfo<>(1, 20, 0, Collections.emptyList());



    private long pageNum;

    private long pageSize;

    private long total;

    private List<T> list;


    public static <T> QueryPageInfo<T> of(List<T> dataList, long currentPage, long pageSize, long totalCount) {
        return new QueryPageInfo<>(currentPage, pageSize, totalCount, dataList);
    }

    public static <T> QueryPageInfo<T> copy(List<T> dataList, QueryPageInfo<?> pageInfo) {
        return QueryPageInfo.of(dataList, pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal());
    }

    @SuppressWarnings("unchecked")
    public static <T> QueryPageInfo<T> empty() {
        return (QueryPageInfo<T>) EMPTY;
    }
}
