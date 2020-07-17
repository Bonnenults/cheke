package com.autozi.cheke.web.settle.facade;

import com.autozi.cheke.service.settle.IChanpayService;
import com.autozi.cheke.service.settle.IDrawCashOrderService;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.settle.entity.DrawCashOrder;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.web.settle.vo.DrawCashOrderVo;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import com.autozi.common.utils.web.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-13
 * Time: 13:51
 */
@Component
public class DrawCashOrderFacade {
    @Autowired
    private IDrawCashOrderService drawCashOrderService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IChanpayService chanpayService;

    public Page<DrawCashOrderVo> findAdminPageForMap(Page<DrawCashOrder> page, Map<String, Object> filters) {
        page = drawCashOrderService.findPageForMap(page, filters);
        Page<DrawCashOrderVo> viewPage = new Page<>();
        List<DrawCashOrderVo> viewList = new ArrayList<>();
        for (DrawCashOrder drawCashOrder : page.getResult()) {
            DrawCashOrderVo drawCashOrderVo = new DrawCashOrderVo();
            BeanUtils.copyProperties(drawCashOrder, drawCashOrderVo);
            if(drawCashOrder.getUserId()==null){
                continue;
            }
            User tkUser = userService.getUserById(drawCashOrder.getUserId());
            if(tkUser==null){
                continue;
            }
            drawCashOrderVo.setTkName(tkUser.getName()==null?"":tkUser.getName());
            drawCashOrderVo.setTkPhone(tkUser.getPhone());
            viewList.add(drawCashOrderVo);
        }
        PageUtil.convertPage(page, viewPage, viewList);
        return viewPage;
    }

    public String confirmDrawCash(Long orderId){
        return chanpayService.dsfPay(orderId);
    }

    public void testDsfCallBack(Long orderId){
        chanpayService.testDsfCallBack(orderId);
    }

    public Map<String,Object> getTotalMoney(Map<String,Object> map){
        return drawCashOrderService.getTotalMoney(map);
    }
}
