#!/usr/bin/env bash
# 检查目标环境
sed -n "s/jdk.tls.disabledAlgorithms/\#jdk.tls.disabledAlgorithms/"p ${JAVA_HOME}/jre/lib/security/java.security
sed -i "s/jdk.tls.disabledAlgorithms/\#jdk.tls.disabledAlgorithms/" ${JAVA_HOME}/jre/lib/security/java.security
echo "调整Java配置成功";