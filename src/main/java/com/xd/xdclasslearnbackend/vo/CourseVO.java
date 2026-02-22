package com.xd.xdclasslearnbackend.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//前端页面显示模型
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO {
    /**
     * 课程ID
     */
    private Long id;

    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 教师姓名
     */
    private String author;

    /**
     * 课程类型
     */
    private String type;

    /**
     * 评分
     */
    private Double rating;

    /**
     * 学生数量
     */
    private Integer students;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 封面图片URL
     */
    private String cover;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
