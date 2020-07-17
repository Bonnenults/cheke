package com.autozi.cheke.mobile.facade;

import com.autozi.cheke.article.entity.Article;
import com.autozi.cheke.article.entity.ArticleOrder;
import com.autozi.cheke.service.article.IArticleOrderService;
import com.autozi.cheke.service.article.IArticleService;
import com.autozi.cheke.service.settle.IAccountService;
import com.autozi.cheke.service.settle.IDrawCashOrderService;
import com.autozi.cheke.settle.entity.Account;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.cheke.settle.entity.BankCard;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.common.core.page.Page;
import com.autozi.common.core.utils.ApplicationPropertiesHelper;
import com.autozi.common.utils.util2.ApplicationProperties;
import com.autozi.common.utils.util2.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lbm on 2018/1/5.
 */
@Component
public class AccountApiFacade {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IDrawCashOrderService drawCashOrderService;
    @Autowired
    private IArticleOrderService articleOrderService;
    @Autowired
    private IArticleService articleService;

    public Account getAccountByUserId(Long userId){
        return accountService.accountByOwnerIdAndType(userId, IAccountConstant.type.TYPE_TK);
    }

    public JSONArray findAccountLogByPage(Page<Map<String,Object>> page, Map<String,Object> filters){
        Page<AccountLog> _page = new Page<>();
        CommonUtils.pageConversion(page, _page);
        _page = accountService.findAccountLogPageForMap(_page,filters);
        JSONArray arr = new JSONArray();
        List<AccountLog> list = _page.getResult();
        for(AccountLog accountLog:list){
            if(accountLog==null){
                continue;
            }
            JSONObject obj = new JSONObject();
            obj.put("id",accountLog.getId());
            int sourceType = accountLog.getSourceType();
            String title = "其它";
            String flag = "+";
            if(sourceType == IAccountConstant.AccountLogSourceType.TYPE_CK_ORDER_MONEY){
                Long orderId = accountLog.getSourceId();
                title = "文章可能已被删除";
                if(orderId!=null){
                    ArticleOrder articleOrder = articleOrderService.getOrderById(orderId);
                    if(articleOrder!=null){
                        Long articleId = articleOrder.getArticleId();
                        if(articleId!=null){
                            Article article = articleService.getArticleById(articleId);
                            if(article!=null){
                                title = article.getTitle()==null?"":article.getTitle();
                            }
                        }
                    }
                }
            }else if(sourceType==IAccountConstant.AccountLogSourceType.TYPE_CK_GET_MONEY){
                flag="-";
                title = "提现";
            }else if(sourceType==IAccountConstant.AccountLogSourceType.TYPE_TK_SHARE_MONEY){
                title = "下级推客任务分成";
            }
            String money = "0.00";
            Double changeAccount = accountLog.getChangeAccount();
            if(changeAccount!=null){
                DecimalFormat df = new DecimalFormat("#.00");
                if(changeAccount.doubleValue()>=0&&changeAccount.doubleValue()<1){
                    money = flag + "0" + df.format(changeAccount);
                }else{
                    money = flag + df.format(changeAccount);
                }
            }
            obj.put("money", money);
            obj.put("title", title);
            String time = "";
            if(accountLog.getCreateTime()!=null){
                time = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(accountLog.getCreateTime());
            }
            obj.put("date", time);
            arr.add(obj);
        }
        CommonUtils.pageConversion(_page, page);
        return arr;
    }

    public int applyDrawCash(Long userId,String sMoney,Long bankCardId){
        Double money = Double.parseDouble(sMoney);
        int msgCode = drawCashOrderService.applyDrawCash(userId,money,bankCardId);
        return msgCode;
    }

    public JSONArray getBankCardByAccountId(Long accountId){
        List<BankCard> bankCardList = accountService.getBankCardByAccountId(accountId);
        JSONArray arr = new JSONArray();
        for(BankCard bankCard:bankCardList){
            if(bankCard==null){
                continue;
            }
            JSONObject obj = new JSONObject();
            obj.put("id",bankCard.getId());
            obj.put("bankName", bankCard.getBankName()==null?"":bankCard.getBankName());
            obj.put("cardNumber", bankCard.getCardNumber()==null?"":bankCard.getCardNumber());
            obj.put("bankLogo", getBankLogoByBankName(bankCard.getBankName()));
            arr.add(obj);
        }
        return arr;
    }

    public String getBankLogoByBankName(String bankName){
        if(bankName==null){
            return "";
        }
        String imgServerUrl = ApplicationProperties.getValue("cheKeDomain");
        if(!imgServerUrl.endsWith("/")){
            imgServerUrl+="/";
        }
        String bankCode = IAccountConstant.Bank.bankMap.get(bankName);
        if(bankCode==null){
            return "";
        }
        String bankLogo = imgServerUrl+"images/banklogo/"+bankCode.toLowerCase()+".png";
        return bankLogo;
    }

    public Long bindBankCard(BankCard bankCard){
        return accountService.insertBankCard(bankCard);
    }

    public JSONArray getBankList(){
        Map<String,String> bankMap = IAccountConstant.Bank.bankMap;
        JSONArray arr = new JSONArray();
        Iterator ite = bankMap.entrySet().iterator();
        while (ite.hasNext()){
            Map.Entry ent = (Map.Entry)ite.next();
            String bankName = (String)ent.getKey();
            String bankCode = (String)ent.getValue();
            String bankLogo = getBankLogoByBankName(bankName);
            JSONObject obj = new JSONObject();
            obj.put("bankName", bankName);
            obj.put("bankCode", bankCode);
            obj.put("bankLogo", bankLogo);
            arr.add(obj);
        }
        return arr;
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.00");
        Double changeAccount = 1d;
        String money = "0.00";
        if(changeAccount.doubleValue()>0&&changeAccount.doubleValue()<1){
            System.out.println(df.format(changeAccount));
            money = "0" + df.format(changeAccount);
        }else{
            money = df.format(changeAccount);
        }
        System.out.println(money);
    }
}
