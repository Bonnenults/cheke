package com.autozi.cheke.service.user;

import com.autozi.cheke.user.entity.Medal;
import com.autozi.common.core.page.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/20 17:51
 * @Description:
 */

@Component
public interface IMedalService {

    public int addMedal(Medal medal);

    Medal getMedalInfo(Long medalId);

    int updateMedal(Medal medal);

    int deleteMedal(Medal medal);

    void updateMedalInf(Medal medal);

    public List<Medal> listMedal(Map<String,Object> filter);

    Page<Medal> findMedalPage(Page<Medal> page, Map<String, Object> filter);
}
