package com.autozi.cheke.service.user;

import com.autozi.cheke.user.dao.MedalDao;
import com.autozi.cheke.user.entity.Medal;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/20 17:51
 * @Description:
 */
@Service
public class MedalService implements IMedalService{
    @Autowired
    private MedalDao medalDao;

    @Override
    public int addMedal(Medal medal){
       return medalDao.insert(medal);
    }

    @Override
    public Medal getMedalInfo(Long medalId){
        return medalDao.get(medalId);
    }

    @Override
    public int updateMedal(Medal medal){
        return medalDao.update(medal);
    }

    @Override
    public int deleteMedal(Medal medal){
        return medalDao.delete(medal);
    }

    @Override
    public void updateMedalInf(Medal medal){
        medalDao.update(medal);
    }

    @Override
    public List<Medal> listMedal(Map<String,Object> filter){
        return medalDao.findList(filter);
    }

    @Override
    public Page<Medal> findMedalPage(Page<Medal> page, Map<String, Object> filter){
        return medalDao.findPageForMap(page, filter);
    }

}
