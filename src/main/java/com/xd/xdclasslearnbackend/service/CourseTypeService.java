package com.xd.xdclasslearnbackend.service;

import com.xd.xdclasslearnbackend.dto.CourseTypeDTO;
import com.xd.xdclasslearnbackend.entity.CourseType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseTypeService {
    List<CourseType> getList(CourseTypeDTO courseTypeDTO);
}
