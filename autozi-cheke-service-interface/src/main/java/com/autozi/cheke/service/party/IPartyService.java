package com.autozi.cheke.service.party;

import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.entity.PartyLog;
import com.autozi.common.core.page.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-10-10
 * Time: 15:48
 */
@Component
public interface IPartyService {
    public Party getPartyById(long partyId);

    public long save(Party party);

    public void update(Party party);

    public Page<Party> findPageForMap(Page<Party> page, Map<String, Object> filters);

    public List<Party> findList(Map<String,Object> fiters);

    public Page<Map<String,Object>> listParty(Page<Map<String,Object>> page,Map<String,Object> fiters);

    public void savePartyLog(PartyLog partyLog);

    public List<PartyLog> findListPartyLog(Map<String,Object> fiters);

    public void updatePartyLog(PartyLog partyLog);

    /**
     * 新增主体信息
     * @param party
     */
    public void addParty(Party party);

    /**
     * 审核拒绝
     * @param partyLog
     */
    public void refuseVerify(PartyLog partyLog);

    /**
     * 修改主体信息
     * @param party
     */
    public void editParty(Party party);

    /**
     * 根据partyId获取partyLog
     * @param partyId
     * @return
     */
    public PartyLog getPartyLogByPartyId(Long partyId);

}
