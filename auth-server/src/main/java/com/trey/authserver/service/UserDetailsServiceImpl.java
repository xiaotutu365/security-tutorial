package com.trey.authserver.service;

import com.trey.authserver.dao.CredentialsDao;
import com.trey.authserver.entity.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CredentialsDao credentialsDao;

    /**
     * 从数据库获取用户的信息并验证
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials credentials = credentialsDao.selectByName(username);
        if (credentials == null) {
            throw new UsernameNotFoundException("User " + username + " can not be found.");
        }
        return User.withUsername(credentials.getName())
                .password(credentials.getPassword())
                .authorities(AuthorityUtils.createAuthorityList("ROLE_PROJECT_ADMIN"))
                .build();
    }
}