用户中心数据库表设计

user表

```sql
create table tb_user(
     id bigint primary key auto_increment comment '主键id',
     user_account varchar(256)  comment '用户账号',
     username varchar(256) not null comment '用户名',
     avatar_url varchar(1024) comment '用户头像',
     gender tinyint comment '用户性别',
     user_password varchar(256) not null comment '用户密码',
     phone varchar(128) comment '用户手机',
     email varchar(512) comment '用户邮箱',
     user_status int default 0 comment '用户状态',
     create_time datetime default CURRENT_TIMESTAMP comment '创建时间',
     update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
     is_delete tinyint comment '逻辑删除'
) default charset utf8mb4 comment '用户';
```

用户中心业务设计

1.注册

​	 前端：

​			用户输入账户、密码、以及校验码

​			检验账户、密码、二次校验密码

​					账户 >= 4 位

​					密码 >= 8 位

​					账户不能重复

​					账户不包含特殊字符

​	后端：

​			双端校验，与前端相同

​			插入数据库，密码md5加密，绝对不能明存储

