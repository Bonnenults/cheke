package com.autozi.cheke.mobile.web;

import com.autozi.cheke.article.type.ArticleStatus;
import com.autozi.cheke.mobile.constant.MobileConstant;
import com.autozi.cheke.mobile.facade.AccountApiFacade;
import com.autozi.cheke.mobile.facade.UserApiFacade;
import com.autozi.cheke.settle.entity.Account;
import com.autozi.cheke.settle.entity.AccountLog;
import com.autozi.cheke.settle.entity.BankCard;
import com.autozi.cheke.settle.type.IAccountConstant;
import com.autozi.cheke.user.entity.PersonalParty;
import com.autozi.cheke.user.entity.User;
import com.autozi.cheke.user.type.IUserType;
import com.autozi.common.core.exception.B2bException;
import com.autozi.common.core.page.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lbm on 2018/1/5.
 */
@RequestMapping("/account/tk/")
@Controller
public class AccountApiController extends BaseApiController{
    @Autowired
    private AccountApiFacade accountApiFacade;
    @Autowired
    private UserApiFacade userApiFacade;

    @RequestMapping("myWallet.api")
    public void myWallet(HttpServletRequest request, HttpServletResponse response){
        JSONObject data = new JSONObject();
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            //封装分页
            Page<Map<String,Object>> page = new Page<>();
            String pageNo = request.getParameter("pageNo");
            if (StringUtils.isNotBlank(pageNo)) {
                page.setPageNo(Integer.parseInt(pageNo));
            } else {
                page.setPageNo(MobileConstant.PageSize._page_no);
            }
            String pageSizeStr = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(pageSizeStr)) {
                page.setPageSize(Integer.parseInt(pageSizeStr));
            } else {
                page.setPageSize(MobileConstant.PageSize._page_size);
            }
            Long userId = user.getId();
            PersonalParty personalParty = userApiFacade.getPersonalParty(user.getPartyId());
            Account account = accountApiFacade.getAccountByUserId(userId);
            //获取账户变更列表
            Map<String,Object> filters = new HashMap<>();
            Long accountId = account.getId();
            filters.put("accountId", accountId);
            JSONArray list = accountApiFacade.findAccountLogByPage(page, filters);
            Double accountMoney = account.getAccount();
            DecimalFormat df = new DecimalFormat("#.00");
            String total = "0.00";
            if(accountMoney.doubleValue()>=0&&accountMoney.doubleValue()<1){
                total="0"+df.format(accountMoney);
            }else{
                total=df.format(accountMoney);
            }
            data.put("totalMoney", total);
            data.put("IDCardStatus",personalParty.getIdCardStatus());
            data.put("list", list);
            data.put("pageNo", page.getPageNo());
            data.put("totalPages", page.getTotalPages());
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,data.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    //跳转到提现页面
    @RequestMapping("toDrawCash.api")
    public void toDrawCash(HttpServletRequest request, HttpServletResponse response){
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            Account account = accountApiFacade.getAccountByUserId(user.getId());
            Long accountId = account.getId();
            //获取银行卡列表
            JSONArray arr = accountApiFacade.getBankCardByAccountId(accountId);
            JSONObject obj = new JSONObject();
            obj.put("cardList", arr);
            String money = "0.00";
            Double changeAccount = account.getAccount();
            if(changeAccount!=null){
                DecimalFormat df = new DecimalFormat("#.00");
                if(changeAccount.doubleValue()>=0&&changeAccount.doubleValue()<1){
                    money="0"+df.format(changeAccount);
                }else{
                    money=df.format(changeAccount);
                }
            }
            obj.put("totalMoney", money);
            obj.put("rateFee", "1.00");
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,obj.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    //跳转到绑定银行卡
    @RequestMapping("toBindBankCard.api")
    public void toBindBankCard(HttpServletRequest request, HttpServletResponse response){
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            JSONObject obj = new JSONObject();
            obj.put("realName", user.getRealName()==null?"":user.getRealName());
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,obj.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    //跳转到绑定银行卡
    @RequestMapping("getBankList.api")
    public void getBankList(HttpServletRequest request, HttpServletResponse response){
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            JSONArray arr = accountApiFacade.getBankList();
            JSONObject obj = new JSONObject();
            obj.put("bankList",arr);
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,obj.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    //绑定银行卡
    @RequestMapping("bindBankCard.api")
    public void bindBankCard(HttpServletRequest request, HttpServletResponse response){
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String realName = request.getParameter("realName");
            String bankName = request.getParameter("bankName");
            String cardNumber = request.getParameter("cardNumber");

            if(cardNumber==null||"".equals(cardNumber.trim())){
                this.response(request,response, MobileConstant.Error._bankNumberNull,MobileConstant.Error._bankNumberNull_msg);
                return;
            }

            if(cardNumber.length()>20||cardNumber.length()<12){
                this.response(request,response, MobileConstant.Error._bankNumberLength,MobileConstant.Error._bankNumberLength_msg);
                return;
            }

            if(!isNumeric(cardNumber.replace(" ",""))){
                this.response(request,response, MobileConstant.Error._bankNumber,MobileConstant.Error._bankNumber_msg);
                return;
            }
            if(realName!=null && !realName.equals("")){
                if(realName.length()>30||realName.length()<2){
                    this.response(request,response, MobileConstant.Error._realNameLength,MobileConstant.Error._realNameLength_msg);
                    return;
                }
                user.setRealName(realName);
            }
            Account account = accountApiFacade.getAccountByUserId(user.getId());
            Long accountId = account.getId();
            //获取银行卡列表
            JSONArray arr = accountApiFacade.getBankCardByAccountId(accountId);
            for (int i = 0; i < arr.size(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String tempNumber = obj.getString("cardNumber");
                if(cardNumber.equals(tempNumber)){
                    this.response(request,response, MobileConstant.Error._bankCardRepeat, MobileConstant.Error._bankCardRepeat_msg);
                    return;
                }
            }
            String bankLogo = accountApiFacade.getBankLogoByBankName(bankName);

            BankCard card = new BankCard();
            card.setAccountId(accountId);
            card.setBankName(bankName);
            card.setCardNumber(cardNumber);
            card.setName(realName);

            JSONObject obj = new JSONObject();
            Long cardId = accountApiFacade.bindBankCard(card);
            obj.put("bankCardId", cardId);
            obj.put("bankName", bankName);
            obj.put("cardNumber", cardNumber);
            obj.put("bankLogo", bankLogo);
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg,obj.toString());
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    //提现
    @RequestMapping("applyDrawCash.api")
    public void applyDrawCash(HttpServletRequest request, HttpServletResponse response){
        try {
            User user = this.getUser(request);
            //是否登录
            if(user==null){
                this.response(request,response, MobileConstant.Error._tokenIsExpire,MobileConstant.Error._tokenIsExpire_msg);
                return;
            }
            String money = request.getParameter("money");
            String bankCardId = request.getParameter("bankCardId");
            PersonalParty personalParty =userApiFacade.getPersonalParty(user.getPartyId());
            Integer idCardStatus = personalParty.getIdCardStatus();
            Integer verifyFlag = user.getVerifyFlag();
            if(verifyFlag== null || verifyFlag!= 1){
                if(idCardStatus== IUserType.TUIKE_STATUS_REGISTER){
                    this.response(request,response, MobileConstant.Error._idNotUpload, MobileConstant.Error._idNotUpload_msg);
                    return;
                }
                if(idCardStatus== IUserType.TUIKE_STATUS_VERIFY || idCardStatus== IUserType.TUIKE_STATUS_REFUSE){
                    this.response(request,response, MobileConstant.Error._idNotVerify, MobileConstant.Error._idNotVerify_msg);
                    return;
                }
            }
            if(money==null || "".equals(money.trim())){
                this.response(request,response, MobileConstant.Error._moneyNull, MobileConstant.Error._moneyNull_msg);
                return;
            }
            Double dMoney = Double.parseDouble(money);
            if(dMoney%10!=0){
                this.response(request,response, MobileConstant.Error._money10, MobileConstant.Error._money10_msg);
                return;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 6);
            calendar.add(Calendar.MONTH, 0);
            Date now = new Date();
            if(now.getTime()<calendar.getTime().getTime()){
                this.response(request,response, MobileConstant.Error._isNot6Date, MobileConstant.Error._isNot6Date_msg);
                return;
            }
            Account account = accountApiFacade.getAccountByUserId(user.getId());
            Double accountMoney = account.getAccount();
            Double applyMoney = Double.parseDouble(money);
            if(applyMoney.doubleValue()>accountMoney.doubleValue()){
                this.response(request,response, MobileConstant.Error._moneyPoor, MobileConstant.Error._moneyPoor_msg);
                return;
            }
            accountApiFacade.applyDrawCash(user.getId(),money,Long.parseLong(bankCardId));
            this.response(request,response, MobileConstant.Error._ok, MobileConstant.Error._ok_msg);
        }catch (B2bException e){
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.response(request,response, MobileConstant.Error._error, MobileConstant.Error._error_msg);
        }
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
