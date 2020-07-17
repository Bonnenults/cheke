var PartyAdmin = {};

PartyAdmin.openRefuseDialog=function(){
    $(".mask_float").show();
    $("#reasons_for").show();
}

PartyAdmin.closeRefuseDialog=function(){
    $(".mask_float").hide();
    $("#reasons_for").hide();
}

PartyAdmin.changeStatus=function(status){
    $("#status").val(status);
    $(".qy_seach_cz").click();
    App.commonFormSubmit('filter_form');
}

PartyAdmin.selectPartyAdminClass=function(key){
    $("#partyClass").val(key);
}
//返回
PartyAdmin.returnList=function(){
    window.location.href="/admin/party/list/listParty.action";
}
//通过审核
PartyAdmin.passVerify=function(){
    var partyId = $("#partyId").val();
    $.ajax({
        url:contextpath+'/admin/party/list/passVerify.action?id='+partyId,
        dataType:"json",
        success:function(data){
            if(data.msg=="ok"){
                mizhu.confirmAlert("审核通过成功！",function(flag){
                    window.location.href="/admin/party/list/listParty.action";
                });
            }else{
                mizhu.alert(data.msg);
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试');
        },
    });
}
//拒绝
PartyAdmin.refuseVerify=function(){
    var partyId = $("#partyId").val();
    var refuseReason = $("#refuseReason").val();
    if(refuseReason==""){
        mizhu.alert("请填写拒绝原因");
        return;
    }
    if(refuseReason.length>100){
        mizhu.alert("字数不能超过100");
        return;
    }
    $.ajax({
        type:'post',
        url:contextpath+'/admin/party/list/refuseVerify.action',
        data:{"partyId":partyId,"refuseReason":refuseReason},
        dataType:"json",
        success:function(data){
            if(data.msg=="ok"){
                $(".mask_float").hide();
                $("#reasons_for").hide();
                mizhu.confirmAlert("提交成功！",function(flag){
                    window.location.href="/admin/party/list/listParty.action";
                });
            }else{
                mizhu.alert(data.msg);
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            mizhu.alert('系统出现错误，请稍候再试');
        },
    });
}


/**
 * 锁定或者解锁
 */
PartyAdmin.lockOrUnLockCheke = function(partyId,status){
    var msg ="";
    if(status ==1){
        msg = "确定解锁用户";
    }else{
        msg ="确定锁定用户";
    }
    mizhu.confirm('提示', msg, function (flag) {
        if (flag) {
            $.ajax({
                url: context + '/admin/party/list/lockOrUnLockCheke.action',
                type: 'POST',
                data: {partyId: partyId,"status":status},
                success: function (data) {
                    mizhu.alert(data);
                    App.commonFormSubmit('filter_form');
                }
            });
        }
    })
};


/**
 * 弹出修改密码弹出框
 */
PartyAdmin.openEditPwd = function (partyId,partyName,loginName) {
    $("#partyName").html(partyName);
    $("#loginName").html(loginName);
    $("#partyId").val(partyId);
    $(".mask_float").show();
    $("#editPwd").show();
    PartyAdmin.initPwdValidator();
};

PartyAdmin.initPwdValidator = function () {
    //添加验证
    var validator = new Clover.validator.Validator("#editPwd");
    PartyAdmin.pwdValidator = validator;
    PartyAdmin.pwdValidator.addValidation("#pwd1", "required");
    PartyAdmin.pwdValidator.addValidation("#pwd1", "length>=6","密码长度必须为6-15位");
    PartyAdmin.pwdValidator.addValidation("#pwd1", "length<=15","密码长度必须为6-15位");
    PartyAdmin.pwdValidator.addValidation("#pwd1", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    PartyAdmin.pwdValidator.addValidation("#pwd2", "required");
    PartyAdmin.pwdValidator.addValidation("#pwd2", "length>=6","密码长度必须为6-15位");
    PartyAdmin.pwdValidator.addValidation("#pwd2", "length<=15","密码长度必须为6-15位");
    PartyAdmin.pwdValidator.addValidation("#pwd2", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
};

/**
 * 隐藏修改密码弹出框
 */
PartyAdmin.closeEditPwd = function () {
    $(".mask_float").hide();
    $("#editPwd").hide();
};

/**
 * 修改密码
 */
PartyAdmin.submitPasswordForm = function () {
    var password = $("#pwd1").val();
    var password2 = $("#pwd2").val();
    var partyId = $("#partyId").val();
    if (!PartyAdmin.pwdValidator.validate()) {//输入验证
        return false;
    } else if (password != password2) {
        //mizhu.alert("提示！","再次密码输入不一致");
        mizhu.alert('两次输入密码不一致');
        return;
    } else {
        $.ajax({
            type:'post',
            url:context+'/admin/party/list/updatePassword.action',
            data:{"partyId":partyId,"password1":password,"password2":password2},
            success:function(result){
                if(result =='ok'){
                    mizhu.alert('修改成功');
                    PartyAdmin.closeEditPwd();
                }else{
                    mizhu.alert(result);
                }
            }
        })
    }
};


