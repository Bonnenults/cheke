package com.autozi.cheke.service.party;

import com.autozi.cheke.party.entity.ChekeFans;
import com.autozi.common.core.page.Page;

import java.util.Map;

/**
 * Created by lbm on 2017/12/15.
 */
public interface IChekeFansService {
    public void save(ChekeFans chekeFans);
    public void update(ChekeFans chekeFans);
    public void delete(Long userId,Long partyId);
	public void goAttentionCK(Long partyId, Long userId);

	/**
	 * 根据车客ID和用户ID获取关系记录
	 *
	 * @param partyId
	 * @param userId
	 * @return
	 */
	public ChekeFans getChekeFans(Long partyId, Long userId);
	/**
	 * 查看用户是否关注过
	 */
	public Boolean isAttention(Long userId,Long articleCreateId);

	/**
	 * 获取车客粉丝量
	 * @param partyId
     */
	public Integer getChekeFansCount(Long partyId);
	public Page<ChekeFans> findPageForMap(Page<ChekeFans> page, Map<String, Object> filters);
}
