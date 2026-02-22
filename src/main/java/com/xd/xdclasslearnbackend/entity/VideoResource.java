package com.xd.xdclasslearnbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 康志阳
 * @date 2026/2/21 20:31
 */
@Data
@TableName("course_resource")
public class VideoResource {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String resourceName;

    private String resourceUrl;

    private Long courseId;

    private Long chapterId;

    private Integer sortOrder;

    private Long typeId;

    private Date createdTime;

    private Date updatedTime;
}
