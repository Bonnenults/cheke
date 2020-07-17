package com.autozi.cheke.web.medal.facade;

import com.autozi.cheke.service.user.IMedalRuleService;
import com.autozi.cheke.service.user.IMedalService;
import com.autozi.cheke.user.entity.Medal;
import com.autozi.cheke.user.entity.MedalRule;
import com.autozi.cheke.user.type.MedalRuleStatus;
import com.autozi.cheke.user.type.MedalStatus;
import com.autozi.cheke.web.medal.vo.MedalRuleVo;
import com.autozi.cheke.web.medal.vo.MedalVo;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *@author mingxin li
 *@data 2018/7/2
 *
 */
@Component
public class MedalRuleAdminFacade {

    @Autowired
    private IMedalService medalService;

    @Autowired
    private IMedalRuleService medalRuleService;


    public Page<MedalRuleVo> findMedalRulePage(Page<MedalRule> page, Map<String,Object> filter) throws Exception{
        return convertMedalRuleVo(medalRuleService.findMedalRulePage(page,filter));
    }

    /**
     * 新增勋章规则
     * @param medalRule
     * @return
     */
    public int addMedalRule(MedalRule medalRule) {
        return medalRuleService.addMedalRule(medalRule);
    }


    /**
     * 更新勋章规则
     * @param medalRule
     * @return
     */
    public int updateMedalRule(MedalRule medalRule) {
        return medalRuleService.updateMedalRule(medalRule);
    }


    /**
     * 拼装
     * @param MedalRulePage
     * @return
     */
    private Page<MedalRuleVo> convertMedalRuleVo(Page<MedalRule> MedalRulePage) {
        Page<MedalRuleVo> voPage = new Page<>();
        List<MedalRuleVo> voList = new ArrayList<>();

        for(MedalRule medalRule : MedalRulePage.getResult()) {
            MedalRuleVo vo = new MedalRuleVo();
            BeanUtils.copyProperties(medalRule,vo);
            Medal medal = medalService.getMedalInfo(medalRule.getMedalId());
            if(medal != null){
                vo.setMedalName(medal.getMedalName());
            }
            vo.setStatusCN(MedalRuleStatus.getNameByType(medalRule.getStatus()));
            voList.add(vo);
        }

        PageUtil.convertPage(MedalRulePage,voPage,voList);
        return voPage;
    }

    /**
     * 下线勋章规则
     * @param medalRuleId
     * @return
     */
    public int offLineMedalRule(Long medalRuleId) {
        MedalRule medalRule = medalRuleService.getMedalRule(medalRuleId);
        medalRule.setStatus(MedalRuleStatus.OFFLINE.getType());
        medalRule.setOfflineTime(new Date());
        medalRule.setUpdateTime(new Date());

        return medalRuleService.updateMedalRule(medalRule);
    }

    /**
     * 上线勋章规则
     * @param medalRuleId
     * @return
     */
    public int onlineMedalRule(Long medalRuleId) {
        MedalRule medalRule = medalRuleService.getMedalRule(medalRuleId);
        medalRule.setStatus(MedalStatus.PUBLISH.getType());
        medalRule.setPublishTime(new Date());
        medalRule.setUpdateTime(new Date());

        return medalRuleService.updateMedalRule(medalRule);
    }

    /**
     * 删除勋章
     * @param medalRuleId
     * @return
     */
    public int deleteMedalRule(Long medalRuleId) {
        MedalRule medalRule = medalRuleService.getMedalRule(medalRuleId);

        return medalRuleService.deleteMedalRule(medalRule);
    }


    public MedalRule getMedalRuleById(Long id){
        return medalRuleService.getMedalRule(id);
    }

    public MedalRule getMedalRuleByFilter(Map<String,Object> filter){

        MedalRule medalRule = medalRuleService.getMedalRuleByFilter(filter);
        if(medalRule != null){
            return medalRule;
        }
        return null;
    }

}
