package com.xd.xdclasslearnbackend.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 康志阳
 * @date 2026/2/18 17:21
 */
@Data
public class CourseType {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
