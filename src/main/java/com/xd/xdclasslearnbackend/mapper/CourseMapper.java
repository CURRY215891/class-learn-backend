package com.xd.xdclasslearnbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xd.xdclasslearnbackend.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 康志阳
 * @date 2026/2/18 19:26
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    int count(@Param("course") Course course, @Param("minRating")BigDecimal minRating, @Param("minStudentCount")Long minStudentCount);

    List<Course> findListByPage(@Param("course") Course course, @Param("offset")int offset, @Param("pageSize")int pageSize);
}
