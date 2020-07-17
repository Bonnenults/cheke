package com.autozi.cheke.service.party;

import com.autozi.cheke.party.dao.ChekeLetterDao;
import com.autozi.cheke.party.dao.ChekeLetterRelationDao;
import com.autozi.cheke.party.dao.PartyDao;
import com.autozi.cheke.party.entity.ChekeLetter;
import com.autozi.cheke.party.entity.ChekeLetterRelation;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.user.dao.UserDao;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2017/12/15.
 */
@Service
public class ChekeLetterService implements IChekeLetterService{
    @Autowired
    private ChekeLetterDao chekeLetterDao;
    @Autowired
    private ChekeLetterRelationDao chekeLetterRelationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PartyDao partyDao;


    @Override
    public List<ChekeLetter> getListByRelationId(Long relationId) {
        ChekeLetter letter = new ChekeLetter();
        letter.setRelationId(relationId);
        return chekeLetterDao.findListForExample(letter);
    }

    @Override
    public Page<ChekeLetterRelation> findPageForMap(Page<ChekeLetterRelation> page, Map<String, Object> filters) {
        return chekeLetterRelationDao.findPageForMap(page,filters);
    }

    @Override
    public ChekeLetter getChekeLetterById(Long id) {
        return chekeLetterDao.get(id);
    }

    @Override
    public void toCheke(ChekeLetter chekeLetter) {
        Long userId = chekeLetter.getFromUserId();
        Long partyId = chekeLetter.getToUserId();
        save(userId,partyId,chekeLetter,1);//推客给车客发私信，状态为1
    }

    @Override
    public void toUser(ChekeLetter chekeLetter) {
        Long userId = chekeLetter.getToUserId();
        Long partyId = chekeLetter.getFromUserId();
        save(userId,partyId,chekeLetter,2);//车客给推客发私信，状态为2
    }

    private void save(Long userId,Long partyId,ChekeLetter chekeLetter,int status){
        //查看是否已经存在关系
        ChekeLetterRelation chekeLetterRelation = new ChekeLetterRelation();
        chekeLetterRelation.setUserId(userId);
        chekeLetterRelation.setPartyId(partyId);
        List<ChekeLetterRelation> list = chekeLetterRelationDao.findListForExample(chekeLetterRelation);

        //若还没有私信过，则新建关联关系
        ChekeLetterRelation relation = new ChekeLetterRelation();
        relation.setStatus(status);
        relation.setContent(chekeLetter.getContent());
        if(list!=null && list.size()==0){
            User user = userDao.get(userId);
            Party party = partyDao.get(partyId);
            relation.setPartyId(partyId);
            relation.setUserId(userId);
            relation.setPhone(user.getPhone());
            relation.setUserName(user.getName());
            relation.setPartyName(party.getName());
            relation.setPartyClass(party.getPartyClass());
            relation.setUpdateTime(new Date());
            relation.setCreateTime(new Date());
            chekeLetterRelationDao.insert(relation);
        }else{
            relation.setId(list.get(0).getId());
            relation.setUpdateTime(new Date());
            chekeLetterRelationDao.update(relation);
        }
        //存储私信内容
        chekeLetter.setRelationId(relation.getId());
        chekeLetter.setCreateTime(new Date());
        chekeLetter.setUpdateTime(new Date());
        chekeLetterDao.insert(chekeLetter);
    }

    @Override
    public Page<Map<String,Object>> findPartyLetterList(Page<Map<String, Object>> page, Map<String, Object> filters) {
        return chekeLetterRelationDao.findPartyLetterList(page,filters);
    }

    @Override
    public ChekeLetterRelation getChekeLetterRelationById(Long id) {
        return chekeLetterRelationDao.get(id);
    }

    @Override
    public int getLetterCount(Long partyId,int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("partyId", partyId);
        map.put("status", status);
        return chekeLetterRelationDao.getLetterCount(map);
    }

    @Override
    public List<ChekeLetter> checkNewLetter(ChekeLetter chekeLetter) {
        return chekeLetterDao.findListForExample(chekeLetter);
    }

}
