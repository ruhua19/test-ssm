package com.ruhua.springtest.mapper;

import com.ruhua.springtest.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    void addUser();

    @Select("select * from user where id = #{id}")
    User getUserFromId(Long id);

    void deleteUser(Long id);

    void updateUser(User user);

    User getUserByName(String name);

}
