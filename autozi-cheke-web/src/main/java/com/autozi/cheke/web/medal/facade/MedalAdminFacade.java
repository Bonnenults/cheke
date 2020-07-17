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
public class MedalAdminFacade {

    @Autowired
    private IMedalService medalService;

    @Autowired
    private IMedalRuleService medalRuleService;
    

    public Page<MedalVo> findMedalPage(Page<Medal> page, Map<String,Object> filter) throws Exception{
        return convertMedalVo(medalService.findMedalPage(page,filter));
    }

    public Page<MedalRuleVo> findMedalRulePage(Page<MedalRule> page, Map<String,Object> filter) throws Exception{
        return convertMedalRuleVo(medalRuleService.findMedalRulePage(page,filter));
    }

    /**
     * 新增勋章
     * @param medal
     * @return
     */
    public int addMedal(Medal medal) {
        return medalService.addMedal(medal);
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
     * 更新勋章
     * @param medal
     * @return
     */
    public int updateMedal(Medal medal) {
        return medalService.updateMedal(medal);
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
     * @param MedalPage
     * @return
     */
    private Page<MedalVo> convertMedalVo(Page<Medal> MedalPage) {
        Page<MedalVo> voPage = new Page<>();
        List<MedalVo> voList = new ArrayList<>();

        for(Medal medal : MedalPage.getResult()) {
            MedalVo vo = new MedalVo();
            BeanUtils.copyProperties(medal,vo);
            vo.setStatusCN(MedalStatus.getNameByType(medal.getStatus()));
            voList.add(vo);
        }

        PageUtil.convertPage(MedalPage,voPage,voList);
        return voPage;
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
     * 下线勋章
     * @param medalId
     * @return
     */
    public int offLineMedal(Long medalId) {
        Medal medal = medalService.getMedalInfo(medalId);
        medal.setStatus(MedalStatus.OFFLINE.getType());
        medal.setOfflineTime(new Date());
        medal.setUpdateTime(new Date());

        return medalService.updateMedal(medal);
    }

    /**
     * 上线勋章
     * @param medalId
     * @return
     */
    public int onlineMedal(Long medalId) {
        Medal medal = medalService.getMedalInfo(medalId);
        medal.setStatus(MedalStatus.PUBLISH.getType());
        medal.setPublishTime(new Date());
        medal.setUpdateTime(new Date());

        return medalService.updateMedal(medal);
    }

    /**
     * 删除勋章
     * @param medalId
     * @return
     */
    public int deleteMedal(Long medalId) {
        Medal medal = medalService.getMedalInfo(medalId);

        return medalService.deleteMedal(medal);
    }

    /**
     * 判断勋章是否已有人获得
     * @param medalId
     * @return
     */
    public boolean checkedIsRef(Long medalId) {
        Medal medal = medalService.getMedalInfo(medalId);
        if(medal.getObtainNum() > 0){
            return true;
        }
        return false;
    }

    public Medal getMedalById(Long id){
        return medalService.getMedalInfo(id);
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
