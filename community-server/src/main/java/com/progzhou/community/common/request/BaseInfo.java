package com.progzhou.community.common.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 公共请求参数
 */
@Data
@NoArgsConstructor
public class BaseInfo implements Serializable {

    @NotBlank(message = "userId不能为空")
    private String userId;

    @NotBlank(message = "鉴权参数不能为空")
    private String authorization;

    @NotBlank(message = "traceId不能为空")
    private String traceId;

}
