package com.autozi.cheke.service.user;

import com.autozi.cheke.user.entity.ChekeComment;
import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.user.entity.SearchFeedBack;
import com.autozi.common.core.page.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/23 11:51
 * @Description:
 */

@Component
public interface IChekeCommentService {

    public Long addChekeComment(ChekeComment chekeComment);

    public void updateChekeComment(ChekeComment chekeComment);

    void deleteChekeComment(ChekeComment chekeComment);

    ChekeComment getChekeCommentById(Long id);

    ChekeComment getChekeCommentByFilter(Map<String, Object> filter);

    List<ChekeComment> getCommentListByFilter(Map<String, Object> filter);

    Page<ChekeComment> findCommentPage(Page<ChekeComment> page, Map<String, Object> filters);
}
