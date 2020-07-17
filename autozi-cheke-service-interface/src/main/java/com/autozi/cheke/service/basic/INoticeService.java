package com.autozi.cheke.service.basic;

import com.autozi.cheke.basic.entity.Notice;
import com.autozi.cheke.party.entity.ChekeLetterRelation;
import com.autozi.common.core.page.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lbm on 2018/2/26.
 */
@Component
public interface INoticeService {
    public void insert(Notice notice);

    public void update(Notice notice);

    public Page<Notice> findPageForMap(Page<Notice> page, Map<String, Object> filters);

    public Notice getNoticeById(Long id);
}
