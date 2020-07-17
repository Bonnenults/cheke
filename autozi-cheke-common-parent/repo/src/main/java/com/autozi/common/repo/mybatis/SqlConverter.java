package com.autozi.common.repo.mybatis;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kai.liu
 * Date: 11-11-30
 * Time: 上午10:12
 */
public interface SqlConverter {

    List<?> exec(List<?> sqlList);
}
