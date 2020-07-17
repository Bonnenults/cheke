#!/usr/bin/env bash
# Tomcat端口配置
export DEFAULT_TOMCAT_HTTP_PORT='8080';
export DEFAULT_TOMCAT_AJP_PORT='8009';
export DEFAULT_TOMCAT_SERVER_PORT='8005';
# 检查目标环境
if [ -z "$TOMCAT_HTTP_PORT" ]; then
    export TOMCAT_HTTP_PORT="$DEFAULT_TOMCAT_HTTP_PORT";
fi
if [ -z "$TOMCAT_AJP_PORT" ]; then
    export TOMCAT_AJP_PORT="$DEFAULT_TOMCAT_AJP_PORT";
fi
if [ -z "$TOMCAT_SERVER_PORT" ]; then
    export TOMCAT_SERVER_PORT="$DEFAULT_TOMCAT_SERVER_PORT";
fi
# 打印并重写配置
sed -n "s/Connector port=\"8080\"/Connector port=\"$TOMCAT_HTTP_PORT\"/"p /usr/local/tomcat/conf/server.xml
sed -i "s/Connector port=\"8080\"/Connector port=\"$TOMCAT_HTTP_PORT\"/" /usr/local/tomcat/conf/server.xml
sed -n "s/Connector port=\"8009\"/Connector port=\"$TOMCAT_AJP_PORT\"/"p /usr/local/tomcat/conf/server.xml
sed -i "s/Connector port=\"8009\"/Connector port=\"$TOMCAT_AJP_PORT\"/" /usr/local/tomcat/conf/server.xml
sed -n "s/Server port=\"8005\"/Server port=\"$TOMCAT_SERVER_PORT\"/"p /usr/local/tomcat/conf/server.xml
sed -i "s/Server port=\"8005\"/Server port=\"$TOMCAT_SERVER_PORT\"/" /usr/local/tomcat/conf/server.xml
echo "即将启动Tomcat,并使用端口HTTP:$TOMCAT_HTTP_PORT,AJP:$TOMCAT_AJP_PORT,Server:$TOMCAT_SERVER_PORT";