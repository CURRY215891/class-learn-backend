package com.xd.xdclasslearnbackend.mapper;

import com.xd.xdclasslearnbackend.entity.CourseType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 康志阳
 * @date 2026/2/18 17:36
 */
@Mapper
public interface CourseTypeMapper {
    List<CourseType> findList(CourseType courseType);
}
