package com.autozi.cheke.service.basic;

import com.autozi.cheke.basic.entity.Area;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lbm on 2017/12/1.
 */
@Component
public interface IAreaService {
    public List<Area> findByParent(Long parentId);
    public Area getById(Long areaId);
    public Area getByCode(String areaCode);
    public List<Area> listAll();
}
