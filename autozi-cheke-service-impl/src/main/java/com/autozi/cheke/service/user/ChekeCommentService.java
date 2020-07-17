package com.autozi.cheke.service.user;

import com.autozi.cheke.user.dao.ChekeCommentDao;
import com.autozi.cheke.user.entity.ChekeComment;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/23 11:51
 * @Description:
 */
@Service
public class ChekeCommentService implements IChekeCommentService{
    @Autowired
    private ChekeCommentDao chekeCommentDao;


    @Override
    public Long addChekeComment(ChekeComment chekeComment){
        Long id = chekeCommentDao.insertAndGetId(chekeComment);
        return id;
    }

    @Override
    public void updateChekeComment(ChekeComment chekeComment){
        chekeCommentDao.update(chekeComment);
    }

    @Override
    public void deleteChekeComment(ChekeComment chekeComment){
        chekeCommentDao.delete(chekeComment);
    }

    @Override
    public ChekeComment getChekeCommentById(Long id){
        ChekeComment chekeComment = chekeCommentDao.get(id);
        return chekeComment;
    }

    @Override
    public ChekeComment getChekeCommentByFilter(Map<String, Object> filter){
        ChekeComment chekeComment = null;
        List<ChekeComment> list = chekeCommentDao.findListForMap(filter);
        if (list != null && list.size() > 0) {
            chekeComment = list.get(0);
        }
        return chekeComment;
    }

    @Override
    public List<ChekeComment> getCommentListByFilter(Map<String, Object> filter){
        return chekeCommentDao.findListForMap(filter);
    }

    @Override
    public Page<ChekeComment> findCommentPage(Page<ChekeComment> page, Map<String, Object> filters) {
        return chekeCommentDao.findPageForMap(page,filters);
    }

}
