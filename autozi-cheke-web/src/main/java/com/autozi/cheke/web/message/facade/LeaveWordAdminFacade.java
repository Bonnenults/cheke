package com.autozi.cheke.web.message.facade;

import com.autozi.cheke.article.entity.ArticleLeaveWord;
import com.autozi.cheke.service.message.ILeaveWordService;
import com.autozi.common.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wang-lei on 2017/12/11.
 */
@Component
public class LeaveWordAdminFacade {

    @Autowired
    private ILeaveWordService leaveWordService;

    public Page<ArticleLeaveWord> findLeaveWordPage(Page<ArticleLeaveWord> page,Map<String,Object> filter) {
        return leaveWordService.findLeaveWordPage(page,filter);
    }

}
