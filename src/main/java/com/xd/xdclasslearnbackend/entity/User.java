package com.xd.xdclasslearnbackend.entity;

import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;

/**
 * @author 康志阳
 * @date 2026/2/18 19:31
 */
@Data
public class User {
    private long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String avatar;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
