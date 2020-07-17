package com.autozi.cheke.web.settle.controller;

import com.autozi.cheke.settle.entity.DrawCashOrder;
import com.autozi.cheke.settle.type.IDrawCashOrderConstant;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.settle.facade.DrawCashOrderFacade;
import com.autozi.cheke.web.settle.vo.DrawCashOrderVo;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-12
 * Time: 10:42
 */
@Controller
@RequestMapping("/drawCashOrder")
public class DrawCashOrderController extends BaseController {
    @Autowired
    DrawCashOrderFacade drawCashOrderFacade;

    /**
     * 平台查看充值订单列表
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/list/listDrawCashOrder.action")
    public String listDrawCashOrder(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Map<String, Object> filters = buildFilter(request, uiModel);
        User user = getCurrentUser();
        Page<DrawCashOrder> page = buildPage(request);
        filters.put("status", IDrawCashOrderConstant.status.STATUS_CREATE);//查待审核的
        Page<DrawCashOrderVo> orderPage = drawCashOrderFacade.findAdminPageForMap(page, filters);
        uiModel.addAttribute("orderPage", orderPage);
        this.pageInfoByMap(uiModel, orderPage, filters);
        if (ajax != null) {
            return "/admin/settle/drawCashOrder/listDrawCashOrder_ajax";
        }
        return "/admin/settle/drawCashOrder/listDrawCashOrder.html";
    }


    /**
     * 平台查看充值订单列表
     *
     * @param request
     * @param uiModel
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/list/listDrawCashOrderPayEnd.action")
    public String listDrawCashOrderPayEnd(HttpServletRequest request, Model uiModel, String ajax) throws UnsupportedEncodingException {
        Map<String, Object> filters = buildFilter(request, uiModel);
        User user = getCurrentUser();
        Page<DrawCashOrder> page = buildPage(request);
        filters.put("status", IDrawCashOrderConstant.status.STATUS_END);
        Page<DrawCashOrderVo> orderPage = drawCashOrderFacade.findAdminPageForMap(page, filters);
        Map<String, Object> map = new HashMap<>();
        map.put("status", IDrawCashOrderConstant.status.STATUS_END);
        Map<String,Object> result = drawCashOrderFacade.getTotalMoney(map);
        uiModel.addAttribute("orderPage", orderPage);
        uiModel.addAttribute("totalMoney",result.get("totalMoney").toString());
        uiModel.addAttribute("fee",result.get("fee").toString());
        uiModel.addAttribute("orderCount",result.get("orderCount").toString());
        this.pageInfoByMap(uiModel, orderPage, filters);
        if (ajax != null) {
            return "/admin/settle/drawCashOrder/listDrawCashOrderPayEnd_ajax";
        }
        return "/admin/settle/drawCashOrder/listDrawCashOrderPayEnd.html";
    }

    /**
     * 确认提现
     * @param orderId
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/list/confirmDrawCash.action")
    public void confirmDrawCash(Long orderId,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        String msg="";
        try{
            msg = drawCashOrderFacade.confirmDrawCash(orderId);
            //drawCashOrderFacade.testDsfCallBack(orderId);
        }catch (Exception e){
            msg = "后台错误";
        }
        jsonObject.put("msg", msg);
        this.response(response, jsonObject.toString());
    }


}
