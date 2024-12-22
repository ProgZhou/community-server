package com.progzhou.community.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页参数
 */
@Data
@NoArgsConstructor
public class PageInfo {

    private Integer page;

    private Integer size;

}
