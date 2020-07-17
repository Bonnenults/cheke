#!/usr/bin/env bash
# 遇到任意命令返回非0则推出脚本
set -e
# 获取脚本所在的路径
bash_dir=$(cd `dirname $0`; pwd)
echo "启动脚本所在路径:$bash_dir";
# 设置默认参数
# 配置文件存储路径
export DEFAULT_AUTOZI_CONFIG_TMP_PATH='/tmp/config';
# 默认的配置文件路径,公共配置
export DEFAULT_AUTOZI_CONFIG_PATH='/usr/local/tomcat/webapps/ROOT/WEB-INF/classes';
# 处理公共配置文件
# 检查目标环境
if [ -z "$AUTOZI_CONFIG_TMP_PATH" ]; then
    export AUTOZI_CONFIG_TMP_PATH="$DEFAULT_AUTOZI_CONFIG_TMP_PATH";
fi
if [ -z "$AUTOZI_CONFIG_PATH" ]; then
    export AUTOZI_CONFIG_PATH="$DEFAULT_AUTOZI_CONFIG_PATH";
fi
echo "完成公共配置参数处理。AUTOZI_CONFIG_TMP_PACH:$AUTOZI_CONFIG_TMP_PATH ; AUTOZI_CONF_PATH:$AUTOZI_CONFIG_PATH";
# 更新全部配置文件
. ${bash_dir}/Get_Configuration.sh
# 处理App配置
. ${bash_dir}/App_Configuration.sh
# 处理Java配置
. ${bash_dir}/Java_Configuration.sh
# 处理Tomcat配置
. ${bash_dir}/Tomcat_Configuration.sh
# 处理各环境独立脚本
. ${bash_dir}/Script_Configuration.sh

# 执行启动命令
exec "$@"