#!/usr/bin/env bash
# 配置文件配置
# 配置文件所在的服务器
export DEFAULT_AUTOZI_CONFIG_URL='https://git.autozi.com';
# 配置文件仓库名称
export DEFAULT_AUTOZI_CONFIG_REPO='cheke/api-ci';
# 配置文件表示
export DEFAULT_AUTOZI_CONFIG_TARGET='master';
# 授权的Token
export DEFAULT_AUTOZI_CONFIG_TOKEN='';

# 检查配置参数
if [ -z "$AUTOZI_CONFIG_URL" ]; then
    export AUTOZI_CONFIG_URL="$DEFAULT_AUTOZI_CONFIG_URL";
fi
if [ -z "$AUTOZI_CONFIG_REPO" ]; then
    export AUTOZI_CONFIG_REPO="$DEFAULT_AUTOZI_CONFIG_REPO";
fi
if [ -z "$AUTOZI_CONFIG_TARGET" ]; then
    export AUTOZI_CONFIG_TARGET="$DEFAULT_AUTOZI_CONFIG_TARGET";
fi
if [ -z "$AUTOZI_CONFIG_TOKEN" ]; then
    export AUTOZI_CONFIG_TOKEN="$DEFAULT_AUTOZI_CONFIG_TOKEN";
fi
# 下载配置
mkdir -pv ${AUTOZI_CONFIG_TMP_PATH}
$bash_dir/config --url=${AUTOZI_CONFIG_URL} --repo=${AUTOZI_CONFIG_REPO} --branch=${AUTOZI_CONFIG_TARGET} --token=${AUTOZI_CONFIG_TOKEN} --path=${AUTOZI_CONFIG_TMP_PATH}/config.tar
# 解压配置文件
cd ${AUTOZI_CONFIG_TMP_PATH} && tar -xvf config.tar --strip-components=1 && rm -rf config.tar && ls -lR
