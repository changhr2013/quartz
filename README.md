# quartz
 SpringBoot 集成 Quartz 实现可控的任务
 
 1. 建立数据库，运行脚本，数据库sql脚本位于 `quartz\src\main\resources\schema\schema-mysql.sql`。
 2. 修改Spring boot配置文件相应的数据库连接参数。
 3. 运行Spring-Boot项目。
 4. 测试(使用postman进行测试)
 - 添加任务
 ```
 POST /job/save HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
	"className":"com.changhr.quartz.job.HelloJob",
	"cronExpression":"*/6 * * * * ?",
	"jobName":"HelloJob",
	"jobGroup":"TEST_GROUP",
	"triggerName":"HELLO_TRIGGER",
	"triggerGroup":"TEST_GROUP",
	"pause":"false",
	"enable":"true",
	"description":"Test HelloJob For SpringBoot"
}
 ```
 - 暂停任务
 ```
 GET /job/pause/1 HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
 ```
 - 继续执行任务
 ```
 GET /job/resume/1 HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
Postman-Token: e9c93950-e073-26de-9a75-1e281a23a719
 ```
 - 更新任务
 ```
 PUT /job/update/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
    "id":1,
    "className":"cn.com.hellowood.scheduledjob.job.TestJob",
    "cronExpression":"*/5 * * * * ?",
    "jobName":"testJob",
    "jobGroup":"TEST_GROUP",
    "triggerName":"TEST_TRIGGER",
    "triggerGroup":"TEST_GROUP",
    "pause": "false",
    "enable": "true",
    "description":"test Job for SpringBoot"
}
 ```
 - 删除任务
 ```
 DELETE /job/delete/1 HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
 ```
