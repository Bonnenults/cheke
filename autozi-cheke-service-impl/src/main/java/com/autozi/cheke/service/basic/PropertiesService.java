package com.autozi.cheke.service.basic;

import com.autozi.cheke.basic.dao.PropertiesDao;
import com.autozi.cheke.basic.entity.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mingxin.li on 2018/5/31 15:58.
 */
@Service
public class PropertiesService implements IPropertiesService {

    @Autowired
    private PropertiesDao propertiesDao;

    @Override
    public int addProperties(Properties properties) {
        return propertiesDao.insert(properties);
    }

    @Override
    public Properties getProperties(Map<String, Object> filter){
        Properties properties = null;
        List<Properties> list = propertiesDao.findListForMap(filter);
        if (list != null && list.size() > 0) {
            properties = list.get(0);
        }
        return properties;
    }

    @Override
    public int updateProperties(Properties properties){
        return propertiesDao.update(properties);
    }
}
