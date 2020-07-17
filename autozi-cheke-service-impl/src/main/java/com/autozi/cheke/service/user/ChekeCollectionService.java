package com.autozi.cheke.service.user;

import com.autozi.cheke.party.entity.ChekeFans;
import com.autozi.cheke.user.dao.ChekeCollectionDao;
import com.autozi.cheke.user.dao.ChekeCommentDao;
import com.autozi.cheke.user.entity.ChekeCollection;
import com.autozi.cheke.user.entity.ChekeComment;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/25 11:27
 * @Description:
 */
@Service
public class ChekeCollectionService implements IChekeCollectionService{
    @Autowired
    private ChekeCollectionDao chekeCollectionDao;


    @Override
    public void addChekeCollection(ChekeCollection chekeCollection){
        chekeCollectionDao.insert(chekeCollection);
    }

    @Override
    public void updateChekeCollection(ChekeCollection chekeCollection){
        chekeCollectionDao.update(chekeCollection);
    }

    @Override
    public void deleteChekeCollection(Long collectionId,Long userId){
        ChekeCollection chekeCollection = new ChekeCollection();
        chekeCollection.setCollectionId(collectionId);
        chekeCollection.setUserId(userId);
        chekeCollectionDao.cancelCollect(chekeCollection);
    }
    @Override
    public void goCollection(Long collectionId, Long userId,Integer isCourse){
        if(collectionId==null || userId==null){
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("collectionId", collectionId);
        map.put("userId", userId);

        List<ChekeCollection> list = chekeCollectionDao.findListForMap(map);
        if(list != null && list.size() != 0){
            //取消收藏
            chekeCollectionDao.cancelCollect(list.get(0));
        }else{
            //没有收藏,加上收藏
            ChekeCollection chekeCollection = new ChekeCollection();
            chekeCollection.setCollectionId(collectionId);
            chekeCollection.setUserId(userId);
            chekeCollection.setIsCourse(isCourse);
            chekeCollection.setCreateTime(new Date());
            chekeCollection.setUpdateTime(new Date());
            chekeCollectionDao.insert(chekeCollection);
        }
    }

    @Override
    public Boolean isCollected(Long collectionId, Long userId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("collectionId", collectionId);
        map.put("userId", userId);

        List<ChekeCollection> list = chekeCollectionDao.findListForMap(map);
        if(list != null && list.size() != 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Page<ChekeCollection> findCollectionPage(Page<ChekeCollection> page, Map<String, Object> filters) {
        return chekeCollectionDao.findPageForMap(page,filters);
    }

}
