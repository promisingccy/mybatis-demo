package com.ccy.mybatisdemo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName User
 * @Description //TODO
 * @Author ccy
 * @Date 2020/5/26 19:25
 * @Version 1.0
 **/
@Data
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;
}
