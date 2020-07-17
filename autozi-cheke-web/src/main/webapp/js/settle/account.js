/**
 * Created by phoenix on 2017-12-05.
 */
var Account = {};

/**
 * 切换充值记录订单
 * @param target
 * @param status
 */
Account.changeAccountOrderType = function (target,status) {
    $(".qy_seach_wrap input").val("");
   $("[name='payStatus']").each(function(index,item){
       $(item).removeClass("act");
   });
    $(target).addClass("act");
    $.ajax({
        type:'post',
        url:context+'/account/ck/list/listAccountOrderAjax.action',
        data:{'status':status},
        success: function (data) {
           $("#changeId").html('').html(data);
        }
    })
};

/**
 * 账户充值重新计算可用金额
 */
Account.countRealMoney = function () {

   var allFee = $("#allFee").val();
    var money = $("#realMoney").val();
    var reg= "^[0-9]{3,6}$"
    var re = new RegExp(reg);
    if(re.test(money)){
        var accountMoney = Number(money) -(Number(money).mul(Number(allFee)));
        $("#accountMoney").val(accountMoney);
    }
};

/**
 * 提交充值订单
 */
Account.submitRechargeMoney=function(){
    var money = $("#realMoney").val();
    if(!money){
        mizhu.alert("请输入充值金额");
        return;
    }
    if(money==""){
        mizhu.alert("请输入充值金额");
        return;
    }
    if(money&&money<500){
        mizhu.alert("充值金额输入值必须是不小于500的正整数");
        return;
    }
    if(money>100000){
        mizhu.alert("充值金额不能超过10万元");
        return;
    }
    window.location.href=context+"/account/ck/list/submitRechargeMoney.action?realMoney="+money;
};

Account.bindRechargeMoney=function(){
    //只能输入大于500的整数，其他字符或是小于500，就无法输入
    $("#realMoney").on('keyup', function (event) {
        var once_money = $(this);
        once_money.val(once_money.val().replace(/[^\d.]/g, ""));
        var val=once_money.val();
        if(val&&val>0){
            once_money.val(parseInt(val))
        }else{
            once_money.val("")
        }
    });
}

Account.eBankPay=function(orderId){
    $.ajax({
        type:'post',
        url:context+'/account/ck/list/eBankPay.action',
        dataType:"json",
        data:{'orderId':orderId},
        success: function (data) {
            var url = data.url;
            window.open(url);
        }
    })
};

Account.bindSelectPay=function(){
    $('.lf_order_third_party input').click(function(){
        $('.lf_order_third_party div').removeClass('act');
        $(this).parent().addClass('act');
    });
}

Account.selectPay=function(type,orderId){
    mizhu.confirm("支付确认","确定跳转到支付页面？",function (){
        if(type==1){
            Account.eBankPay(orderId);
        }else if(type==2){
            window.location.href=context+'/account/ck/list/toAliPay.action?orderId='+orderId;
        }else if(type==3){
            window.location.href=context+'/account/ck/list/toWxPay.action?orderId='+orderId;
        }
    });
}

Account.rechargeAccount=function(){
    if(!Index.isHashAuthor()){
        return;
    }
    App.jump('/account/ck/list/rechargeAccount.action');
}

var countdown=60;
Account.settimeAli=function() {
    if (countdown == 0) {
        var orderId = $("#orderId").val();
        var html = '二维码已过期，<a href="'+context+'/account/ck/list/toAliPay.action?orderId='+orderId+'" style="color: blue">刷新</a>页面重新获取二维码';
        $("#timeStr").html(html);
        return;
    } else {
        $("#timeDown").html(countdown);
        countdown--;
    }
    setTimeout(function() {
        Account.settimeAli()
    },1000)
}

Account.settimeWx=function() {
    if (countdown == 0) {
        var orderId = $("#orderId").val();
        var html = '二维码已过期，<a href="'+context+'/account/ck/list/toWxPay.action?orderId='+orderId+'" style="color: blue">刷新</a>页面重新获取二维码';
        $("#timeStr").html(html);
        return;
    } else {
        $("#timeDown").html(countdown);
        countdown--;
    }
    setTimeout(function() {
        Account.settimeWx()
    },1000)
}

var countSuccess=15;
Account.setSuccessTime=function() {
    if (countSuccess == 0) {
        window.location.href=context+'/account/ck/list/showAccount.action';
        return;
    } else {
        $("#timeSuccess").html(countSuccess);
        countSuccess--;
    }
    setTimeout(function() {
        Account.setSuccessTime()
    },1000)
}

Account.toShowAccount=function(){
    window.location.href=context+'/account/ck/list/showAccount.action';
}

Account.checkPaySuccess=function(){
    var orderId = $("#orderId").val();
    $.ajax({
        type:'post',
        url:context+'/account/ck/list/checkPaySuccess.action?orderId='+orderId,
        dataType:"json",
        data:{'orderId':orderId},
        success: function (data) {
            var payStatus = data.msg;
            if(payStatus==true){
                $(".mask_float1").show();
                $("#pay_success").show();
                Account.setSuccessTime();
            }else{
                setTimeout(function() {
                    Account.checkPaySuccess()
                },5000)
            }
        }
    })
}





