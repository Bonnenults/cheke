#!/usr/bin/env bash
# 检查目标环境
# 检查原始配置文件目录是否存在
if [ ! -d "$AUTOZI_CONFIG_TMP_PATH"  ] || [ ! -d "$AUTOZI_CONFIG_TMP_PATH/app"  ] ; then
    echo '配置文件不存在，将不处理任何配置';
fi
# 检查目标配置文件目录是否存在,不存在的话则创建目录
if [ ! -d "$AUTOZI_CONFIG_PATH" ]; then
    mkdir -pv ${AUTOZI_CONFIG_PATH};
fi
# 拷贝配置文件到指定目录
\cp -rv ${AUTOZI_CONFIG_TMP_PATH}/app/* ${AUTOZI_CONFIG_PATH}/
echo "完成APP配置文件处理。应用配置文件:$AUTOZI_CONFIG_TMP_PATH/app，目标路径:$AUTOZI_CONFIG_PATH";