package com.autozi.cheke.service.user;

import com.autozi.common.core.page.IDataRows;
import com.autozi.common.core.page.Page;

/**
 * Created by wang-lei on 2017/11/29.
 */
public class DataRowsServiceImpl implements IDataRows{
    @Override
    public int getUserDataRows() {
        return Page.SIZE;
    }
}
