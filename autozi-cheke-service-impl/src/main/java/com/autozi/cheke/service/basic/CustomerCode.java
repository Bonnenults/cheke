/**
 * 文件名称   : com.wms.login.service.CustomerCode.java
 * 项目名称   : 中驰汽配交易平台
 * 创建日期   : 2013-4-10
 * 更新日期   :
 * 作       者   : haifeng.li@qeegoo.com
 *
 * Copyright (C) 2013 启购时代 版权所有.
 */
package com.autozi.cheke.service.basic;

import org.apache.commons.lang.StringUtils;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
public class CustomerCode implements RunningNumberEncodable {
    
    private String prefix;
    private int length;

    public CustomerCode(String prefix, int length) {
        this.prefix = prefix;
        this.length = length;
    }
    
    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String encode(long indexNumber) {
        return prefix + StringUtils.leftPad(String.valueOf(indexNumber), length, '0');
    }
}
