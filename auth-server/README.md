## 配置OAuth2资源服务器

步骤如下:
* 新建OAuth2ServerConfig,并且继承AuthorizationServerConfigurerAdapter类
* 覆盖configure(ClientDetailsServiceConfigurer clients)方法

### Token转换器
JwtAccessTokenConverter

加密方式：
* 对称加密
* 非对称加密