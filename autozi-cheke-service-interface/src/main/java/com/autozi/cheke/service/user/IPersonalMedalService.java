package com.autozi.cheke.service.user;

import com.autozi.cheke.user.entity.PersonalMedal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/20 17:51
 * @Description:
 */

@Component
public interface IPersonalMedalService {

    public void addPersonalMedal(PersonalMedal personalMedal);

    void updatePersonalMedal(PersonalMedal personalMedal);

    public List<PersonalMedal> listPersonalMedal(Map<String, Object> filter);

    PersonalMedal getPersonalMedal(Long id);

    PersonalMedal getPersonalMedalByFilter(Map<String, Object> filter);
}
