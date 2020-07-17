package com.autozi.cheke.service.user;

import com.autozi.cheke.party.entity.ChekeFans;
import com.autozi.cheke.user.entity.ChekeCollection;
import com.autozi.cheke.user.entity.ChekeComment;
import com.autozi.common.core.page.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/25 11:21
 * @Description:
 */

@Component
public interface IChekeCollectionService {

    public void addChekeCollection(ChekeCollection chekeCollection);

    public void updateChekeCollection(ChekeCollection chekeCollection);

    public void deleteChekeCollection(Long collectionId,Long userId);

    public void goCollection(Long collectionId, Long userId,Integer isCourse);

    Boolean isCollected(Long collectionId, Long userId);

    Page<ChekeCollection> findCollectionPage(Page<ChekeCollection> page, Map<String, Object> filters);
}
