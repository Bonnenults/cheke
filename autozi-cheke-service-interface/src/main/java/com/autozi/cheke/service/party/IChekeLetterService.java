package com.autozi.cheke.service.party;

import com.autozi.cheke.party.entity.ChekeLetter;
import com.autozi.cheke.party.entity.ChekeLetterRelation;
import com.autozi.common.core.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2017/12/15.
 */
public interface IChekeLetterService {
    public List<ChekeLetter> getListByRelationId(Long relationId);
    public Page<ChekeLetterRelation> findPageForMap(Page<ChekeLetterRelation> page, Map<String, Object> filters);
    public ChekeLetter getChekeLetterById(Long id);
    public void toCheke(ChekeLetter chekeLetter);
    public void toUser(ChekeLetter chekeLetter);
    public Page<Map<String,Object>> findPartyLetterList(Page<Map<String, Object>> page, Map<String, Object> filters);
    public ChekeLetterRelation getChekeLetterRelationById(Long id);
    public int getLetterCount(Long partyId,int status);

    /**
     * 获取最新的消息
     *
     * @param chekeLetter
     * @return
     */
    public List<ChekeLetter> checkNewLetter(ChekeLetter chekeLetter);
}
