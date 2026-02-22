package com.xd.xdclasslearnbackend.service;

import com.xd.xdclasslearnbackend.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User login(String username,String password);

    User getUserByUsername(String username);
}
