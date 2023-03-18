用户中心数据库表设计

```sql
create table tb_user
(
	id bigint auto_increment comment '主键id'
		primary key,
	user_account varchar(256) null comment '用户账号',
	username varchar(256) null comment '用户名',
	avatar_url varchar(1024) null comment '用户头像',
	gender tinyint null comment '用户性别',
	user_password varchar(256) not null comment '用户密码',
	phone varchar(128) null comment '用户手机',
	email varchar(512) null comment '用户邮箱',
	user_status int default 0 null comment '用户状态',
	create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
	is_delete tinyint default 0 null comment '逻辑删除'
)
comment '用户';
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

2.登录

​	前端：

​		POST请求 用户名 + 密码

​	后端：

​		先进行用户名正确性

​		再根据用户名查询密码并返回匹配结果

​		成功返回用户所有数据（脱敏）

如何知道是哪个用户登陆了？

1.连接上服务端后，得到一个session1状态，返回给前端

2.登录成功后，得到了登录成功的session，并且给session设置一些值，返回给前端一个设置cookie的命令

3.前端接收到后端的命令后，设置cookie,保存到浏览器内

4.前端再次去请求后端的时候（相同域名），在请求头中带cookie去请求

5.后端拿到cookie找到对应的session

6.从session中科院取出基于该session存储的变量，（用户的登录信息、登录名等）

```bash
npm cache clean --force # 清除
```

```bash
npm insatll yarn -g # 安装yarn
```

```bash
yarn config set registry https://registry.npm.taobao.org -g

yarn config set sass_binary_site http://cdn.npm.taobao.org/dist/node-sass -g

npm config set registry https://registry.npm.taobao.org
```

```bash
npm i @ant-design/pro-cli -g #安装客户端
```

```bash
pro create myapp # 创建模板项目
```

```bash
umi@3 #选择 umi版本 
```

```bash
simple # 轻量易扩展版本
```

```bash
yarn add @umijs/preset-ui -D  # 添加可视化UI编辑器
```

```
set-ExecutionPolicy RemoteSigned # 若在vscode里无法执行yarn，请更改安全策略，管理员打开PowerShell 
```

