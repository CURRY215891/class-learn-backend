package com.xd.xdclasslearnbackend.mapper;

import com.xd.xdclasslearnbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
}
