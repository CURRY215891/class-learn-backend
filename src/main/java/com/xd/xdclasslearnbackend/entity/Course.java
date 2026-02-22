package com.xd.xdclasslearnbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 * 映射数据库中的course表，表示课程信息
 * @author 老帆
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    /**
     * 课程ID，主键，自增长
     */
    private Long id;

    /**
     * 课程标题，不能为空
     */
    private String title;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程详细内容
     */
    private String detail;

    /**
     * 课程封面图片URL
     */
    private String coverImage;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 课程价格，精度10位，小数点后2位
     */
    private BigDecimal price;

    /**
     * 课程评分，精度2位，小数点后1位
     */
    private BigDecimal rating;

    /**
     * 学生人数
     */
    private Long studentCount;


    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


    private CourseType type;

    private User creator;

}