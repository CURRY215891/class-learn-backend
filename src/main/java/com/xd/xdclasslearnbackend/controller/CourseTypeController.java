package com.xd.xdclasslearnbackend.controller;

import com.xd.xdclasslearnbackend.commen.Result;
import com.xd.xdclasslearnbackend.entity.CourseType;
import com.xd.xdclasslearnbackend.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 康志阳
 * @date 2026/2/18 17:19
 */
@RestController
@RequestMapping("/api/course-types")
public class CourseTypeController {
    @Autowired
    private CourseTypeService courseTypeService;
    @GetMapping
    public Result<List<CourseType>> getAllCourseType() {
        List<CourseType> courseTypes = courseTypeService.getList(null);
        return Result.success("success", courseTypes);
    }
}
