package com.autozi.cheke.service.basic;

import com.autozi.cheke.basic.dao.NoticeDao;
import com.autozi.cheke.basic.entity.Notice;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by lbm on 2018/2/26.
 */
public class NoticeService implements INoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public void insert(Notice notice) {
        notice.setCreateTime(new Date());
        notice.setUpdateTime(new Date());
        noticeDao.insert(notice);
    }

    @Override
    public void update(Notice notice) {
        notice.setUpdateTime(new Date());
        noticeDao.update(notice);
    }

    @Override
    public Page<Notice> findPageForMap(Page<Notice> page, Map<String, Object> filters) {
        return noticeDao.findPageForMap(page,filters);
    }

    @Override
    public Notice getNoticeById(Long id) {
        return noticeDao.get(id);
    }
}
