package com.xd.xdclasslearnbackend.service;

import com.xd.xdclasslearnbackend.commen.PageResult;
import com.xd.xdclasslearnbackend.dto.CourseDTO;
import com.xd.xdclasslearnbackend.entity.Course;
import com.xd.xdclasslearnbackend.vo.ChapterVO;
import com.xd.xdclasslearnbackend.vo.CourseDetailVO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CourseService {
    PageResult<Course> getPage(CourseDTO query);
    CourseDetailVO getCourseDetail(Long id);
    List<ChapterVO> getChapters(Long courseId);
}
