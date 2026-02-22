package com.xd.xdclasslearnbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 适合人群实体类
 */
@Data
@TableName("target_audience")
public class TargetAudience {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private Long courseId;

    private Date createdTime;

    private Date updatedTime;
}