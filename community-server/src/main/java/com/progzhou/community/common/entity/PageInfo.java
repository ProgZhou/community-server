package com.progzhou.community.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
public class PageInfo {

    @Value("${page-request.page}")
    private Integer page;

    @Value("${page-request.size}")
    private Integer size;

}
