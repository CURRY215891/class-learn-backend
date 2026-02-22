package com.xd.xdclasslearnbackend.service.impl;

import com.xd.xdclasslearnbackend.dto.CourseTypeDTO;
import com.xd.xdclasslearnbackend.entity.CourseType;
import com.xd.xdclasslearnbackend.mapper.CourseTypeMapper;
import com.xd.xdclasslearnbackend.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 康志阳
 * @date 2026/2/18 17:32
 */
@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Override
    public List<CourseType> getList(CourseTypeDTO query) {
        CourseType courseType = new CourseType();
        if(query!=null){
            courseType.setName(query.getName());
            courseType.setDescription(query.getDescription());
        }
        return courseTypeMapper.findList(courseType);
    }
}
