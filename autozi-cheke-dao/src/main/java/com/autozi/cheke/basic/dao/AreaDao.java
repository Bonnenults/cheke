package com.autozi.cheke.basic.dao;
import com.autozi.cheke.basic.mapper.AreaMapper;
import com.autozi.common.dal.mybatis.MyBatisDao;
import com.autozi.cheke.basic.entity.Area;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AreaDao extends MyBatisDao<Area> {
    public List<Area> listAll(){
        return getMapper(AreaMapper.class).listAll();
    }
}
