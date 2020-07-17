package com.autozi.cheke.web.basic.facade;

import com.autozi.cheke.basic.entity.Properties;
import com.autozi.cheke.service.basic.IPropertiesService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mingxin.li on 2018/5/31 18:39.
 */
@Component
public class PropertiesFacade {
    @Autowired
    private IPropertiesService propertiesService;

    @Autowired
    private PropertiesRedisFacade propertiesRedisFacade;

    public int updateSpace(Integer space){

        Map<String, Object> filter = new HashedMap();
        filter.put("popKey","SPACE");
        Properties SPACE = propertiesService.getProperties(filter);
        if(SPACE==null){
            return -1;
        }
        if(Objects.equals(Integer.valueOf(SPACE.getValue()), space)){
            return 1;
        }

        SPACE.setValue(space.toString());
        SPACE.setUpdateTime(new Date());

        propertiesRedisFacade.saveSpace(space.toString());

        return propertiesService.updateProperties(SPACE);

    }

}
