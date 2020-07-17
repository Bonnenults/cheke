package com.autozi.cheke.service.user;

import com.autozi.cheke.user.entity.Medal;
import com.autozi.cheke.user.entity.MedalRule;
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
public interface IMedalRuleService {

    public int addMedalRule(MedalRule medalRule);

    int updateMedalRule(MedalRule medalRule);

    int deleteMedalRule(MedalRule medalRule);

    MedalRule getMedalRule(Long medalRuleId);

    MedalRule getMedalRuleByFilter(Map<String, Object> filter);

    public List<MedalRule> listMedalRule(Map<String, Object> filter);

    Page<MedalRule> findMedalRulePage(Page<MedalRule> page, Map<String, Object> filter);
}
