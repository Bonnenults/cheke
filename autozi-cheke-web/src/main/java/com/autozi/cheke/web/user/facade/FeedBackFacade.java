package com.autozi.cheke.web.user.facade;

import com.autozi.cheke.user.entity.FeedBack;
import com.autozi.cheke.web.user.vo.FeedBackView;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2018/6/15 17:49
 * @Description:
 */

@Component
public class FeedBackFacade {

    public Page<FeedBackView> findForFeedBackPage(Page<FeedBack> page, Map<String, Object> filters) {
        //page = FeedBackService.findFeedBackPageForMap(page, filters);
        Page<FeedBackView> viewPage = new Page<FeedBackView>();
        List<FeedBackView> viewList = new ArrayList<FeedBackView>();
        for (FeedBack obj : page.getResult()) {
            if (obj == null || obj.getId() == null) {
                continue;
            }
            FeedBackView view = new FeedBackView();
            BeanUtils.copyProperties(obj, view);
            viewList.add(view);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }
}
