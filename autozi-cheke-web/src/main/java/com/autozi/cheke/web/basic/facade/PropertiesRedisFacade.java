package com.autozi.cheke.web.basic.facade;

import com.autozi.cheke.basic.type.propertiesConstants;
import com.autozi.common.cache.jedis.proxy.RedisProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mingxin.li on 2018/5/31 18:54.
 */
@Component
public class PropertiesRedisFacade {
    @Autowired
    private RedisProxy redisProxy;

    /**
     * 保存space到resdis
     * @param space
     * @return
     */
    public void saveSpace(String space) {
        _saveSpace(space);
    }

    /**
     * 获取缓存中space
     * @return
     */
    public String getSpace() {
        return _getSpace();
    }

    /**
     * 保存COUNT_SORT到resdis
     * @param countSort
     * @return
     */
    public void saveCountSort(String countSort) {
        _saveCountSort(countSort);
    }

    /**
     * 获取缓存中COUNT_SORT
     * @return
     */
    public String getCountSort() {
        return _getCountSort();
    }

    // ----------------------------------------------------

    private void _saveSpace(String space) {
        redisProxy.saveStrValue(propertiesConstants.PROPERTIS_SPACE ,space.toString());
    }

    private String _getSpace(){
        return redisProxy.getStrValue(propertiesConstants.PROPERTIS_SPACE);
    }

    private void _saveCountSort(String countSort) {
        redisProxy.saveStrValue(propertiesConstants.PROPERTIS_COUNTSORTE ,countSort.toString());
    }

    private String _getCountSort(){
        return redisProxy.getStrValue(propertiesConstants.PROPERTIS_COUNTSORTE);
    }
}
