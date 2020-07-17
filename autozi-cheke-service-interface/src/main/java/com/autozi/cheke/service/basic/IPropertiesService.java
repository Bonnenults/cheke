package com.autozi.cheke.service.basic;

import com.autozi.cheke.basic.entity.Properties;

import java.util.Map;

/**
 * Created by mingxin.li on 2018/5/31 15:59.
 */
public interface IPropertiesService {
    int addProperties(Properties propertis);

    Properties getProperties(Map<String, Object> filter);

    int updateProperties(Properties properties);
}
