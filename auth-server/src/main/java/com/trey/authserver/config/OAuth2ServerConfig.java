package com.trey.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

/**
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 引入spring-boot-starter-data-jpa依赖,且使用mysql数据库
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 由WebSecurityConfig中的authenticationManagerBean引入
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 实现接口UserDetailsService
     */
    @Qualifier(value = "userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 对应 oauth_client_details表
     *
     * @return
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 对应 oauth_access_token表
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        // 1.使用Jdbc来存储Token
        return new JdbcTokenStore(dataSource);
        // 2.以Jwt的方式来存储Token
        // return new JwtTokenStore(jwtTokenEnhancer());
    }

    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123456");
        return converter;
    }

    /**
     * 客户端详情信息,将客户端信息读取到认证服务器,可以从内存中读取,也可从数据库中读取
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * 该方法用来配置
     * 1)授权(authorization)
     * 2)令牌(token)的访问端点和令牌服务(token services)
     * <p>
     * 根据用户输入的用户名和密码从数据库中进行认证,认证通过则返回token
     * 用户输入username和password,在UserDetailsService具体的实现中进行比对
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore());
                // .tokenEnhancer(jwtTokenEnhancer());
                // .tokenEnhancer(jwtAccessTokenConverter())
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;
    }

    /**
     * 该方法用来配置令牌端点(token endpoint)的安全约束
     * tokenKeyAccess --> /oauth/token_key(TokenKeyEndpoint)
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()")  // 开启/oauth/token_key验证端口无权限访问(permitAll())
                .checkTokenAccess("isAuthenticated()");     // 开启/oauth/check_token验证端口认证权限访问
    }
}