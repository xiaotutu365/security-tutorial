DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`
(
    `id`        bigint(11) NOT NULL COMMENT '权限id',
    `authority` varchar(255) DEFAULT NULL COMMENT '权限',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `credentials`;
CREATE TABLE `credentials`
(
    `id`       bigint(11)   NOT NULL COMMENT '凭证id',
    `enabled`  tinyint(1)   NOT NULL COMMENT '是否可用',
    `name`     varchar(255) NOT NULL COMMENT '用户名',
    `password` varchar(255) NOT NULL COMMENT '密码',
    `version`  int(11) DEFAULT NULL COMMENT '版本号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `credentials_authorities`;
CREATE TABLE `credentials_authorities`
(
    `credentials_id` bigint(20) NOT NULL COMMENT '凭证id',
    `authorities_id` bigint(20) NOT NULL COMMENT '权限id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;