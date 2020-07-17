package com.autozi.cheke.settle.dao;
import com.autozi.cheke.settle.mapper.DrawCashOrderMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.settle.entity.DrawCashOrder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DrawCashOrderDao extends MyBatisDao<DrawCashOrder> {
    public Map<String,Object> getTotalMoney(Map<String,Object> map){
        return getMapper(DrawCashOrderMapper.class).getTotalMoney(map);
    }
}
