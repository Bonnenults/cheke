package com.autozi.cheke.web.basic.controller;

import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.basic.facade.AreaFacade;
import com.autozi.cheke.web.basic.vo.AreaView;
import com.autozi.cheke.web.party.facade.PartyFacade;
import com.autozi.cheke.web.user.facade.UserFacade;
import com.autozi.cheke.web.user.vo.UserView;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lbm on 2017/11/28.
 */
@RequestMapping("/basic/area")
@Controller
public class AreaController extends BaseController {
    @Autowired
    private AreaFacade areaFacade;

    @RequestMapping("/list/listByParentId.action")
    public void listByParentId(Long parentId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<AreaView> list = areaFacade.listByParentId(parentId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        response(response, jsonObject.toString());
    }
}
