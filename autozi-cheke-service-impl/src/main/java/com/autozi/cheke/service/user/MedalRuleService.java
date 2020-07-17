package com.autozi.cheke.service.user;

import com.autozi.cheke.user.dao.MedalRuleDao;
import com.autozi.cheke.user.entity.MedalRule;
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
public class MedalRuleService implements IMedalRuleService{
    @Autowired
    private MedalRuleDao medalRuleDao;

    @Override
    public int addMedalRule(MedalRule medalRule){
        return medalRuleDao.insert(medalRule);
    }

    @Override
    public int updateMedalRule(MedalRule medalRule){
        return medalRuleDao.update(medalRule);
    }

    @Override
    public int deleteMedalRule(MedalRule medalRule){
        return medalRuleDao.delete(medalRule);
    }

    @Override
    public MedalRule getMedalRule(Long medalRuleId){
        return medalRuleDao.get(medalRuleId);
    }

    @Override
    public MedalRule getMedalRuleByFilter(Map<String, Object> filter){
        List<MedalRule> list = medalRuleDao.findListForMap(filter);
        if(list!=null && list.size()!=0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<MedalRule> listMedalRule(Map<String,Object> filter){
        return medalRuleDao.findListForMap(filter);
    }

    @Override
    public Page<MedalRule> findMedalRulePage(Page<MedalRule> page, Map<String, Object> filter){
        return medalRuleDao.findPageForMap(page, filter);
    }

}
