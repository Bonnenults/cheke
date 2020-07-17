FROM hub.autozi.net/library/tomcat:8_jdk7
#设置时区和语言环境变量
ENV TZ Asia/Shanghai
# 添加nfs支持
RUN yum install -y nfs-utils rpcbind && yum clean all
#配置项目标签
LABEL team passcar
LABEL project mobile
LABEL service web
# 安装配置管理Agent
ADD http://autozi.lipg.cn/cloud/config/config-1.0 /entrypoint/config
RUN chmod +x /entrypoint/config
# 配置初始化脚本
COPY ./entrypoint/ /entrypoint/
RUN chmod +x /entrypoint/entrypoint.sh
ENTRYPOINT ["/entrypoint/entrypoint.sh"]
# 更新server.xml
COPY ./app/tomcat/server.xml /usr/local/tomcat/conf/
# 清理无用文件
RUN rm -rf /usr/local/tomcat/webapps/*
# 拷贝项目运行文件
COPY ./target/ /usr/local/tomcat/webapps/ROOT/
# 配置默认的环境变量
# 配置日志的volume
VOLUME /data/logs
# 启动命令
#CMD ["catalina.sh", "run"]
CMD /usr/local/tomcat/bin/catalina.sh start && tail -f /usr/local/tomcat/logs/catalina.out