package com.xd.xdclasslearnbackend.vo;

import lombok.Data;
import java.util.List;

/**
 * 章节视图对象
 */
@Data
public class ChapterVO {
    private Long id;
    private String title;
    private String description;
    private List<ResourceVO> resources; // 包含该章节下的资源列表
}