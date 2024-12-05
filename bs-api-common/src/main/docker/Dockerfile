#配置jdk
FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17-centos

#将当前目录下的所有 .jar 文件合并成一个名为 app.jar 的文件
ADD *.jar /app.jar

# 暴露端口号
EXPOSE 9090

# 运行项目
ENTRYPOINT  ["java","-jar","/app.jar"]

