# 项目使用帮助


## 建立数据库
在MySQL中登录账号：root，密码：12345678。

登录数据库：
```sql
mysql -u root -p
```
用以下命令建立数据库：
```sql
CREATE DATABASE thutickets;
```
进入数据库：
``` sql
USE thutickets;
```
并添加数据表：
```sql
CREATE TABLE `user`  (
  `openid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'openid',
  `session_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'session_key',
  `status_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'status_key',
  `studentid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT 'studentid',
  `nick_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(11) NULL DEFAULT NULL COMMENT '性别',
  `language` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言',
  `province` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `country` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家',
  `avatar_url` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_visit_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  PRIMARY KEY (`openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = Dynamic;
```

``` sql
CREATE TABLE `event`  (
`eventid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'eventid',
`title` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
`date` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日期',
`time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间',
`text` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
`img_path` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
`create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`eventid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '活动信息' ROW_FORMAT = Dynamic;
```

``` sql
CREATE TABLE `ticket`  (
`ticketid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'eventid',
`eventid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT 'eventid',
`studentid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT 'studentid',
`validation` tinyint(11) NULL DEFAULT NULL COMMENT '有效校验',
`create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`ticketid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '门票信息' ROW_FORMAT = Dynamic;
```

如果需要彻底删除表（以user为例）：
```sql
drop table user;
```

## IDEA配置
在IDEA中用打开`ThuTickets/thutickets`文件夹，右击项目名称选择`maven->Reimport`可以自动导入依赖的包。

### 依赖包可以在pom.xml中查看
依赖包主要有以下（通过以上操作已经安装好）：
Mybatis-Plus（用于将实体类映射到数据库中的数据项）、lombok（用于实体类中简化代码）、MySQL驱动、SpringBoot依赖包等。

### 访问静态资源
请将cn.edu.tsinghua.thutickets.configuration.WebAppConfig中addResourceHandler方法下addResourceLocations改为本机的绝对路径（记得前面必须有"file:"）。

## 运行
在IDEA中找到`src/main/java/cn.edu.tsinghua.thutickets/ThuticketsApplication`，右击该文件点击`run 'ThuticketsApplication'`启动服务器（端口8080，可在`src/main/resources/application.properties`中修改）。
