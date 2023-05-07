package com.ruhua.springtest.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserCodeShareInfo {
    /**
     *
     */

    private Integer id;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private Integer codeId;


    private Integer isShare;

    /**
     *
     */
    private Date createAt;

    /**
     *
     */
    private Date updateAt;
}
