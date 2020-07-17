/**
 *
 */
package com.autozi.cheke.web.index.controller;

import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.service.user.IUserService;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.cheke.util.mvc.BaseController;
import com.autozi.cheke.web.article.facade.ArticleFacade;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.cheke.web.index.facade.MenuFacade;
import com.autozi.cheke.web.index.vo.Menu;
import com.autozi.cheke.web.party.facade.ChekeLetterFacade;
import com.autozi.cheke.web.party.facade.PartyFacade;
import com.autozi.cheke.web.settle.facade.AccountFacade;
import com.autozi.cheke.web.settle.vo.AccountVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author shixin.zhang
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private MenuFacade menuFacade;
    @Autowired
    private ArticleFacade articleFacade;
    @Autowired
    private AccountFacade accountFacade;
    @Autowired
    private PartyFacade partyFacade;
    @Autowired
    private ChekeLetterFacade chekeLetterFacade;

    /**
     * 登陆后首页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/index.action")
    public String index() throws Exception {
        return "/index/index";
    }

    /**
     * 头部
     *
     * @param uiModel
     * @return
     */
    @RequestMapping("/top.action")
    public String topAdmin(Model uiModel) {
        User user = getCurrentUser();
        Long partyId = user.getPartyId();
        int count = chekeLetterFacade.getLetterCount(partyId);
        uiModel.addAttribute("letterCount",count);
        uiModel.addAttribute("user",user);
        Party party = partyFacade.getPartyById(user.getPartyId());
        uiModel.addAttribute("party",party);
        uiModel.addAttribute("userName",user.getLoginName());
        return "/index/top";
    }

    /**
     * 左侧菜单
     *
     * @param uiModel
     * @return
     */
    @RequestMapping("/menus.action")
    public String menusAdmin(Model uiModel) {
        List<Menu> navMenus = menuFacade.getAvailableNavMenus(getCurrentUser());
        uiModel.addAttribute("mainMenus", navMenus);
        return "/index/menus";
    }

    @RequestMapping("/frontPage.action")
    public String frontPageAdmin(HttpServletRequest request, Model uiModel) throws Exception {
        request.getSession().removeAttribute("CAPTCHA_NUMBER");
        User user = getCurrentUser();
        uiModel.addAttribute("userView", user);
        if(user.getUserType()== IUserType.USER_TYPE_CHEKE){
            AccountVo account = accountFacade.getAccountVoByPartyId(user.getPartyId());
            uiModel.addAttribute("accountVo", account);
            Map<String, Object> filters = new HashMap<>();
            filters.put("aIsTask",1);
            filters.put("createUserId", user.getId());
            filters.put("status", ArticleStatus.PUBLISH.getType());
            List<ArticleVo> list = articleFacade.getTaskList(filters);
            uiModel.addAttribute("list", list);
            //获取总花费金额
            Double totalCost = accountFacade.getAllCost(user.getPartyId());
            uiModel.addAttribute("totalCost", totalCost);
            //获取花费金额
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            filters.clear();
            filters.put("partyId",user.getPartyId());
            filters.put("createTimeStart", cal.getTime());
            filters.put("createTimeEnd",new Date());
            Double weekCost = accountFacade.getTotalMoney(filters);
            uiModel.addAttribute("weekCost", weekCost);
            uiModel.addAttribute("totalCost", totalCost);
            uiModel.addAttribute("date", new Date());
            uiModel.addAttribute("party", partyFacade.getPartyById(user.getPartyId()));
        }
        return "/index/frontPage";
    }

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        System.out.println(date);
    }
}
