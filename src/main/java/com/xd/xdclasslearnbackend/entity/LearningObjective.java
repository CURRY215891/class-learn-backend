package com.xd.xdclasslearnbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 学习目标实体类
 */
@Data
@TableName("learning_objective")
public class LearningObjective {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private Long courseId;

    private Date createdTime;

    private Date updatedTime;
}
