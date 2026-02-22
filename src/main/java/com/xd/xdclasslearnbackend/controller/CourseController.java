package com.xd.xdclasslearnbackend.controller;

import com.xd.xdclasslearnbackend.commen.PageResult;
import com.xd.xdclasslearnbackend.commen.Result;
import com.xd.xdclasslearnbackend.dto.CourseDTO;
import com.xd.xdclasslearnbackend.entity.Course;
import com.xd.xdclasslearnbackend.service.CourseService;
import com.xd.xdclasslearnbackend.vo.CourseListVO;
import com.xd.xdclasslearnbackend.vo.CourseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 康志阳
 * @date 2026/2/18 18:50
 */
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @GetMapping
    public Result<CourseListVO> getCourseList(@RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) Long typeId,
                                              @RequestParam(required = false) Long type_id,
                                              @RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "9") Integer pageSize) {
        // 兼容处理：如果 typeId 为空但 type_id 不为空，则使用 type_id
        if (typeId == null && type_id != null) {
            typeId = type_id;
        }
        
        System.out.println("收到查询请求: keyword=" + keyword + ", typeId=" + typeId + ", page=" + page);

        CourseDTO query = new CourseDTO();
        query.setPageNum(page);
        query.setPageSize(pageSize);
        query.setTitle(keyword);
        query.setTypeId(typeId);
        //获取分页结果
      PageResult<Course> pageResult = courseService.getPage(query);
       //将分页结果转换为CourseListVO
        List<CourseVO> courseVOList = convertToCourseVoList(pageResult.getData());
        CourseListVO courseListVO = new CourseListVO();
        courseListVO.setList(courseVOList);
        courseListVO.setTotal(pageResult.getTotal());
        courseListVO.setPage(pageResult.getPageNum());
        courseListVO.setPageSize(pageResult.getPageSize());
        return Result.success("success", courseListVO);
    }
    //将分页结果转换为CourseListVO
    private List<CourseVO> convertToCourseVoList(List<Course> courseList){
        List<CourseVO> courseVOList = new ArrayList<>();
        for (Course course : courseList) {
            CourseVO courseVO = new CourseVO();
            BeanUtils.copyProperties(course, courseVO);
            courseVO.setAuthor(course.getTeacherName());
            //设置额外字段
            if(course.getType()!=null){
                courseVO.setType(course.getType().getName());
            }
            if(course.getRating()!=null){
                courseVO.setRating(course.getRating().doubleValue());
            }
            if(course.getStudentCount()!=null){
                courseVO.setStudents(course.getStudentCount().intValue());
            }
            courseVO.setCover(course.getCoverImage());
            courseVO.setCreateTime(course.getCreatedTime());
            courseVOList.add(courseVO);
        }
        return courseVOList;
    }
}
