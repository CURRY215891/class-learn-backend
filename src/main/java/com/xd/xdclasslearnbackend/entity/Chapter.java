package com.xd.xdclasslearnbackend.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 康志阳
 * @date 2026/2/21 20:24
 */
@Data
@TableName("course_chapter")
public class Chapter {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private Long courseId;

    private Integer sortOrder;

    private Date createdTime;

    private Date updatedTime;
}
