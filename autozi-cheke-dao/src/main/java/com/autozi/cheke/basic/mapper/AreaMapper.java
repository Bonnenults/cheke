package com.autozi.cheke.basic.mapper;
import com.autozi.common.dal.mapper.BaseMapper;
import com.autozi.cheke.basic.entity.Area;

import java.util.List;

public interface AreaMapper extends BaseMapper<Area> {
    public List<Area> listAll();
}
