package com.autozi.cheke.service.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.autozi.cheke.basic.dao.AreaDao;
import com.autozi.cheke.basic.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2017/12/1.
 */
@Service
public class AreaService implements IAreaService{
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> findByParent(Long parentId) {
        Map<String,Object> filters = new HashMap<>();
        filters.put("parentId",parentId);
        return areaDao.findListForMap(filters);
    }

    @Override
    public Area getById(Long areaId) {
        return areaDao.get(areaId);
    }

    @Override
    public Area getByCode(String areaCode) {
        Map<String,Object> filters = new HashMap<>();
        filters.put("areaCode",areaCode);
        List<Area> list = areaDao.findListForMap(filters);
        return list.get(0);
    }

    @Override
    public List<Area> listAll() {
        return areaDao.listAll();
    }
}
