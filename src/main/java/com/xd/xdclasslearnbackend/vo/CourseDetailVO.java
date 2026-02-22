package com.xd.xdclasslearnbackend.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程详情VO
 * 继承 CourseVO 以复用基础信息，并扩展详情页专用字段
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseDetailVO extends CourseVO {
    /**
     * 课程详细内容（富文本/HTML）
     */
    private String detail;

    /**
     * 学习目标列表
     */
    private List<String> learningObjectives = new ArrayList<>();

    /**
     * 适合人群列表
     */
    private List<String> targetAudiences = new ArrayList<>();


}