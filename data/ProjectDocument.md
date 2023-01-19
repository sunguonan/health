多对多 要借助一个中间表进行查询
一对多 在多的中加一列
user -->   class
多个用户(class_id) --->  一个班级

-----

vue 有四个生命周期 八个钩子方法
create 创建 -- beforecreate()  created()
mounte 挂载 -- beforemounte()  mounted()
update 更新 -- beforeupdate()  updated()
destory 销毁 -- beforedestory() destoried()

-----

http状态码
2xx 没问题
3xx 302重定向
4xx 客户端有问题 404 浏览器访问服务器没有的页面
5xx 服务端有问题

-----

数据格式
form-data是基于post请求来传递数据的 可以传递文件
x-www-form-urlencod 也是基于post请求传递数据
对于get请求和post请求 走的格式都是urlencode格式 key=value&key=value

-----

cron表达式 -- 定时任务
\* * * * * * *
秒 分 时 日 月 周 年

特殊字符:
逗号（,）：指定一个值列表，例如使用在月域上1,4,5,7表示1月、4月、5月和7月
横杠（-）：指定一个范围，例如在时域上3-6表示3点到6点（即3点、4点、5点、6点）
**星号**（*）：表示这个域上包含所有合法的值。例如，在月份域上使用星号意味着每个月都会触发
**斜线**（/）：表示递增，例如使用在秒域上0/15表示每15秒
问号（?）：只能用在日和周域上，但是不能在这两个域上同时使用。表示不指定
#号（#）：只能使用在周域上，用于指定月份中的第几周的哪一天，例如6#3，意思是某月的第三个周五 (6=星期五，3意味着月份中的第三周)
**母亲节**
L：某域上允许的最后一个值。只能使用在日和周域上。当用在日域上，表示的是在月域上指定的月份的最后一天。用于周域上时，表示周的最后一天，就是星期六
W：W 字符代表着工作日 (星期一到星期五)，只能用在日域上，它用来指定离指定日的最近的一个工作日。

0 0 14 * * * *
每天的下午两点做某事

0/2 * * * * *
从每分钟的0秒开始每隔2秒执行一次
一分钟执行30次

-----

json --> {name:User.getname()} 使用get方法拿出对应的值
mybatis --> select * from table; 查出数据 调用setxxx()方法 存放在实体类中 到时候要用的时候 调用getxxx()方法获取即可

-----

cookie 客户端 大小限制:4k
session 服务端 放在内存、Redis

1. 当用户登入会把用户名等信息放在session存储起来 并且会发一个标识符给cookie
2. 当下次用户访问页面时 会把cookie携带发送给服务端
3. 到服务端后拿出cookie的值去session找 如果登入了就访问相关页面

-----

!!!写任何代码 -->  代码逻辑：**先把错排除了**！

-----

后端鉴权设计的表 总共五张表 (随着项目设计的不同 在这五张表的基础上还会新增不同的表)

user 用户
user_role 关联表 多对多

role 角色
role_auth 关联表 多对多

auth 权限

---

redis 默认端口6379

---

Redis的持久化策略 -- aof and rdb

rdb 直接将内存快照存在硬盘上
aof 将指令存在硬盘上 比如
在Redis中我们数据存储
set a b
set c d
delete a
使用aof策略时 只会存储 set c d  (会自动进行优化)

---

Redis的单线程可以保证事务的隔离级别性质 不会出现脏读 幻读
Redis可以支持 30w/s






