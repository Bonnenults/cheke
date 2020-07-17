package com.autozi.cheke.service.party;

import com.alibaba.dubbo.config.annotation.Service;
import com.autozi.cheke.party.dao.ChekeFansDao;
import com.autozi.cheke.party.entity.ChekeFans;

import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2017/12/15.
 */
@Service
public class ChekeFansService implements IChekeFansService {
    @Autowired
    private ChekeFansDao chekeFansDao;

    @Override
    public void save(ChekeFans chekeFans) {
        chekeFans.setCreateTime(new Date());
        chekeFans.setUpdateTime(new Date());
        chekeFansDao.insert(chekeFans);
    }

    @Override
    public void update(ChekeFans chekeFans) {
        chekeFans.setUpdateTime(new Date());
        chekeFansDao.update(chekeFans);
    }

    @Override
    public void delete(Long userId, Long partyId) {
        ChekeFans chekeFans = new ChekeFans();
        chekeFans.setUserId(userId);
        chekeFans.setPartyId(partyId);
        chekeFansDao.cancelFollow(chekeFans);
    }

	@Override
	public void goAttentionCK(Long partyId, Long userId) {
		if(partyId==null || userId==null){
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("partyId", partyId);
		List<ChekeFans> list = chekeFansDao.findListForMap(map);
		if(list != null && list.size() != 0){
			//取消关注
			chekeFansDao.cancelFollow(list.get(0));
		}else{
			//没有关注,加上关注
			ChekeFans chekeFans = new ChekeFans();
			chekeFans.setUserId(userId);
	        chekeFans.setPartyId(partyId);
	        chekeFans.setCreateTime(new Date());
	        chekeFans.setUpdateTime(new Date());
			chekeFansDao.insert(chekeFans);
		}
	}

	@Override
	public ChekeFans getChekeFans(Long partyId, Long userId) {
		if(partyId==null || userId==null){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("partyId", partyId);
		List<ChekeFans> list = chekeFansDao.findListForMap(map);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	@Override
	public Boolean isAttention(Long userId, Long articleCreateId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("partyId", articleCreateId);
		List<ChekeFans> list = chekeFansDao.findListForMap(map);
		if(list != null && list.size() != 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Integer getChekeFansCount(Long partyId) {
		return chekeFansDao.getFansCountByPartyId(partyId);
	}

	@Override
	public Page<ChekeFans> findPageForMap(Page<ChekeFans> page, Map<String, Object> filters) {
		return chekeFansDao.findPageForMap(page,filters);
	}
}
