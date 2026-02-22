package com.xd.xdclasslearnbackend.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 康志阳
 * @date 2026/2/18 18:51
 */
@Data
public class CourseListVO {
    private List<CourseVO> list;
    private long total;
    private int page;
    private int pageSize;
}
