## 配置OAuth2资源服务器

步骤如下:
* 新建OAuth2ServerConfig,并且继承AuthorizationServerConfigurerAdapter类
* 覆盖configure(ClientDetailsServiceConfigurer clients)方法

### Token转换器
JwtAccessTokenConverter

加密方式：
* 对称加密
* 非对称加密

### keystore创建
```text
keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore keystore.jks -storepass mypass
```

OAuth2.0为我们提供四种授权方式:
* 授权码模式(authorization code)
* 简化模式(implicit)
* 密码模式(resource owner password credentials)
* 客户端模式(client credentials)

token访问端点`org.springframework.security.oauth2.provider.endpoint.TokenEndpoint`

TokenStore为接口，存在于包`org.springframework.security.oauth2.provider.token`下，有以下几种实现类:
* InMemoryTokenStore
* JdbcTokenStore
* JwtTokenStore
* RedisTokenStore

在具体场景中，我们可以根据需要选择合适的TokenStore.