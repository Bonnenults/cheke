/**
 * 文件名称   : com.wms.utils.RunningNumberHelper.java
 * 项目名称   : 中驰汽配交易平台
 * 创建日期   : 2013-4-11
 * 更新日期   :
 * 作       者   : haifeng.li@qeegoo.com
 *
 * Copyright (C) 2013 启购时代 版权所有.
 */
package com.autozi.cheke.service.basic;

import com.autozi.common.core.utils.ApplicationContextProvider;
import com.autozi.common.utils.util2.DateTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <PRE>
 * <p/>
 * 中文描述：
 * <p/>
 * </PRE>
 *
 * @version 1.0.0
 */
public class RunningNumberHelper {

    @Autowired
    private RunningNumberService runningNumberService;

    private long getNumber(String type) {
        return runningNumberService.createNextNumber(type);
    }

    private long getNumber(String type, Long partyId) {
        return runningNumberService.createNextNumber(type, partyId);
    }


    /**
     * 从Spring Context中获取RunningNumberHelper的实例，并获取单个顺序号
     *
     * @param encodable 编码器
     * @return 顺序号
     */
    private synchronized static long getNextNumber(String prefix) {
        RunningNumberHelper helper = ApplicationContextProvider.getApplicationContext().getBean(RunningNumberHelper.class);
        return helper.getNumber(prefix);
    }

    private synchronized static long getNextNumber(String prefix, Long partyId) {
        RunningNumberHelper helper = ApplicationContextProvider.getApplicationContext().getBean(RunningNumberHelper.class);
        return helper.getNumber(prefix, partyId);
    }


    /**
     * <PRE>
     * <p/>
     * 中文描述：获取编码
     * <p/>
     * </PRE>
     *
     * @param prefix
     * @param length
     * @return
     * @作者 Lihf
     * @日期 2013-4-10
     */
    public String createCode(String prefix, int length) {
        String _code = runningNumberService.createOrderCode(new CustomerCode(prefix, length));
        _code = _code.substring(0, _code.length() - length) + DateTools.getYYYYMMDD() + _code.substring(_code.length() - length);
        return _code;
    }

    /**
     * TX
     * @return
     */
    public static String getDrawCashOrderCode() {
        return "TX" + String.valueOf(getNextNumber("TX", 2L));
    }

    /**
     * 充值订单
     *
     * @return
     */
    public static String getAccountOrderCode() {
        return "CZ" + String.valueOf(getNextNumber("CZ", 2L));
    }

}
