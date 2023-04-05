package com.ruhua.springtest.param;

import lombok.Data;

import java.util.Date;

@Data
public class UserParam {
    Long id;
    String name;
    String password;
    Date creteTime;
}
