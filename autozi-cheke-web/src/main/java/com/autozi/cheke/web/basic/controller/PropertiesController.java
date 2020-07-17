package com.autozi.cheke.web.basic.controller;

import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.basic.facade.PropertiesFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by mingxin.li on 2018/5/31 18:36.
 */
@RequestMapping("/basic/properties")
@Controller
public class PropertiesController extends BaseController {

    @Autowired
    private PropertiesFacade propertiesFacade;


    /**
     * 设置广告间隔
     */
    @RequestMapping("/update/updateSpace.action")
    public void updateSpace(Integer space, HttpServletResponse response) throws Exception{

        if(space<=0) {
            responseJson(response,"err","推送频率需要大于0");
            return;
        }

        int num = propertiesFacade.updateSpace(space);

        if(num > 0) {
            responseJson(response,"ok","设置成功");
        }else {
            responseJson(response,"err","设置失败");
        }

    }

}
