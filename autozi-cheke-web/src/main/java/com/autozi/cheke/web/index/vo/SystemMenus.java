package com.autozi.cheke.web.index.vo;


import com.autozi.cheke.user.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 生成系统菜单
 */
public class SystemMenus {

    private final static SystemMenus INSTANCE = new SystemMenus();


    private MenuList menus;

    private SystemMenus() {
        initMenus();
    }

    /**
     * 初始化菜单
     */
    private void initMenus() {
        menus = new MenuList(new HashSet<Integer>());
        //车客菜单 从10 开始
        menus.add(new Menu(10, "首页", "/frontPage.action", 0, Menu.TYPE_MENU));

        menus.add(new Menu(99, "角色管理", "/authority/admin/index.action", 0, Menu.TYPE_MENU));
        menus.add(new Menu(991, "角色管理", "/role/admin/list/listRole.action", 99, Menu.TYPE_MENU));
        //---------------------车客平台后台菜单start------------------------------
        //车客-信息管理
        menus.add(new Menu(11, "信息管理", "信息管理-车客", "/authority/index.action?id=11", 0, Menu.TYPE_MENU));
        menus.add(new Menu(111, "资讯管理", "/article/yxpt/list/listArticle.action", 11, Menu.TYPE_MENU));
        menus.add(new Menu(112, "培训管理", "/video/yxpt/list/listArticle.action", 11, Menu.TYPE_MENU));
        menus.add(new Menu(113, "项目管理", "/project/yxpt/list/listArticle.action", 11, Menu.TYPE_MENU));
        menus.add(new Menu(114, "信息查询", "/article/yxpt/query/queryArticle.action", 11, Menu.TYPE_MENU));
        menus.add(new Menu(115, "留言查询", "/leaveWord/yxpt/list/listLeaveWordDetail.action", 11, Menu.TYPE_MENU));


        //车客-资金管理
        menus.add(new Menu(12, "资金管理", "资金管理-车客", "/authority/index.action?id=12", 0, Menu.TYPE_MENU));
        menus.add(new Menu(121, "资金账户", "/account/ck/list/showAccount.action", 12, Menu.TYPE_MENU));
        menus.add(new Menu(122, "账户明细", "/account/ck/accountLog/listAccountLog.action", 12, Menu.TYPE_MENU));
        menus.add(new Menu(124, "发票管理", "/invoice/cheke/list/listAccountOrder.action", 12, Menu.TYPE_MENU));
        //车客-账户管理
        menus.add(new Menu(13, "账户管理", "账户管理-车客", "/authority/index.action?id=13", 0, Menu.TYPE_MENU));
        menus.add(new Menu(131, "基础信息", "/cheke/party/edit/userInfo.action", 13, Menu.TYPE_MENU));
        menus.add(new Menu(132, "私信列表", "/letter/cheke/list/listLetter.action", 13, Menu.TYPE_MENU));
        //---------------------车客平台后台菜单end------------------------------


        //---------------------运营平台菜单start------------------------------
        //车客-信息管理
        menus.add(new Menu(31, "车客管理", "车客管理-运营", "/authority/index.action?id=31", 0, Menu.TYPE_MENU));
        menus.add(new Menu(311, "车客列表", "/admin/party/list/listParty.action",31, Menu.TYPE_MENU));

        menus.add(new Menu(32, "留言管理", "留言管理-运营", "/authority/index.action?id=32", 0, Menu.TYPE_MENU));
        menus.add(new Menu(321, "留言列表", "/leaveWord/admin/list/listLeaveWord.action",32, Menu.TYPE_MENU));
        menus.add(new Menu(322, "私信列表", "/letter/admin/list/listLetterAdmin.action", 32, Menu.TYPE_MENU));
        menus.add(new Menu(323, "意见反馈", "/feedback/admin/list/listFeedback.action",32, Menu.TYPE_MENU));
        menus.add(new Menu(324, "搜索反馈", "/feedback/admin/list/listFeedbackForSearch.action",32, Menu.TYPE_MENU));

        menus.add(new Menu(33, "推客管理", "推客管理-运营", "/authority/index.action?id=33", 0, Menu.TYPE_MENU));
        menus.add(new Menu(331, "推客列表", "/tuike/user/list/listTuikeUser.action",33, Menu.TYPE_MENU));
        menus.add(new Menu(332, "勋章管理", "/medal/admin/list/listMedal.action",33, Menu.TYPE_MENU));
        menus.add(new Menu(333, "勋章规则", "/medalRule/admin/list/listMedalRule.action",33, Menu.TYPE_MENU));

        menus.add(new Menu(34, "信息管理", "信息管理-运营", "/authority/index.action?id=34", 0, Menu.TYPE_MENU));
        menus.add(new Menu(341, "信息列表", "/article/admin/list/listArticle.action",34, Menu.TYPE_MENU));
        menus.add(new Menu(342, "信息分类", "/article/admin/list/listArticleTag.action", 34, Menu.TYPE_MENU));
        menus.add(new Menu(343, "课程管理", "/course/admin/list/listCourse.action", 34, Menu.TYPE_MENU));
        menus.add(new Menu(344, "广告管理", "/article/ad/list/listAd.action", 34, Menu.TYPE_MENU));

        menus.add(new Menu(35, "财务管理", "财务管理-运营", "/authority/index.action?id=35", 0, Menu.TYPE_MENU));
        menus.add(new Menu(351, "订单查询", "/accountOrder/admin/list/listAccountOrder.action",35, Menu.TYPE_MENU));
        menus.add(new Menu(352, "资金账户", "/account/admin/listAccount/listAccountForAdmin.action", 35, Menu.TYPE_MENU));
        menus.add(new Menu(353, "充值开票", "/invoice/admin/list/listInvoice.action", 35, Menu.TYPE_MENU));
        menus.add(new Menu(354, "推客提现", "/drawCashOrder/admin/list/listDrawCashOrder.action", 35, Menu.TYPE_MENU));

        menus.add(new Menu(36, "系统设置", "系统设置-运营", "/authority/index.action?id=36", 0, Menu.TYPE_MENU));
        menus.add(new Menu(361, "角色权限", "/role/admin/list/listRole.action", 36, Menu.TYPE_MENU));
        menus.add(new Menu(362, "用户管理", "/user/admin/list/listUser.action", 36, Menu.TYPE_MENU));
        //---------------------运营平台菜单end------------------------------
    }

