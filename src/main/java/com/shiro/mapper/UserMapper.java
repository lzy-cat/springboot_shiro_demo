package com.shiro.mapper;

import com.shiro.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository  //解决注入UserMapper接口时UserService的userMapper 报红（虽然报红不影响运行）
@Mapper
public interface UserMapper {

   public User queryUserByName(String name);
}
