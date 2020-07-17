package com.autozi.cheke.web.settle.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleCountInfo;
import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.party.entity.Party;
import com.autozi.cheke.party.type.IPartyType;
import com.autozi.cheke.service.article.IArticleCountInfoService;
import com.autozi.cheke.service.article.IArticleService;
import com.autozi.cheke.service.party.IPartyService;
import com.autozi.cheke.service.settle.IAccountService;
import com.autozi.cheke.settle.entity.Account;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.web.article.vo.ArticleVo;
import com.autozi.cheke.web.settle.vo.AccountLogVo;
import com.autozi.cheke.web.settle.vo.AccountVo;
import com.autozi.common.core.page.Page;
import com.autozi.common.utils.o2o.PageUtil;
import com.autozi.common.utils.util2.DateTools;
import com.autozi.common.utils.util2.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: long.jin
 * Date: 2017-12-04
 * Time: 14:51
 */
@Component
public class AccountFacade {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IPartyService partyService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleCountInfoService articleCountInfoService;


    public AccountVo getAccountVoByPartyId(long partyId) {
        Account account = accountService.accountByOwnerIdAndType(partyId, IAccountConstant.type.TYPE_CK);
        AccountVo accountVo = new AccountVo();
        BeanUtils.copyProperties(account, accountVo);
        Party party = partyService.getPartyById(account.getOwnerId());
        if(party!=null){
            accountVo.setPartyName(party.getName());
            accountVo.setCompanyName(party.getCompanyName());
        }
        return accountVo;
    }

    public Page<AccountLogVo> findAccountLogPage(Page<AccountLog> page, Map<String, Object> filter) {
        filter =DateUtils.String2Date(filter);
        page = accountService.findAccountLogPageForMap(page, filter);
        Page<AccountLogVo> voPage = new Page<>();
        List<AccountLogVo> voList = new ArrayList<>();
        for (AccountLog accountLog : page.getResult()) {
            AccountLogVo accountLogVo = new AccountLogVo();
            BeanUtils.copyProperties(accountLog, accountLogVo);
            String title = getLogTitle(accountLog);
            accountLogVo.setNote(title);
            accountLogVo.setSourceTypeCN(IAccountConstant.AccountLogSourceType.typeMap.get(accountLog.getSourceType().toString()));
            voList.add(accountLogVo);
        }
        PageUtil.convertPage(page, voPage, voList);
        return voPage;
    }

    public String getLogTitle(AccountLog accountLog){
        Integer sourceType = accountLog.getSourceType();
        if(sourceType==null || accountLog.getSourceId()==null){
            return "";
        }
        if(sourceType.intValue()==IAccountConstant.AccountLogSourceType.TYPE_SPREAD_RECHARGE){
            //充值
            return "账户充值";
        }else if(sourceType.intValue()==IAccountConstant.AccountLogSourceType.TYPE_SPREAD_COST){
            //推广付费
            Long sourceId = accountLog.getSourceId();
            Article article = articleService.getArticleById(sourceId);
            if(article==null){
                return "文章可能已被删除";
            }
            String title = article.getTitle() == null ? "" : article.getTitle();
            return title;
        }else if(sourceType.intValue()==IAccountConstant.AccountLogSourceType.TYPE_SPREAD_RETURN){
            //推广退回
            Long sourceId = accountLog.getSourceId();
            Article article = articleService.getArticleById(sourceId);
            if(article==null){
                return "文章可能已被删除";
            }
            String title = article.getTitle() == null ? "" : article.getTitle();
            return title;
        }
        return "";
    }


    public Page<AccountLogVo> findAccountLogPageForAdmin(Page<AccountLog> page, Map<String, Object> filter) {
        page = accountService.findAccountLogPageForMap(page, filter);
        Page<AccountLogVo> voPage = new Page<>();
        List<AccountLogVo> voList = new ArrayList<>();
        for (AccountLog accountLog : page.getResult()) {
            AccountLogVo accountLogVo = new AccountLogVo();
            BeanUtils.copyProperties(accountLog, accountLogVo);
            accountLogVo.setSourceTypeCN(IAccountConstant.AccountLogSourceType.typeMap.get(accountLog.getSourceType().toString()));
            voList.add(accountLogVo);
        }
        PageUtil.convertPage(page, voPage, voList);
        return voPage;
    }

    /**
     * 查询所有的账户
     * @param page
     * @param filter
     * @return
     */
    public Page<AccountVo> findAccountPage(Page<Account> page, Map<String, Object> filter) {
        page = accountService.findAccountPage(page,filter);
        Page<AccountVo> viewPage  = new Page<>();
        List<AccountVo> viewList =  new ArrayList<>();
        for(Account account:page.getResult()){
            AccountVo vo = new AccountVo();
            BeanUtils.copyProperties(account, vo);
            viewList.add(vo);
            Party party = partyService.getPartyById(account.getOwnerId());
            if(party !=null){
                vo.setPartyName(party.getName());
                String partyClassName = "";
                Integer partyClass = party.getPartyClass();
                if(partyClass!=null){
                    partyClassName = IPartyType.partyClassMap.get(party.getPartyClass().toString());
                }
                vo.setPartyClassName(partyClassName);
                vo.setCompanyName(party.getCompanyName());
            }
        }
        PageUtil.convertPage(page,viewPage,viewList);
        return viewPage;
    }

    public Page<ArticleVo> findArticlePage(Page<Article> page, Map<String, Object> filter) throws Exception {
        page = articleService.findArticlePage(page,filter);
        Page<ArticleVo> viewPage  = new Page<>();
        List<ArticleVo> viewList =  new ArrayList<>();
        for(Article article:page.getResult()){
            if(article==null){
                continue;
            }
            ArticleVo vo = new ArticleVo();
            BeanUtils.copyProperties(article, vo);
            ArticleCountInfo articleCountInfo = articleCountInfoService.getCountInfoByArticleId(article.getId());
            Double allCost = articleCountInfo.getAllCost();
            Double usedCost = articleCountInfo.getUsedCost();//余额
            Double onceCost = articleCountInfo.getOnceCost();
            Double cost = allCost-usedCost;
            if(article.getStatus()==ArticleStatus.PUBLISH.getType()){
                usedCost=0d;
            }
            int clickCount = articleCountInfo.getPageView();
            vo.setAllCost(allCost);
            vo.setUsedCost(usedCost);
            vo.setOnceCost(onceCost);
            vo.setPageView(clickCount);
            vo.setCost(cost);
            viewList.add(vo);
        }
        PageUtil.convertPage(page,viewPage,viewList);
        return viewPage;
    }

    public Double getTotalMoney(Map<String, Object> filter) {
        return articleCountInfoService.countCostForParty(filter);
    }

    public Double getAllCost(Long partyId) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("partyId", partyId);
        filters.put("onlineAndEndStatus", "22");
        return articleCountInfoService.countTotalCostForParty(filters);
    }

}
