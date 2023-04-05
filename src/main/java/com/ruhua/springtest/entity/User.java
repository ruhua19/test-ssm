package com.ruhua.springtest.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    Long id;
    String name;
    String password;
    Date createTime;
}
