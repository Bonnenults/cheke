var User = {};
var AdminUser = {};
var TuikeUser = {};

User.validator = null;
User.initValidator = function () {
    //添加验证
    var validator = new Clover.validator.Validator("#registerForm");
    User.validator = validator;
    User.validator.addValidation("#partyName", "required", "请填写车客名称");
    User.validator.addValidation("#partyName", "length<=20", "车客名称长度为2-20");
    User.validator.addValidation("#partyName", "length>=2", "车客名称长度为2-20");
    User.validator.addValidation("#loginName", "required", "请填写登录名");
    User.validator.addValidation("#loginName", "zmkt", "登录名以字母开头、数字组合");
    User.validator.addValidation("#loginName", "length<=20", "登录名长度为6-20");
    User.validator.addValidation("#loginName", "length>=6", "登录名长度为6-20");
    User.validator.addValidation("#loginName", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    User.validator.addValidation("#validateCode", "required","未填写图形码");
    User.validator.addValidation("#password", "required","请输入密码");
    User.validator.addValidation("#password", "length>=6","密码长度必须为6-15位");
    User.validator.addValidation("#password", "length<=15","密码长度必须为6-15位");
    User.validator.addValidation("#password", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    User.validator.addValidation("#validatePassword", "required","请输入确认密码");
    User.validator.addValidation("#validatePassword", "length>=6","密码长度必须为6-15位");
    User.validator.addValidation("#validatePassword", "length<=15","密码长度必须为6-15位");
    User.validator.addValidation("#validatePassword", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
};
User.openRegister = function () {
    $(".mask_float").show();
    $("#regist_agreement").show();
};
User.closeRegister=function(){
    $(".mask_float").hide();
    $("#regist_agreement").hide();
};
User.register = function() {
    if(User.validator.validate()){
        if(!$('#agreeBox').prop('checked')){
            mizhu.alert("请同意《车客号用户注册协议》")
            return
        }
        var password = $("#password").val();
        var validatePassword = $("#validatePassword").val();
        if(password!=validatePassword){
            mizhu.alert("两次填写的密码不一致")
            return
        }
        var datas=$("#registerForm").serialize();
        //token = $('#FORM_TOKEN_KEY').val();
        jQuery.ajax({
            type:"POST",
            url:contextpath+'/cheke/register/register.action',
            data:datas,
            dataType:"json",
            error:function(XMLHttpRequest, textStatus, errorThrown){
                mizhu.alert('系统出现错误，请稍候再试');
            },
            success:function(data){
                var msg = data.msg;
                if("ok"==msg){
                    mizhu.confirmAlert("注册成功！",function(flag){
                        window.location.href=contextpath+'/login.action';
                    });
                }else{
                    mizhu.alert(msg);
                }
            }
        });
    }
};

/**
 * 锁定或者解锁用户
 */
AdminUser.lockOrUnLockUser = function(userId,status){
    var msg ="";
    if(status ==1){
        msg = "确定解锁用户";
    }else{
        msg ="确定锁定用户";
    }
    mizhu.confirm('提示', msg, function (flag) {
            if (flag) {
                $.ajax({
                    url: context + '/user/admin/list/lockOrUnLockUser.action',
                    type: 'POST',
                    data: {userId: userId,"status":status},
                    success: function (data) {
                        mizhu.confirmAlert(data,function(){
                            App.commonFormSubmit('filter_form');
                        });

                    }
                });
            }
    })
};

/**
 * 锁定或者解锁推客用户
 */
User.lockOrUnLockTuikeUser = function(userId,status){
    var msg ="";
    if(status ==1){
        msg = "确定解锁用户";
    }else{
        msg ="确定锁定用户";
    }
    mizhu.confirm('提示', msg, function (flag) {
        if (flag) {
            $.ajax({
                url: context + '/tuike/user/list/lockOrUnLockTuikeUser.action',
                type: 'POST',
                data: {userId: userId,"status":status},
                success: function (data) {
                    mizhu.confirmAlert(data,function(){
                        App.commonFormSubmit('filter_form');
                    });
                }
            });
        }
    })
};

/**
 * 弹出修改密码弹出框
 */
AdminUser.openEditPwd = function (userId,name,loginName) {
    $("#editPwdUserId").val(userId);
    $("#editPwdName").html(name);
    $("#editPwdLoginName").html(loginName);
    $(".mask_float").show();
    $("#editPwd").show();
    AdminUser.initPwdValidator();
};

AdminUser.initPwdValidator = function () {
    //添加验证
    var validator = new Clover.validator.Validator("#editPwd");
    AdminUser.pwdValidator = validator;
    AdminUser.pwdValidator.addValidation("#pwd1", "required");
    AdminUser.pwdValidator.addValidation("#pwd1", "length>=6","密码长度必须为6-15位");
    AdminUser.pwdValidator.addValidation("#pwd1", "length<=15","密码长度必须为6-15位");
    AdminUser.pwdValidator.addValidation("#pwd1", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    AdminUser.pwdValidator.addValidation("#pwd2", "required");
    AdminUser.pwdValidator.addValidation("#pwd2", "length>=6","密码长度必须为6-15位");
    AdminUser.pwdValidator.addValidation("#pwd2", "length<=15","密码长度必须为6-15位");
    AdminUser.pwdValidator.addValidation("#pwd2", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
};

/**
 * 隐藏修改密码弹出框
 */
AdminUser.closeEditPwd = function () {
    $(".mask_float").hide();
    $("#editPwd").hide();
};

/**
 * 修改密码
 */
AdminUser.submitPasswordForm = function () {
    var password = $("#pwd1").val();
    var password2 = $("#pwd2").val();
    var userId = $("#editPwdUserId").val();
    if (!AdminUser.pwdValidator.validate()) {//输入验证
        return false;
    } else if (password != password2) {
        mizhu.alert("两次密码输入不一致");
        return;
    } else {
        $.ajax({
            type:'post',
            url:context+'/user/admin/list/savePassword.action',
            data:{"userId":userId,"password1":password,"password2":password2},
            success:function(result){
               if(result =='ok'){
                   mizhu.confirmAlert('修改成功',function(){
                       AdminUser.closeEditPwd();
                   });

               }else{
                   mizhu.alert(result);
               }
            }
        })
    }
};

/**
 * 隐藏新增修改用户弹出框
 */
AdminUser.closeEditUser = function () {
    $(".mask_float").hide();
    $("#editUser").hide();
};


/**
 * 弹出用户编辑框
 */
AdminUser.openAddOrEditUser = function (userId) {
    var url ="/user/admin/list/editUser.action";
    if(userId){
        url = url +"?userId="+userId;
    }
    $.ajax({
        type: "post",
        url: context +url,
        cache: false,
        success: function (data) {
            $('#editUser').html(data);
            $(".mask_float").show();
            $("#editUser").show();
            if(userId){
                AdminUser.updateUserValidator();
            } else{
                AdminUser.initAddUserValidator();
            }
        }
    });
};


AdminUser.initAddUserValidator = function () {
    //添加验证
    var validator = new Clover.validator.Validator("#editUser");
    AdminUser.userValidatore = validator;
    AdminUser.userValidatore.addValidation("#aName", "required");
    AdminUser.userValidatore.addValidation("#aName", "!length>1","姓名至少2位");
    AdminUser.userValidatore.addValidation("#aName", "!length<13","姓名至多12位");
    AdminUser.userValidatore.addValidation("#aLoginName", "required");
    AdminUser.userValidatore.addValidation("#aLoginName", "length>5","用户名长度至少6位");
    AdminUser.userValidatore.addValidation("#aLoginName", "length<16","用户名长度长度至多15位");
    AdminUser.userValidatore.addValidation("#aLoginName", "alpha");
    AdminUser.userValidatore.addValidation("#aRoleId", "required","请选择角色");
    AdminUser.userValidatore.addValidation("#apwd1", "required");
    AdminUser.userValidatore.addValidation("#apwd1", "length>=6","密码长度必须为6-15位");
    AdminUser.userValidatore.addValidation("#apwd1", "length<=15","密码长度必须为6-15位");
    AdminUser.userValidatore.addValidation("#apwd1", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    AdminUser.userValidatore.addValidation("#apwd2", "required");
    AdminUser.userValidatore.addValidation("#apwd2", "length>=6","密码长度必须为6-15位");
    AdminUser.userValidatore.addValidation("#apwd2", "length<=15","密码长度必须为6-15位");
    AdminUser.userValidatore.addValidation("#apwd2", "alpha");       //为password添加字符验证,只能包含字母,数字及下划线
    AdminUser.userValidatore.addValidation("#aPhone", "required");
    AdminUser.userValidatore.addValidation("#aPhone", "integer");
    AdminUser.userValidatore.addValidation("#aPhone", "length=11","手机号为11位");
    AdminUser.userValidatore.addValidation("#aEmail", "email");
    AdminUser.userValidatore.addValidation("#aEmail", "length<32");
    AdminUser.userValidatore.addAsyncValidation("#aLoginName", function (target, inform) {
           var userId = $("#aUserId").val();
           $.ajax({
               url:context + 'user/admin/list/isAvailableLoginName.action',
               data:{loginName:target.val(), userId:userId},
               success:function (isValid) {
                   inform(isValid);
               }
           })
       }, "该用户名已存在");
};

AdminUser.updateUserValidator = function () {
    //添加验证
    var validator = new Clover.validator.Validator("#editUser");
    AdminUser.userValidatore = validator;
    AdminUser.userValidatore.addValidation("#aName", "required");
    AdminUser.userValidatore.addValidation("#aName", "!length>1","姓名至少2位");
    AdminUser.userValidatore.addValidation("#aName", "!length<16","姓名至多15位");
    AdminUser.userValidatore.addValidation("#aRoleId", "required","请选择角色");
    AdminUser.userValidatore.addValidation("#aPhone", "required");
    AdminUser.userValidatore.addValidation("#aPhone", "length=11");       //为password添加字符验证,只能包含字母,数字及下划线
    AdminUser.userValidatore.addValidation("#aEmail", "length<32");       //为password添加字符验证,只能包含字母,数字及下划线
};

/**
 * 修改用户
 * 7.12
 */
User.updateUser = function (userId, type) {
    $.ajax({
        type:"GET",
        url:context + '/users/userForm_ajax.action',
        cache:false,
        data:{userId:userId, type:type},
        success:function (data) {
            $('#user_add_window').html(data).show();
            User.userDialog.dialog("open");
        }
    });
};


/**
 * 新增或者修改用户保存
 */
AdminUser.saveNewOrUpdateUser = function () {
    var successUrl = $("#successUrl").val(); //保存页面刷新
    var userId = $("#aUserId").val();
    var password =$("#apwd1").val();
    var password2 =$("#apwd2").val();
    var loginName = $("#aLoginName").val();
    var name = $("#aName").val();
    var phone = $("#aPhone").val();
    var email = $("#aEmail").val();
    var roleId = $("#aRoleId").val();
    if (!AdminUser.userValidatore.validate()) {//输入验证
        return false;
    } else if (password != password2) {
        mizhu.alert("两次密码输入不一致");
        return;
    } else {
        $.ajax({
            type:'post',
            url:context+'/user/admin/list/saveUser.action',
            data:{"userId":userId,"password1":password,"password2":password2,'loginName':loginName,"name":name,"phone":phone,"email":email,"roleId":roleId},
            success:function(result){
               if(result =='ok'){
                   mizhu.confirmAlert('保存成功', function () {
                       AdminUser.closeEditUser();
                       window.location.href = context + successUrl;
                   });
               }else{
                   mizhu.alert(result);
               }
            }
        })
    }
};


TuikeUser.openRefuseDialog=function(){
    $(".mask_float").show();
    $("#reasons_for").show();
}

TuikeUser.closeRefuseDialog=function(){
    $(".mask_float").hide();
    $("#reasons_for").hide();
}
//返回
TuikeUser.returnList=function(){
    window.location.href="/tuike/user/list/listTuikeUser.action";
}
//通过审核
TuikeUser.passVerify=function(){
    var userId = $("#userId").val();
    $.ajax({
        type:'post',
        url:contextpath+'/tuike/user/list/passVerify.action?id='+userId,
        dataType:"json",
        success:function(data){
            if(data.msg=="ok"){
                mizhu.confirmAlert("审核通过成功！",function(flag){
                    window.location.href=contextpath+"/tuike/user/list/listTuikeUser.action";
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
TuikeUser.refuseVerify=function(){
    var userId = $("#userId").val();
    var refuseReason = $("#refuseReason").val();
    if(!refuseReason){
        mizhu.alert("请填写拒绝原因");
        return;
    }
    if(refuseReason.length>100){
        mizhu.alert("拒绝原因字数不能超过100");
        return;
    }
    $.ajax({
        type:'post',
        url:contextpath+'/tuike/user/list/refuseVerify.action',
        data:{"id":userId,"refuseReason":refuseReason},
        dataType:"json",
        success:function(data){
            if(data.msg=="ok"){
                TuikeUser.closeRefuseDialog();
                mizhu.confirmAlert("提交成功！",function(flag){
                    window.location.href=contextpath+"/tuike/user/list/audit.action?id="+userId;
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



