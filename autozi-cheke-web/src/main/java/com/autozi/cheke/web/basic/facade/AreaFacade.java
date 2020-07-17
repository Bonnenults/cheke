package com.autozi.cheke.web.basic.facade;

import com.autozi.cheke.basic.entity.Area;
import com.autozi.cheke.service.basic.IAreaService;
import com.autozi.cheke.web.basic.vo.AreaView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lbm on 2017/12/1.
 */
@Component
public class AreaFacade {
    @Autowired
    private IAreaService areaService;

    public List<AreaView> listByParentId(Long parentId) {
        if (parentId == null) {
            parentId = 0l;
        }
        List<Area> list = areaService.findByParent(parentId);
        List<AreaView> viewList = new ArrayList<>();
        for (Area area : list) {
            AreaView view = new AreaView();
            BeanUtils.copyProperties(area, view);
            viewList.add(view);
        }
        return viewList;
    }

    public Area getAreaById(Long id){
        return areaService.getById(id);
    }
}
