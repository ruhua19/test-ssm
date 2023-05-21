package com.ruhua.springtest.param;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName test_result
 */

@Builder
@Data
public class TestResultParam implements Serializable {
    /**
     * 
     */

    private Integer id;

    /**
     * 
     */
    private Integer codeId;

    /**
     * 
     */
    private Integer testUser;

    /**
     * 
     */
    private Date testTime;

    /**
     * 
     */
    private String result;

    /**
     * 
     */
    private Date createAt;

    /**
     * 
     */
    private Date updateAt;

    /**
     *
     */
    private String code;


    private static final long serialVersionUID = 1L;


}