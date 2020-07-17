#!/usr/bin/env bash
# 默认执行的脚本
export DEFAULT_AUTOZI_SCRIPT_FILE=('fs');
# 检查脚本是否存在
if [ ! -d "$AUTOZI_CONFIG_TMP_PATH"  ] || [ ! -d "$AUTOZI_CONFIG_TMP_PATH/script"  ] ; then
    echo '配置脚本不存在，将不处理任何脚本!'
fi
# 检查初始化脚本的环境变量
if [ -z "$AUTOZI_SCRIPT_FILE" ]; then
    AUTOZI_SCRIPT_FILE=${DEFAULT_AUTOZI_SCRIPT_FILE[@]}
else
    # 从环境变量获取需要执行的脚本
    OLD_IFS="$IFS"
    IFS=","
    AUTOZI_SCRIPT_FILE=(${AUTOZI_SCRIPT_FILE})
    IFS="$OLD_IFS"
fi
# 再次确认脚本
if [ ! ${AUTOZI_SCRIPT_FILE} ]; then
    AUTOZI_SCRIPT_FILE=()
fi
set -x
# 载入软件环境变量配置
for script in ${AUTOZI_SCRIPT_FILE[@]};do
    if [ -f "$AUTOZI_CONFIG_TMP_PATH/script/$script.sh" ]; then
        source "$AUTOZI_CONFIG_TMP_PATH/script/$script.sh"
    else
        echo "文件$AUTOZI_CONFIG_TMP_PATH/script/$script.sh不存在，不进行载入。"
    fi
done
set +x