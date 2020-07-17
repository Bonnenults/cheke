package com.autozi.cheke.service.party;

import com.alibaba.dubbo.config.annotation.Service;
import com.autozi.cheke.party.dao.PartyDao;
import com.autozi.cheke.party.dao.PartyLogDao;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.entity.PartyLog;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.user.entity.User;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.util1.UID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2016-05-18
 * Time: 15:53
 */
@Service
public class PartyService implements IPartyService {
    @Autowired
    private PartyDao partyDao;
    @Autowired
    private PartyLogDao partyLogDao;

    public Party getPartyById(long partyId) {
        return partyDao.get(partyId);
    }

    @Override
    public long save(Party party) {
        party.setUpdateTime(new Date());
        party.setCreateTime(new Date());
        partyDao.insert(party);
        return party.getId();
    }

    @Override
    public void update(Party party) {
        party.setUpdateTime(new Date());
        partyDao.update(party);
    }

    @Override
    public Page<Party> findPageForMap(Page<Party> page, Map<String, Object> filters) {
        return partyDao.findPageForMap(page, filters);
    }

    @Override
    public List<Party> findList(Map<String, Object> filters) {
        return partyDao.findList(filters);
    }

    @Override
    public Page<Map<String, Object>> listParty(Page<Map<String, Object>> page, Map<String, Object> fiters) {
        return partyDao.listParty(page, fiters);
    }

    @Override
    public void savePartyLog(PartyLog partyLog) {
        partyLog.setCreateTime(new Date());
        partyLogDao.insert(partyLog);
    }

    @Override
    public List<PartyLog> findListPartyLog(Map<String, Object> filters) {
        return partyLogDao.findListForMap(filters);
    }

    @Override
    public void updatePartyLog(PartyLog partyLog) {
        partyLog.setUpdateTime(new Date());
        partyLogDao.update(partyLog);
    }

    @Override
    public void addParty(Party party) {
        party.setUpdateTime(new Date());
        if(party.getPartyClass()!=4){
            party.setInvoiceTitle(party.getCompanyName());
        }
        //修改审核需要同时插入Log表中
        PartyLog partyLog = new PartyLog();
        BeanUtils.copyProperties(party, partyLog);
        partyLog.setId(UID.next());//防止ID不唯一
        partyLog.setPartyId(party.getId());
        savePartyLog(partyLog);
        partyDao.update(party);
    }

    @Override
    public void refuseVerify(PartyLog partyLog) {
        String refuserReason = partyLog.getRefuseReason();
        Long partyId = partyLog.getPartyId();
        Party party = partyDao.get(partyId);

        int status = party.getStatus();
        //新增审核更新主表状态，修改审核拒绝需要把log表中数据倒回来
        if(status==IPartyType.STATUS_MODIFY_VERIFY){
            //克隆一份新数据
            Party temp = new Party();
            BeanUtils.copyProperties(party,temp);

            //将log表中数据导回来
            PartyLog tempLog= getPartyLogByPartyId(partyId);
            if(tempLog!=null){
                BeanUtils.copyProperties(tempLog,party);
                party.setId(partyId);
                party.setStatus(IPartyType.STATUS_REFUSE);
                partyDao.update(party);
            }
            Long logId = tempLog.getId();
            //将新数据插入到log表中
            BeanUtils.copyProperties(temp,tempLog);
            tempLog.setId(logId);
            tempLog.setPartyId(partyId);
            tempLog.setStatus(IPartyType.STATUS_REFUSE);
            tempLog.setRefuseReason(partyLog.getRefuseReason());
            partyLogDao.update(tempLog);
        }else{
            //更新状态
            Party temp = new Party();
            temp.setId(partyId);
            temp.setStatus(IPartyType.STATUS_REFUSE);
            partyDao.update(temp);
            //更新log表，为了存储拒绝原因
            PartyLog tempLog= getPartyLogByPartyId(partyId);
            tempLog.setStatus(IPartyType.STATUS_REFUSE);
            tempLog.setRefuseReason(partyLog.getRefuseReason());
            partyLogDao.update(tempLog);
        }
    }

    @Override
    public void editParty(Party party) {
        Party temp = partyDao.get(party.getId());
        //修改审核插入Log表中
        PartyLog partyLog = new PartyLog();
        BeanUtils.copyProperties(temp, partyLog);
        partyLog.setId(UID.next());//防止ID不唯一
        partyLog.setPartyId(party.getId());
        partyLog.setUpdateTime(new Date());
        partyLog.setCreateTime(party.getCreateTime());//beanutils不支持拷贝date类型
        partyLog.setStatus(IPartyType.STATUS_MODIFY_VERIFY);
        partyLogDao.insert(partyLog);

        //修改时只需更新主体表中的状态
        party.setUpdateTime(new Date());
        party.setStatus(IPartyType.STATUS_MODIFY_VERIFY);
        partyDao.update(party);
    }

    @Override
    public PartyLog getPartyLogByPartyId(Long partyId) {
        return partyLogDao.getPartyLogByPartyId(partyId);
    }
}
