package com.autozi.cheke.mobile.web;


import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.MedalApiFacade;
import com.autozi.cheke.service.user.IMedalService;
import com.autozi.cheke.service.user.IPersonalMedalService;
import com.autozi.cheke.user.entity.Medal;
import com.autozi.cheke.user.entity.PersonalMedal;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.type.MedalStatus;
import com.autozi.common.core.exception.B2bException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mingxin.li on 2018/6/20 10:42.
 */

@RequestMapping("/medal/tk/")
@Controller
public class MedalApiController extends BaseApiController{

    @Autowired
    private MedalApiFacade medalApiFacade;
    @Autowired
    private IPersonalMedalService personalMedalService;
    @Autowired
    private IMedalService medalService;

    /**
     * 勋章列表	medalList
     * @param request
     * @param response
     */
    @RequestMapping("listMedals.api")
    public void listCourse(HttpServletRequest request, HttpServletResponse response){
        JSONObject data = new JSONObject();
        try {
            //根据token获取用户
            User user = this.getUser(request);
            //封装查询medal条件
            Map<String, Object> filter = new HashMap<String, Object>();

            //封装查询medal_personal条件
            Map<String, Object> filter_per = new HashMap<String, Object>();

            //勋章状态：1已上线
            filter.put("status", MedalStatus.PUBLISH.getType());
            if (user == null ) {
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            filter_per.put("userId", user.getId());

            JSONArray list = medalApiFacade.listMedal(filter,filter_per);
            List<PersonalMedal> list_per = personalMedalService.listPersonalMedal(filter_per);

            data.put("list", list);
            data.put("myMedalNum",list_per.size());
            data.put("medalNum",list.size());

            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    @RequestMapping("adornMedal.api")
    public void adornMedal(HttpServletRequest request,HttpServletResponse response){
        try {
            //根据token获取用户
            User user = this.getUser(request);
            if (user == null) {
                this.response(request, response, MobileConstant.Error._tokenIsExpire, MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String medalId = request.getParameter("medalId");
            if (StringUtils.isBlank(medalId)) {
                this.response(request, response, MobileConstant.Error._error_access, MobileConstant.Error._error_access_msg);
                return;
            }

            Map<String, Object> filter = new HashMap<>();
            filter.put("userId", user.getId());
            filter.put("medalId", Long.valueOf(medalId));

            PersonalMedal personalMedal = medalApiFacade.getPersonalMedalByFilter(filter);
            if (personalMedal == null) {
                this.response(request, response, MobileConstant.Error._error_access, MobileConstant.Error._error_access_msg);
                return;
            }
            medalApiFacade.setPersonalMedalUnadorn(user.getId());

            personalMedal.setStatus(MedalStatus.ADORN.getType());
            personalMedal.setUpdateTime(new Date());
            medalApiFacade.updatePersonalMedal(personalMedal);

            medalApiFacade.countMedalAdornNum(Long.valueOf(medalId));

            Medal medal = medalApiFacade.getMedalById(Long.valueOf(medalId));
            JSONObject data = new JSONObject();
            data.put("medalId",medalId);
            data.put("medalName",medal.getMedalName());
            data.put("image",medal.getImageActive());
            data.put("intro",medal.getIntro());
            data.put("obtainNum",medal.getObtainNum());
            data.put("adornNum",medal.getAdornNum());

            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }


    @RequestMapping("unAdornMedal.api")
    public void unAdornMedal(HttpServletRequest request, HttpServletResponse response){
        try {
            //根据token获取用户
            User user = this.getUser(request);
            if (user == null) {
                this.response(request, response, MobileConstant.Error._tokenIsExpire, MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
			String medalId = request.getParameter("medalId");
			if (StringUtils.isBlank(medalId)) {
                this.response(request, response, MobileConstant.Error._error_access, MobileConstant.Error._error_access_msg);
                return;
            }
            medalApiFacade.setPersonalMedalUnadorn(user.getId());
			
			Medal medal = medalApiFacade.getMedalById(Long.valueOf(medalId));
            JSONObject data = new JSONObject();
            data.put("medalId",medalId);
            data.put("medalName",medal.getMedalName());
            data.put("image",medal.getImageActive());
            data.put("intro",medal.getIntro());
            data.put("obtainNum",medal.getObtainNum());
            data.put("adornNum",medal.getAdornNum());

            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    @RequestMapping("medalInfo.api")
    public void medalInfo(HttpServletRequest request, HttpServletResponse response){
        try {
            //根据token获取用户
            User user = this.getUser(request);
            if (user == null) {
                this.response(request, response, MobileConstant.Error._tokenIsExpire, MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String medalId = request.getParameter("medalId");
            if (!StringUtils.isNotBlank(medalId)) {
                this.response(request, response, MobileConstant.Error._error_access, MobileConstant.Error._error_access_msg);
                return;
            }

            JSONObject data = medalApiFacade.getMedalInfo(Long.valueOf(medalId));
            boolean flag = medalApiFacade.checkIsObtain(Long.valueOf(medalId),user.getId());
            if(flag){
                data.put("isObtain",1);
            }else{
                data.put("isObtain",0);
            }
            boolean flag_adorn = medalApiFacade.checkIsAdorn(Long.valueOf(medalId),user.getId());
            if(flag_adorn){
                data.put("isAdornn",1);
            }else{
                data.put("isAdornn",0);
            }
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

}
