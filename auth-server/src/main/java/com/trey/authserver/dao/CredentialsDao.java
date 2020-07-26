package com.trey.authserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trey.authserver.entity.Credentials;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface CredentialsDao extends BaseMapper<Credentials> {
    @Select("select * from credentials where name = '${username}'")
    Credentials selectByName(@Param("username") String username);
}