    public static Menu getMenu(Integer id) {
        return INSTANCE.menus.getById(id);
    }

    public static List<Menu> getMenus(int type) {
        List<Menu> cloneList = new ArrayList<Menu>();
        for (Menu menu : INSTANCE.menus) {
            if (menu.checkType(type)) {
                cloneList.add(menu.copy());
            }
        }
        return cloneList;
    }

    public static List<Menu> getAllMenus(User user) {
        List<Menu> cloneList = new ArrayList<Menu>();
        for (Menu menu : INSTANCE.menus) {
        	//新增、修改角色，不需要转化URL
//            Menu tmp = menu.copy();
//            if (menu.checkType(Menu.TYPE_B2C_MENU)) {
//                tmp.setUrl(SsoCodec.urlEncodeForB2cLogin(user.getLoginName(), user.getPassword(), menu.getUrl()));
//            } else if (menu.checkType(Menu.TYPE_PX_MENU)) {
//            	tmp.setUrl(SsoCodec.urlEncodeForPxLogin(user.getLoginName(), user.getPassword(), menu.getUrl(), menu.getId() + ""));
//            }
            cloneList.add(menu.copy());
        }
        return cloneList;
    }




    @SuppressWarnings("serial")
    private static class MenuList extends ArrayList<Menu> {
        private Set<Integer> blacklist;
        private Map<Integer, Menu> map = new HashMap<Integer, Menu>();

        public MenuList(Set<Integer> blacklist) {
            this.blacklist = blacklist;
        }

        @Override
        public boolean add(Menu menu) {
            if (blacklist.contains(menu.getId())) {
                return false;
            } else {
                map.put(menu.getId(), menu);
                return super.add(menu);
            }
        }

        public Menu getById(Integer id) {
            return map.get(id);
        }
    }

}
