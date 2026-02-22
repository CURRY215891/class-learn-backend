package com.xd.xdclasslearnbackend.vo;

import lombok.Data;

/**
 * 资源视图对象
 */
@Data
public class ResourceVO {
    private Long id;
    private String title; // 对应 resourceName
    private String url;   // 对应 resourceUrl
    private Long typeId;
}