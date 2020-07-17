package com.autozi.cheke.service.user;

import com.autozi.cheke.user.dao.MedalDao;
import com.autozi.cheke.user.dao.PersonalMedalDao;
import com.autozi.cheke.user.entity.Medal;
import com.autozi.cheke.user.entity.PersonalMedal;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.entity.UserCountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/20 17:51
 * @Description:
 */
@Service
public class PersonalMedalService implements IPersonalMedalService{
    @Autowired
    private PersonalMedalDao personalMedalDao;

    @Override
    public void addPersonalMedal(PersonalMedal personalMedal){
        personalMedalDao.insert(personalMedal);
    }

    @Override
    public void updatePersonalMedal(PersonalMedal personalMedal){
        personalMedalDao.update(personalMedal);
    }

    @Override
    public List<PersonalMedal> listPersonalMedal(Map<String,Object> filter){
        return personalMedalDao.findListForMap(filter);
    }

    @Override
    public PersonalMedal getPersonalMedal(Long id){
        return personalMedalDao.get(id);
    }

    @Override
    public PersonalMedal getPersonalMedalByFilter(Map<String, Object> filter){

        List<PersonalMedal> list = personalMedalDao.findListForMap(filter);
        if(list!=null && list.size()!=0){
            PersonalMedal personalMedal = list.get(0);
            return personalMedal;
        }
        return null;
    }


}
