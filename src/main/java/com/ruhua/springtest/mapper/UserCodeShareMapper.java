package com.ruhua.springtest.mapper;

import com.ruhua.springtest.domain.TeamCodeRelation;
import com.ruhua.springtest.domain.UserCodeShareInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserCodeShareMapper {
    List<UserCodeShareInfo> getCode();


    void addCode(@Param("codeId") Integer codeId,@Param("userId")  Integer userId, @Param("createAt") Date createAt,
                 @Param("updateAt") Date updateAt,@Param("isShare") Integer isShare) ;

    void removeCode(@Param("codeId") Integer codeId) ;
}
